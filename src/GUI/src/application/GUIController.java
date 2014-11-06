package GUI.src.application;

import java.io.IOException;
import java.text.ParseException;
import java.util.function.Consumer;

import Logic.Interpreter.EpiphanyInterpreter;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 
 * Things to implement: 
 * 
 * @author tinweiyang
 *
 */
public class GUIController {

	@FXML private TextArea displayArea;
	@FXML private TextArea terminal;
	@FXML private TextField console;
	@FXML private Button enterButton;
	//private Consumer<String> onMessageReceivedHandler;
	//private Main main;
	private EpiphanyInterpreter interpreter;
	private static GUIController singleton = null;
	
	
	
	/** 
	 * Constructor for singleton
	 */
	public GUIController() {
		singleton = this;
	}
	
	public static GUIController getInstance() {
		return singleton;
	}

	// this method is redundant so far.. method in initalized already handles that button press event
	@FXML
	private void handleButtonAction() {
		String text = console.getText();
		displayArea.appendText(System.lineSeparator() + text);
	}

	/**
	 * Initializes the controller class immediately
	 */
	@FXML
	private void initialize(){
		try {
			interpreter = new EpiphanyInterpreter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("EpiphanyGUI.GUIController.initialize"); // prints out on eclipse console if successful
		//displayArea.setText("Hello World"); // 
		displayArea.setEditable(false);

		terminal.setText("Epiphany is ready for your commands");
		terminal.setEditable(false);

		console.setPromptText("write some stuff in here! e.g. Visit cheers to buy snacks today");
		console.setOnKeyReleased(keyReleasedHandler);

		enterButton.setOnAction((event) -> {
			String text = console.getText();

			//displayArea.appendText(text + System.lineSeparator()); // instead of appending the input text, input -> interpreter -> engine -> UIhandler 
			inputDisplayArea(text);
			console.clear();
		});
	}
	
	/**
	 * Accepts a string input and directly appends it to the displayArea
	 * @param input
	 */
	public void inputDisplayArea(String input) {
		displayArea.appendText(input + System.lineSeparator());
	}
	
	public void inputTerminal(String input) {
		terminal.setText(input + System.lineSeparator());
	}

	/**
	 * Is called by the main application to give reference back to itself
	 * 
	 * @param main
	 */
	/*
	public void setMain(Main main) {
		this.main = main;
	}
	
	*/

	private final EventHandler<KeyEvent> keyReleasedHandler = new EventHandler<KeyEvent>() {
		public void handle(final KeyEvent keyEvent){

			if (keyEvent.getCode().equals(KeyCode.ENTER)) {
				String text = console.getText();
				displayArea.appendText(text + System.lineSeparator());
				/*
				try {
					
					
					//interpreter.getInstance().acceptUserInput(text); // THIS IS A BIG ISSUE?!?!??!?!?!
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				console.clear();
				
				/*
				if (onMessageReceivedHandler != null) {
					onMessageReceivedHandler.accept(text);
				}
				*/
				
			}
		}
	};
}
