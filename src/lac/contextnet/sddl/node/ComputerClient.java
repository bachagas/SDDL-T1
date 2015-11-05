package lac.contextnet.sddl.node;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

import lac.contextnet.sddl.node.CommandHandler;

public class ComputerClient implements NodeConnectionListener {
//	private static String	gatewayIP   = "127.0.0.1";
	private static String	gatewayIP   = "192.168.0.144";
	private static int		gatewayPort = 5500;
	private MrUdpNodeConnection	connection;
	private UUID            myUUID;
	private CommandHandler  commandHandler;
	
	public ComputerClient() {
		commandHandler = new CommandHandler();
		myUUID = UUID.fromString("788b2b22-baa6-4c61-b1bb-01cff1f5f878");
		InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
		try {
			connection = new MrUdpNodeConnection(myUUID);
			connection.connect(address);
			connection.addNodeConnectionListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Logger.getLogger("").setLevel(Level.OFF);
	    new ComputerClient();
	}

	@Override
	public void connected(NodeConnection remoteCon) {
		ApplicationMessage message = new ApplicationMessage();
		String serializableContent = "Hello World! I am a computer client";
		message.setContentObject(serializableContent);
		try {
			connection.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newMessageReceived(NodeConnection remoteCon, Message message) {
		System.out.println(message.getContentObject());
		if (message.getContentObject() instanceof String) {
			String input = (String) message.getContentObject();
			if (input!=null && !input.equals("")) {
	        	int result = commandHandler.processCommand(input);
	            //send ACK message with result information
	        	String ackMessage = "";
	            if (result > 0) { //command processed
	            	ackMessage = "Ok";
	            } else if (result < 0) { //command not recognized
	            	ackMessage = "?";
	            } else if (result == 0) { //connection closed - TODO: fix / change behavior!
	            	ackMessage = "Disconnect!";
	            }
	            System.out.println(ackMessage);
	        	ApplicationMessage appMessage = new ApplicationMessage();
	            appMessage.setRecipientID(message.getSenderID());
	            appMessage.setContentObject(ackMessage);
	            try {
	              connection.sendMessage(appMessage);
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	        }
		}
	}
	
	// other methods
	 
	@Override
	public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}
	 
	@Override
	public void disconnected(NodeConnection remoteCon) {}
	 
	@Override
	public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}
	 
	@Override
	public void internalException(NodeConnection remoteCon, Exception e) {}
}
