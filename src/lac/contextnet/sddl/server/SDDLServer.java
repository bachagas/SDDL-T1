package lac.contextnet.sddl.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.UUID;

import com.espertech.esper.client.*;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory.SupportedDDSVendors;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;
import lac.contextnet.model.EventObject;
import lac.contextnet.model.PingObject;

import org.ini4j.Ini;

public class SDDLServer implements UDIDataReaderListener<ApplicationObject> {
	
	/* The SDDL vendor supported */
    private SupportedDDSVendors supportedDDSVendor;

    /*The SDDL Layer : DDS Abstraction */
    private static SddlLayer sddlLayer;
    
    /* Gateway ID */
	private static UUID gatewayId;
	
	/* Mobile node ID */
	private static UUID nodeId;
	
	/* CEP runtime engine */
	private	static EPRuntime cepRT;
    
	private void setupEsper() {
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("EventObject", EventObject.class.getName());
		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		this.cepRT = cep.getEPRuntime();
		
		// Esper rule to detect user event
		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement ruleConflict = cepAdm.createEPL(
		      //todo o evento precedido por um evento no mesmo contexto mas com uma fonte diferente num intervalo de 5 segundos ==> para detectar os conflitos
		      "select b.source as sender "
		      + "from pattern "
		      + "[every (a=EventObject() -> b=EventObject(context=a.context, source!=a.source) "
		      + "where timer:within(5 seconds))]"
		);
		ruleConflict.addListener(new CEPConflictListener());
		EPStatement ruleComputerNoConflict1 = cepAdm.createEPL(
				"select '#mouse up' as command "
				+ "from EventObject(context='computer').win:length(1) "
				+ "where name = 'event1'"
		);
		ruleComputerNoConflict1.addListener(new CEPComputerListener());
		EPStatement ruleComputerNoConflict2 = cepAdm.createEPL(
				"select '#mouse down' as command "
				+ "from EventObject(context='computer').std:lastevent() "
				+ "where name = 'event2'"
		);
		ruleComputerNoConflict2.addListener(new CEPComputerListener());
		EPStatement ruleLamp = cepAdm.createEPL(
				"select '#1' as command from " +
		        "EventObject(context='lamp').win:length(1) " +
		        "where name = 'event1'"
		);
		ruleLamp.addListener(new CEPLampListener());
	}

	private static class CEPComputerListener implements UpdateListener {
	  public void update(EventBean[] newData, EventBean[] oldData) {
	        System.out.println("COMPUTER COMMAND EVENT: " + newData[0].getUnderlying());
			if (newData[0].get("command") instanceof String) {
				sendMessageToComputer((String) newData[0].get("command"));
			}
	  }
	}
	
	private static class CEPLampListener implements UpdateListener {
	  public void update(EventBean[] newData, EventBean[] oldData) {
	        System.out.println("LAMP COMMAND EVENT: " + newData[0].getUnderlying());
			if (newData[0].get("command") instanceof String) {
				sendMessageToArduino((String) newData[0].get("command"));
			}
	  }
	}
	
	private static class CEPConflictListener implements UpdateListener {
	  public void update(EventBean[] newData, EventBean[] oldData) {
	        System.out.println("*** CONFLICT DETECTED: " + newData[0].getUnderlying());
			if (newData[0].get("sender") instanceof String) {
				sendMessageToNode("*** CONFLICT DETECTED: " + newData[0].getUnderlying(), (String) newData[0].get("sender"));
			}
	  }
	}
	
	public SDDLServer () 
	{
		System.out.println("SDDLServer: starting...");
		
		/*read configuration file*/
		System.out.println("SDDLServer: reading configuration file...");
		readConfigurationFile();
		
	    /*create the SDDL layer with a Subscriber listener*/
		System.out.println("SDDLServer: initializing DDS and SDDL...");
	    sddlLayer = UniversalDDSLayerFactory.getInstance(supportedDDSVendor);
	    sddlLayer.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);
	    sddlLayer.createPublisher();
	    sddlLayer.createSubscriber();
	    Object receiveTopic = sddlLayer.createTopic(Message.class, Message.class.getSimpleName());
	    Object sendTopic = sddlLayer.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
	    sddlLayer.createDataReader(this, receiveTopic);
	    sddlLayer.createDataWriter(sendTopic);
	    
