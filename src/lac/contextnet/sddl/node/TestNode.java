package lac.contextnet.sddl.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.PrivateMessage;

public class TestNode implements NodeConnectionListener {

//  private static String       gatewayIP    = "127.0.0.1";
  private static String       gatewayIP    = "192.168.0.144";
  private static int          gatewayPort  = 5500;
  private MrUdpNodeConnection connection;
  private UUID                myUUID;

  public TestNode() {
    myUUID = UUID.fromString("bb103877-8335-444a-be5f-db8d916f6754");
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

    TestNode client = new TestNode();
    
    try {
		while(true) {
			/*print on screen the input message*/
			System.out.print("\nEscreva a mensagem: ");
			
			/*create and get the input from console*/
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String inputMsg = bufferRead.readLine();
		    
		    /*send message to SDDL*/
		    if (inputMsg != null && inputMsg.length() > 0)
		    	client.sendTextToComputer(inputMsg);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
    
  }
  
  public void sendTextToComputer(String text) throws IOException {
	ApplicationMessage message = new ApplicationMessage();
    message.setContentObject(text);
    message.setRecipientID(UUID.fromString("788b2b22-baa6-4c61-b1bb-01cff1f5f878"));
    try {
      connection.sendMessage(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void connected(NodeConnection remoteCon) {
	String serializableContent = "Hello World! I am a test node";
    ApplicationMessage message = new ApplicationMessage();
    message.setContentObject(serializableContent);
    try {
      connection.sendMessage(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void newMessageReceived(NodeConnection remoteCon, Message message) {
    System.out.print("\nI received a message: ");
    System.out.println(message.getContentObject());
    /*print on screen the input message*/
	System.out.print("\nEscreva a mensagem: ");
  }

  @Override
  public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

  @Override
  public void disconnected(NodeConnection remoteCon) {}

  @Override
  public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

  @Override
  public void internalException(NodeConnection remoteCon, Exception e) {
	  System.out.println("Internal exception!");
	  System.out.println(e);
  }
}