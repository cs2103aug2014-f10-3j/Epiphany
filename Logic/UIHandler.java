package Logic;

public class UIHandler{
	static UIHandler uiHandler;
	
	private UIHandler() {
	}
	
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