	    /*initialize CEP engine */
		System.out.println("SDDLServer: initializing CEP engine...");
	    setupEsper();
	}
		
	public static void main(String[] args) {
		new SDDLServer();

		System.out.println("SDDLServer: started successfully.");
		try {
			while(true) {
				/*print on screen the input message*/
				System.out.print("Escreva a mensagem: ");
				
				/*create and get the input from console*/
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			    String inputMsg = bufferRead.readLine();
			    
			    /*create a private message*/
			    PrivateMessage pMsg = new PrivateMessage();
			    pMsg.setGatewayId(gatewayId);
			    pMsg.setNodeId(nodeId);
			    
			    /*create a application message with the MESSAGE*/
			    ApplicationMessage appMsg = new ApplicationMessage();
			    appMsg.setContentObject(inputMsg);
			    
			    /*assign the private message the application message to be sent to mobile node*/
			    pMsg.setMessage(Serialization.toProtocolMessage(appMsg));
			    
			    /*write topic to DDS*/
			    sddlLayer.writeTopic(PrivateMessage.class.getSimpleName(), pMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onNewData(ApplicationObject topicSample) {
		Message msg = null;
		
		if (topicSample instanceof Message) {
			msg = (Message) topicSample;
			
			gatewayId = msg.getGatewayId();
			nodeId = msg.getSenderId();

			Serializable rawData = Serialization.fromJavaByteStream(msg.getContent());
			treatDataReceival(msg, rawData);
		}
	}
	
	/* Private Methods */
	/**
	 * This method is the one you should customize so that the server is capable of 
	 * handling different types of objects.
	 * 
	 * @param msg
	 * @param rawData
	 */
	private void treatDataReceival(Message msg, Serializable rawData) {
		if(rawData instanceof PingObject) {
			PingObject ping = (PingObject) rawData;
			
			// System.out.print(ping.toString());
			System.out.println("\nMensagem: " + ping.toString());
			System.out.print("Escreva a mensagem: ");
			
			ping.changeState();

			ApplicationMessage appMsg = new ApplicationMessage();
			appMsg.setContentObject(ping);
			
			PrivateMessage privateMessage = new PrivateMessage();
			privateMessage.setGatewayId(gatewayId);
			privateMessage.setNodeId(nodeId);
			privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));

			sddlLayer.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
		}
		if (rawData instanceof EventObject) {
			EventObject newEvent = (EventObject) rawData;
			System.out.print("\nNew event received from: ");
			System.out.print(msg.getGatewayId());
			System.out.print(" / ");
			System.out.println(msg.getSenderId());
			System.out.println("Event: " + newEvent.toString());
			//process events using CEP engine and dispatch results
			cepRT.sendEvent(newEvent);
			System.out.print("Escreva a mensagem: ");
		}
		if (rawData instanceof String) {
			String newEvent = (String) rawData;
			System.out.print("\nNew message received from: ");
			System.out.print(msg.getGatewayId());
			System.out.print(" / ");
			System.out.println(msg.getSenderId());
			System.out.println("Message: " + newEvent);
			System.out.print("Escreva a mensagem: ");
		}
	}
	
	//sends message to sender node
	private static void sendMessageToNode (String msg, String nodeStr) {
		ApplicationMessage appMsg = new ApplicationMessage();
		appMsg.setContentObject(msg);
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setGatewayId(gatewayId);
		UUID nodeId = UUID.fromString(nodeStr);
		privateMessage.setNodeId(nodeId);
		privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));
		sddlLayer.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
	}
	
	//sends command to computer node
	private static void sendMessageToComputer (String msg) {
		sendMessageToNode(msg, "788b2b22-baa6-4c61-b1bb-01cff1f5f878");
	}
	
	//sends command to arduino node
	private static void sendMessageToArduino (String msg) {
		sendMessageToNode(msg, "550547b6-dcea-4aa6-8279-04332f1e251e");
	}
	
	private void readConfigurationFile () {
		/*reading the configuration file (config.ini)*/
        try {
		    String vendor;
		
            File iniFile = new File("config.ini");
            Ini ini = new Ini(iniFile);

            /*check for the sddl vendor*/
            vendor = ini.get("sddllayer", "sddl_vendor");
            if (vendor == null)
                    throw new Exception("Missing 'sddl_vendor' from [sddllayer].");

            if (!vendor.equals("CoreDX")
            && !vendor.equals("RTI")
            && !vendor.equals("OpenSplice"))
                    throw new Exception("Unsupported sddl vendor: "+vendor+", choose between 'CoreDX', 'RTI' or 'OpenSplice'.");

            if (vendor.equals("CoreDX"))
                    supportedDDSVendor = SupportedDDSVendors.CoreDX;
            else if (vendor.equals("RTI"))
                    supportedDDSVendor = SupportedDDSVendors.RTI;
            else if (vendor.equals("OpenSplice"))
                    supportedDDSVendor = SupportedDDSVendors.OpenSplice;
                
        } catch (IOException e) {
            System.out.println("Unable to read file 'config.ini', it exists?");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
	}
}
