package Logic.Interpreter;
/**
 * This is a singleton class that can be instantiated and used to perform
 * all display to the user interface.
 *
 * 
 * @author abdulla contractor and amit gamane
 */

 public class UIHandler{
 	static UIHandler uiHandler;
 	
 	private UIHandler() {
 	}
 	
	/**
	 * Obtain a instance of the class
	 * @return UIHandler
	 */
 	public static UIHandler getInstance(){
 		if(uiHandler == null){
 			uiHandler = new UIHandler();
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
 		System.out.print(toPrint);
 	}
 	
 	/**
 	 * Prints paramter to Display Console
 	 * @param toPrint
 	 */
 	public void printToDisplay(String toPrint){
 		System.out.println(toPrint);
 	}	
 }