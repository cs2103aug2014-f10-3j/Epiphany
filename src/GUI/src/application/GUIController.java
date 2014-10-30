package application;

import java.util.function.Consumer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class GUIController {
	

	@FXML private TextArea displayArea;
	@FXML private TextArea terminal;
	@FXML private TextField console;
	@FXML private Button enterButton;
	private Consumer<String> onMessageReceivedHandler;
	private Main main;
	
	public GUIController() {
		
	}
	
	/**
	 * Initializes the controller class immediately
	 */
	@FXML
	private void initialize(){
		System.out.println("EpiphanyGUI.GUIController.initialize");
		//displayArea.setText("Hello World"); // 
		displayArea.setEditable(false);
		
		//terminal.setText("Epiphany is ready for your commands");
		terminal.setEditable(false);
		
		console.setPromptText("write some stuff in here! e.g. Visit cheers to buy snacks today at 2pm");
		console.setOnKeyReleased(keyReleasedHandler);
	}
		
		/*
		console.addEventHandler(KeyEvent.KEY_RELEASED, KeyEvent) {
			switch (keyEvent.g)
			
		}
		*/

	/**
	 * Is called by the main application to give reference back to itself
	 * 
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	private final EventHandler<KeyEvent> keyReleasedHandler = new EventHandler<KeyEvent>() {
        public void handle(final KeyEvent keyEvent){
        	if (keyEvent.getCode().equals(KeyCode.ENTER)) {
        		String text = console.getText();
        		displayArea.appendText(text + System.lineSeparator());
        		
        	 	if (onMessageReceivedHandler != null) {
            		onMessageReceivedHandler.accept(text);
            		terminal.appendText("Command is successful");
            	}
        		console.clear();
        	}
        	
   
        }
	};
	
	 public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
	        this.onMessageReceivedHandler = onMessageReceivedHandler;
	 }
}
