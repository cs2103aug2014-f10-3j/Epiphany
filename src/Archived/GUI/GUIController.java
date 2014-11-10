//@author A0118794R-unused
package Archived.GUI;

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
	private Main main;
	private static GUIController singleton = null;
	private static final String MESSAGE_INITIALIZE = "EpiphanyGUI.GUIController.initialize";
	private static final String MESSAGE_READY = "Epiphany is ready for your commands";
	private static final String MESSAGE_PROMPT = "write some stuff in here! e.g. Visit cheers to buy snacks today";

	/******** GUIController as a singleton object ***************/
	public GUIController() {
		singleton = this;
	}
	
	public static GUIController getInstance() {
		return singleton;
	}
	
	/**
	 * Initializes the controller class immediately
	 */
	@FXML
	private void initialize() {
		System.out.println(MESSAGE_INITIALIZE);
		displayArea.setEditable(false);
		terminal.setText(MESSAGE_READY);
		terminal.setEditable(false);
		console.setPromptText(MESSAGE_PROMPT);
		console.setOnKeyReleased(keyReleasedHandler);
	
		enterButton.setOnAction((event) -> {
			String text = console.getText();
			displayArea.appendText(text + System.lineSeparator()); // instead of appending the input text, input -> interpreter -> engine -> UIhandler 
			inputDisplayArea(text);
			console.clear();
		});
	}
	
	/**
	 * Handles the button action
	 */
	@FXML
	private void handleButtonAction() {
		String text = console.getText();
		displayArea.appendText(System.lineSeparator() + text);
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
	 * @param main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	private EventHandler<KeyEvent> keyReleasedHandler = new EventHandler<KeyEvent>() {
		public void handle(final KeyEvent keyEvent){
			if (keyEvent.getCode().equals(KeyCode.ENTER)) {
				String text = console.getText();
				displayArea.appendText(text + System.lineSeparator());
				console.clear();
			}
		}
	};
}
