package lac.contextnet.sddl.node;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

import org.ini4j.Ini;

import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory.SupportedDDSVendors;

public class CommandHandler extends Thread {
    private Robot robot;
    private static int mouseIncrement = 10;
    private static int mouseSpeed = 1; //to smooth the mouse move independent
    private static int wheelIncrement = 10;
    private static int typeDelay = 20;
    private static String keyboardMap = "Português (Brasil ABNT)";
    private KeyboardMap keyMap;

    public CommandHandler() {
		try {
			keyMap = new KeyboardMap(keyboardMap);
			// Creates the robot that will emulate local events. 
	        robot = new Robot();
			robot.setAutoDelay(0);
		} catch (Exception e) {
			e.printStackTrace();
		}		
    }

    /**
     * Parses and process a text command.
     * Returns:
     * - > 0: if command successfully processed
     * - < 0: if command unknown
     * - 0  : if must close connection
     * In this case, commands will be processed as following:
     * - a raw text 
     * @throws InterruptedException 
     */
    public int processCommand(String command) {
    	if (command.charAt(0) == '#') {
    		String params[] = command.split(" ");
    		switch (params[0].toLowerCase().substring(1)) {
    		case "mouse":
    	        handleMouseEvent(params[1].toLowerCase());
    			break;
    		case "delete":
    			robot.keyPress(KeyEvent.VK_DELETE);
    			robot.keyRelease(KeyEvent.VK_DELETE);
    			break;
    		case "backspace":
    			robot.keyPress(KeyEvent.VK_BACK_SPACE);
    			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
    			break;
    		case "enter":
    			robot.keyPress(KeyEvent.VK_ENTER);
    			robot.keyRelease(KeyEvent.VK_ENTER);
    			break;
    		case "close":
    			return 0;
    			//break;
    		default: return -1;
    		}
    	} else {
    		type(command); //just types the received text
    	}
    	return 1;
    }
    
    /**
     * Simulates mouse events.
     */
    private void handleMouseEvent(String event) {
		switch (event.toLowerCase()) {
		case "up":
			moveMouse(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y - mouseIncrement);
			break;
		case "down":
			moveMouse(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y + mouseIncrement);
			break;
		case "left":
			moveMouse(MouseInfo.getPointerInfo().getLocation().x - mouseIncrement, MouseInfo.getPointerInfo().getLocation().y);
			break;
		case "right":
			moveMouse(MouseInfo.getPointerInfo().getLocation().x + mouseIncrement, MouseInfo.getPointerInfo().getLocation().y);
			break;
		case "press":
			robot.mousePress(InputEvent.BUTTON1_MASK);
		    break;
		case "release":
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		    break;
		case "click":
			robot.mousePress(InputEvent.BUTTON1_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		    break;
		case "press_right":
			robot.mousePress(InputEvent.BUTTON3_MASK);
		    break;
		case "release_right":
		    robot.mouseRelease(InputEvent.BUTTON3_MASK);
		    break;
		case "click_right":
			robot.mousePress(InputEvent.BUTTON3_MASK);
		    robot.mouseRelease(InputEvent.BUTTON3_MASK);
		    break;
		case "double_click":
			robot.mousePress(InputEvent.BUTTON1_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		    robot.delay(100);
		    robot.mousePress(InputEvent.BUTTON1_MASK);
		    robot.mouseRelease(InputEvent.BUTTON1_MASK);
		    break;
		case "whell_up":
			robot.mouseWheel(wheelIncrement);
			break;
		case "whell_down":
			robot.mouseWheel(-wheelIncrement);
			break;
		default: break;
		}
    }
    
    /**
     * Simulates a smooth mouse move.
     */
    private void moveMouse(int x, int y) {
    	int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        //TODO: improve this algorithm.
        if (y<mouseY)
			for (int i=0;i<=mouseIncrement/mouseSpeed;i=i+1) {
				mouseY=mouseY-mouseSpeed;
				robot.mouseMove(mouseX, mouseY);
			}
        else if (y>mouseY)
			for (int i=0;i<=mouseIncrement/mouseSpeed;i=i+1) {
				mouseY=mouseY+mouseSpeed;
				robot.mouseMove(mouseX, mouseY);
			}
        if (x<mouseX)
			for (int i=0;i<=mouseIncrement/mouseSpeed;i=i+1) {
				mouseX=mouseX-mouseSpeed;
				robot.mouseMove(mouseX, mouseY);
			}
        else if (x>mouseX)
			for (int i=0;i<=mouseIncrement/mouseSpeed;i=i+1) {
				mouseX=mouseX+mouseSpeed;
				robot.mouseMove(mouseX, mouseY);
			}
    }
    
    /**
     * Types a raw text character sequence.
     */
    private void type(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            char character = characters.charAt(i);
			try {
				//keyMap.type(character);
				type(character);
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
    }
    
    /**
     * Types a single character.
     */
    private void type(char key) throws IllegalArgumentException {
    	Vector keyCodes = (Vector) keyMap.get(key);
    	if (keyCodes!=null) doType(keyCodes, 0, keyCodes.size());
    	else throw new IllegalArgumentException("Cannot recognize character '" + key + "' (" + (int) key + ").");
    }
    
    /**
     * Simulates an user typing one or more keys (java.awt.event.KeyEvent).
     */
    private void doType(Vector keyCodes, int offset, int length) throws IllegalArgumentException {
        if (length == 0) {
            return;
        }
        
        try {
            robot.keyPress((int) keyCodes.elementAt(offset));
            //some keys must be released immediately
            if ((int) keyCodes.elementAt(offset)==KeyEvent.VK_CAPS_LOCK) robot.keyRelease((int) keyCodes.elementAt(offset));
            doType(keyCodes, offset + 1, length - 1);
            //some keys were be released immediately
            if ((int) keyCodes.elementAt(offset)==KeyEvent.VK_CAPS_LOCK) robot.keyPress((int) keyCodes.elementAt(offset));
            robot.keyRelease((int) keyCodes.elementAt(offset));
        } catch (IllegalArgumentException e) {
        	throw new IllegalArgumentException("Cannot type virtual key " + keyCodes.elementAt(offset) + ". This is probably because your keyboard is not correctly mapped. Please, contact the developer.");
        }
    }
    
    /**
     * Reads configuration file.
     */    
    private void readConfigurationFile () {

		/*reading the configuration file (computer.ini)*/
        try {	    
            File iniFile = new File("computer.ini");
            Ini ini = new Ini(iniFile);

            /*check for the computer robot parameters*/
            mouseIncrement = Integer.parseInt(ini.get("mouse", "mouseIncrement"));
            mouseSpeed = Integer.parseInt(ini.get("mouse", "mouseSpeed"));
            wheelIncrement = Integer.parseInt(ini.get("mouse", "wheelIncrement"));
            typeDelay = Integer.parseInt(ini.get("keyboard", "typeDelay"));
            keyboardMap = ini.get("keyboard", "keyboardMap");                
        } catch (IOException e) {
            System.out.println("Unable to read file 'config.ini', it exists?");
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
	}
}
