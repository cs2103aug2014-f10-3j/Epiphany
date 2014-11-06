package Logic.Interpreter;

import java.awt.FontFormatException;
import java.io.IOException;
import java.text.AttributedString;
import java.text.ParseException;
import java.awt.font.TextAttribute;

//import GUIBackup.EpiphanyUI;

/**
 * This is a singleton class that can be instantiated and used to perform
 * all display to the user interface.
 *
 * 
 * @author abdulla contractor and amit gamane
 */

 public class UIHandler{
 	public static UIHandler uiHandler;
 	//public static EpiphanyUI ui;
 	
 	private UIHandler() throws IOException, ParseException, FontFormatException {
 		uiHandler=this;
 		//ui = new EpiphanyUI();
 	}
 	
	/**
	 * Obtain a instance of the class
	 * @return UIHandler
	 * @throws ParseException 
	 * @throws IOException 
	 */
 	public static UIHandler getInstance(){
 		if(uiHandler == null){
 			try {
				uiHandler = new UIHandler();
			} catch (Exception e) {
				e.printStackTrace();
			}
 		} 
 		return uiHandler;
 	}
 	
 	/**
	 * Prints parameter to Terminal.
	 * @param toPrint
	 */
 	public void printToTerminal(String toPrint){
 		System.out.println(toPrint);
 		//ui.changeSystemStatusTo(toPrint);
 	}
 
 	/**
 	 * Prints parameter inline to Terminal.
 	 * @param toPrint
 	 * @param modifier
 	 */
 	public void printToTerminal(String toPrint, String modifier){
 		System.out.print(toPrint);
 		//ui.changeSystemStatusTo(toPrint);
 	}
 	
 	/**
 	 * Prints paramter to Display Console
 	 * @param toPrint
 	 */
 	public void printToDisplay(String toPrint){
 		System.out.println(toPrint);
 		//ui.changeDisplayTo(toPrint);
 	}
 	
 	/*public  void resetDisplay(){
 		ui.resetDisplay();
<<<<<<< Updated upstream
 	}*/
=======
 	}
 	
 	// wy added a function here to enable strikethrough text
 	public void strikeThroughText(String input) {
 		AttributedString str_attribute = new AttributedString(input);
 		str_attribute.addAttribute(TextAttribute.STRIKETHROUGH, input.length());
 	}
>>>>>>> Stashed changes
 }