package lac.contextnet.sddl.node;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Maps characters to the corresponding key events to be generated according to the keyboard layout.
 * Key codes events based on: https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
 * @author Bruno
 *
 */
public class KeyboardMap extends HashMap {
	private Robot robot;
	
	public KeyboardMap(String layout) throws Exception {
		super();
		robot = new Robot();
		robot.setAutoDelay(0);
		switch (layout) {
		case "Português (Brasil ABNT)":
			initializeKeyMapBrasilABNT();
			break;
		default:
			throw new Exception("Keyboard layout " + layout + " is not recognized/supported yet. Please, contact the development team.");
		}
	}
	
    /**
     * Initializes the keyboard map according to the user keyboard layout:
     * unicode char -> sequence of java.awt.event.keyEvent constants
     * Note: Must be adapted to user keyboard layout.
     */
    private void initializeKeyMapBrasilABNT() {
    	Vector temp;
    	//Basic letters (lowercase):
		temp = new Vector(); temp.add(KeyEvent.VK_A); this.put('a', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_B); this.put('b', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_C); this.put('c', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_D); this.put('d', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_E); this.put('e', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_F); this.put('f', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_G); this.put('g', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_H); this.put('h', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_I); this.put('i', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_J); this.put('j', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_K); this.put('k', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_L); this.put('l', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_M); this.put('m', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_N); this.put('n', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_O); this.put('o', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_P); this.put('p', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_Q); this.put('q', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_R); this.put('r', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_S); this.put('s', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_T); this.put('t', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_U); this.put('u', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_V); this.put('v', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_W); this.put('w', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_X); this.put('x', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_Y); this.put('y', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_Z); this.put('z', temp);
    	//Uppercase letters:
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_A); this.put('A', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_B); this.put('B', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_C); this.put('C', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_D); this.put('D', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_E); this.put('E', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_F); this.put('F', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_G); this.put('G', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_H); this.put('H', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_I); this.put('I', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_J); this.put('J', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_K); this.put('K', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_L); this.put('L', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_M); this.put('M', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_N); this.put('N', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_O); this.put('O', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_P); this.put('P', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_Q); this.put('Q', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_R); this.put('R', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_S); this.put('S', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_T); this.put('T', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_U); this.put('U', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_V); this.put('V', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_W); this.put('W', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_X); this.put('X', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_Y); this.put('Y', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_Z); this.put('Z', temp);
    	//Special letters with accentuation for the Brazilian Portuguese:
    	//TODO: Fix "ç" and "Ç" to map to real cedilla chars.
    	//temp = new Vector(); temp.add(KeyEvent.VK_DEAD_CEDILLA); this.put('ç', temp);
    	//temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_CEDILLA); this.put('Ç', temp);
    	//temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_C); this.put('ç', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_C); this.put('ç', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_C); this.put('Ç', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_A); this.put('á', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_A); this.put('Á', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_E); this.put('é', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_E); this.put('É', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_I); this.put('í', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_I); this.put('Í', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_O); this.put('ó', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_O); this.put('Ó', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_U); this.put('ú', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_U); this.put('Ú', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_A); this.put('à', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_A); this.put('À', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_E); this.put('è', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_E); this.put('È', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_I); this.put('ì', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_I); this.put('Ì', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_O); this.put('ò', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_O); this.put('Ò', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_U); this.put('ù', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); temp.add(KeyEvent.VK_U); this.put('Ù', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_A); this.put('â', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_A); this.put('Â', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_A); this.put('ã', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_A); this.put('Ã', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_O); this.put('õ', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_O); this.put('Õ', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_E); this.put('ê', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_E); this.put('Ê', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_I); this.put('î', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_I); this.put('Î', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_O); this.put('ô', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_O); this.put('Ô', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_U); this.put('û', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); temp.add(KeyEvent.VK_U); this.put('Û', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CAPS_LOCK); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_6); temp.add(KeyEvent.VK_U); this.put('ü', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_6); temp.add(KeyEvent.VK_U); this.put('Ü', temp);
    	//Others chars:
    	temp = new Vector(); temp.add(KeyEvent.VK_BACK_SLASH); this.put('\\', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_BACK_SLASH); this.put('|', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DIVIDE); this.put('/', temp); //Note: VK_SLASH didn't work!
    	//TODO: Fix question mark "?"
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DIVIDE); this.put('?', temp);
    	//temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_SLASH); this.put('?', temp);
    	//temp = new Vector(); temp.add(KeyEvent.VK_ALT_GRAPH); temp.add(KeyEvent.VK_W); this.put('?', temp);
    	//TODO: Fix VK_ALT_GRAPH key.
    	//temp = new Vector(); temp.add(KeyEvent.VK_ALT_GRAPH); temp.add(KeyEvent.VK_SLASH); this.put('°', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_COMMA); this.put(',', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_COMMA); this.put('<', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_PERIOD); this.put('.', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_PERIOD); this.put('>', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SEMICOLON); this.put(';', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_SEMICOLON); this.put(':', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_TILDE); this.put('~', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_TILDE); this.put('^', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_CLOSE_BRACKET); this.put(']', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_CLOSE_BRACKET); this.put('}', temp);
    	//TODO: Fix VK_ALT_GRAPH key.
    	//temp = new Vector(); temp.add(KeyEvent.VK_ALT_GRAPH); temp.add(KeyEvent.VK_CLOSE_BRACKET); this.put('º', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_DEAD_ACUTE); this.put('´', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_DEAD_ACUTE); this.put('`', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_OPEN_BRACKET); this.put('[', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_OPEN_BRACKET); this.put('{', temp);
    	//TODO: Fix VK_ALT_GRAPH key.
    	//temp = new Vector(); temp.add(KeyEvent.VK_ALT_GRAPH); temp.add(KeyEvent.VK_OPEN_BRACKET); this.put('ª', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_QUOTE); this.put('\'', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_QUOTE); this.put('\"', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_0); this.put('0', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_1); this.put('1', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_2); this.put('2', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_3); this.put('3', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_4); this.put('4', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_5); this.put('5', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_6); this.put('6', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_7); this.put('7', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_8); this.put('8', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_9); this.put('9', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_MINUS); this.put('-', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_EQUALS); this.put('=', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_1); this.put('!', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_2); this.put('@', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_3); this.put('#', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_4); this.put('$', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_5); this.put('%', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_6); this.put('¨', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_7); this.put('&', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_8); this.put('*', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_9); this.put('(', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_0); this.put(')', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_MINUS); this.put('_', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SHIFT); temp.add(KeyEvent.VK_EQUALS); this.put('+', temp);
    	//TODO: Fix VK_ALT_GRAPH key.
    	//temp = new Vector(); temp.add(KeyEvent.VK_ALT_GRAPH); temp.add(KeyEvent.VK_EQUALS); this.put('§', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_ENTER); this.put('\n', temp);
    	temp = new Vector(); temp.add(KeyEvent.VK_SPACE); this.put(' ', temp); //space
    	temp = new Vector(); temp.add(KeyEvent.VK_TAB); this.put('\t', temp); 
    }
    
    public void type(char key) throws Exception {
    	Vector keyCodes = (Vector) this.get(key);
    	if (keyCodes!=null) doType(keyCodes, 0, keyCodes.size());
    	else throw new Exception("Cannot recognize character '" + key + "' (" + (int) key + ").");
    }
    
    /**
     * Types one or more key presses (java.awt.event.KeyEvent).
     */
    private void doType(Vector keyCodes, int offset, int length) throws IllegalArgumentException {
        if (length == 0) {
            return;
        }
        
        try {
            robot.keyPress((int) keyCodes.elementAt(offset));
            doType(keyCodes, offset + 1, length - 1);
            robot.keyRelease((int) keyCodes.elementAt(offset));
        } catch (IllegalArgumentException e) {
        	throw new IllegalArgumentException("Cannot type virtual key " + keyCodes.elementAt(offset) + ". This is probably because your keyboard is not correctly mapped. Please, contact the developer.");
        }
    }
    
    public static void main(String[] args) throws Exception {
    	int ok = 0;
    	int errors = 0;
    	Robot robot = new Robot();

    	System.out.println("Please, click here and press ENTER to begin test...");
    	try {
	    	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    	bufferRead.readLine();
	    	String layout = "Português (Brasil ABNT)";
	    	System.out.println("Got it! Initing test for keyboard layout: " + layout + "\nPlase, wait here until the test ends...\n");
	    	KeyboardMap keyMap = new KeyboardMap(layout);
	    	Vector ENTER_KEY = (Vector) keyMap.get('\n');
	    	Iterator iterator = keyMap.entrySet().iterator();
	    	while (iterator.hasNext()) {
	    		Map.Entry mapEntry = (Map.Entry) iterator.next();
	    		System.out.println("Test for character=" + mapEntry.getKey()
	    			+ ", Key events=" + mapEntry.getValue().toString());
	    		Vector keys = (Vector) mapEntry.getValue();
	    		
    		    System.out.print("Simulated typing: ");
    		    Thread.sleep(100);
    		    // Simulate the typing of each character:
    		    // stack keys
    		    for (int i=0;i<keys.size();i++) {
    		    	robot.keyPress((int) keys.elementAt(i));
    		    	//some keys must be released immediately
    		    	if ((int) keys.elementAt(i)==KeyEvent.VK_CAPS_LOCK) robot.keyRelease((int) keys.elementAt(i));
    		    }
    		    
    		    // unstack keys
    		    for (int i=keys.size();i>0;i--) {
    		    	//some keys were released immediately
    		    	if ((int) keys.elementAt(i-1)==KeyEvent.VK_CAPS_LOCK) robot.keyPress((int) keys.elementAt(i-1));
    		    	robot.keyRelease((int) keys.elementAt(i-1));
    		    }
    		    
    		    // Simulate the ENTER key:
    		    robot.keyPress((int) ENTER_KEY.elementAt(0)); //Note: ENTER is just one key press (VK_ENTER).
    		    
    	    	String s = bufferRead.readLine();
    		    System.out.println(s);
    		    if ((char)mapEntry.getKey()=='\n') {
    		    	ok++; //we assume it is always ok for \n since it uses it on every other char type
    		    	s = bufferRead.readLine(); //we read one more line from the input buffer
    		    }
    		    else if (s.charAt(0)==(char)mapEntry.getKey()) ok++;
    		    else {
    		    	errors++;
    		    	System.out.println("Failed for character=" + mapEntry.getKey()
    		    			+ ", Key events=" + mapEntry.getValue().toString());
    		    }
	    	}
	    	System.out.println("\nFinished testing for the <" + layout + "> keyboard layout with " + keyMap.size() + " characters, " + ok + " passed, " + errors + " not good");
	    	if (ok==keyMap.size()) System.out.println("Yeah! It seems it worked for everything! (You are good ;-)"); 
    	}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }
    
}
