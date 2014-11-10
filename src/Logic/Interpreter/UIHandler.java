//@author A0118794R
package Logic.Interpreter;

import java.awt.FontFormatException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.fusesource.jansi.AnsiConsole;

/**
 * This is a singleton class that can be instantiated and used to perform
 * all display to the user interface.
 *
 */

public class UIHandler{

	public static UIHandler uiHandler;
	private static HashMap<String, String> colors;
	
	private UIHandler() throws IOException, ParseException, FontFormatException {
		 AnsiConsole.systemInstall();
		uiHandler=this;
		colors = new HashMap<String, String>();
		populateColors();
	}
	
	private static void populateColors(){
		colors.put("green", "\033[32m");
		colors.put("red", "\033[31m");
		colors.put("black", "\033[30m");
		colors.put("white", "\033[37m");
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
	}

	/**
	 * Prints parameter inline to Terminal.
	 * @param toPrint
	 * @param modifier
	 */
	public void printToTerminal(String toPrint, String modifier){
		if(modifier.equals("inline")){
			System.out.print(toPrint);
		}else if(isColor(modifier)){
			System.out.println(colors.get(modifier) + toPrint);
		}else{
			System.out.println("error");
		}
	}

	/**
	 * Prints paramter to Display Console
	 * @param toPrint
	 */
	public void printToDisplay(String toPrint){
		System.out.println(toPrint);
	}

	public void printToDisplay(String toPrint, String color){
		System.out.println(toPrint);
	}

	public void strikeThroughText(String input) {
		System.out.println("\033[9m" + input); // red when overdue
		resetToDefault(); // to reset terminal to default system colour
	}

	// This is for completed tasks
	public void printToDisplayGreen(String input) {
		System.out.println("\033[1;32m" + input); // red when overdue
		resetToDefault(); // to reset terminal to default system colour
	}

	// This is for overdue tasks
	public void printToDisplayRed(String input) {
		System.out.println("\033[31m" + input); // red when overdue
		resetToDefault(); // to reset terminal to default system colour
	}
	
	public void printHeader(String input) {
		System.out.println("\033[31m" + input); // red when overdue
		resetToDefault(); // to reset terminal to default system colour
	}

	public void resetToDefault() {
		System.out.print("\033[0m"); // to reset terminal to default system colour
	}
	
	public static boolean isColor(String input){
		return (colors.containsKey(input));
	}
}