/**
 * This is a singleton class that can be instantiated and used to perform
 * all display to the user interface.
 * 
 * @author abdulla contractor and amit gamane
 */
package Logic;

public class UIHandler{
	static UIHandler uiHandler;
	
	private UIHandler() {
	}
	
	/**
	 * Obtain a
	 * @return
	 */
	public static UIHandler getInstance(){
		if(uiHandler == null){
			uiHandler = new UIHandler();
		} 
		return uiHandler;
	}
	
	void printToTerminal(String toPrint){
		System.out.println(toPrint);
	}

	void printToTerminal(String toPrint, String modifier){
		System.out.print(toPrint);
	}
	
	void printToDisplay(String toPrint){
		System.out.println(toPrint);
	}	
}
