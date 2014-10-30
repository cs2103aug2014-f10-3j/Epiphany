package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class GUIController {
	
	@FXML
	private TextArea textArea;
	private Main main;
	
	public GUIController() {
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
