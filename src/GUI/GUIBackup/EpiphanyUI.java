/*package GUIBackup;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Logic.Interpreter.EpiphanyInterpreter;

public class EpiphanyUI extends JFrame {
	//INSTANCE VARIABLES
	public EpiphanyInterpreter interpreter;
	public static EpiphanyUI epiphanyUI;
	JLabel systemStatusLabel;
	JTextField userInputField;
	JTextArea displayArea;
	//STATIC VARIABLES
	private static final Font systemFont = new Font("consolas", Font.PLAIN, 14);
	private static final Font statusFont = new Font("consolas", Font.PLAIN, 11);
	private static final Font titleFont = new Font("consolas", Font.BOLD, 14);
	
	public static EpiphanyUI getInstance() throws IOException, ParseException{
		if(epiphanyUI==null){
			epiphanyUI = new EpiphanyUI();
		}
		return epiphanyUI;
	}

	public EpiphanyUI() throws IOException, ParseException {
		epiphanyUI = this;
		this.setVisible(true);
		interpreter = new EpiphanyInterpreter();
		setTitle("Epiphany");
		setSize(500, 650);
		setResizable(false);
		getContentPane().setBackground(Color.black);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);

		systemStatusLabel = setSystemStatusLabel();
		userInputField = setUserInputField();
		displayArea = setDisplayArea();

		JLabel titleLabel = new JLabel();
		titleLabel.setBackground(Color.black);
		titleLabel.setForeground(Color.LIGHT_GRAY);
		titleLabel.setFont(titleFont);
		titleLabel.setText("Epiphany");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(2, 2, 490, 30);
		add(titleLabel);

		this.changeSystemStatusTo("Welcome to Epiphany! Please enter a task.");
	}


	private JTextArea setDisplayArea() {

		JTextArea tArea = new JTextArea();
		tArea.setBackground(Color.black);
		tArea.setForeground(Color.LIGHT_GRAY);
		tArea.setLineWrap(true);
		tArea.setEditable(false);
		JScrollPane scrollPanel = new JScrollPane(tArea);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBounds(2, 32, 490, 520);
		scrollPanel.setBackground(Color.black);
		scrollPanel.setForeground(Color.white);

		scrollPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(scrollPanel);
		return tArea;
	}


	private JTextField setUserInputField() {

		JTextField userInputField = new JTextField();
		userInputField.setBounds(2,560,490,35);
		userInputField.setBackground(Color.black);
		userInputField.setForeground(Color.white);
		userInputField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		userInputField.addActionListener(new TextFieldListener());
		add(userInputField);
		userInputField.setFont(systemFont);
		return userInputField;
	}


	public void changeDisplayTo(String toDisplay){
		displayArea.append(toDisplay + "\n");
	}
	
	public void resetDisplay(){
		displayArea.setText("");
	}
	
	public void changeSystemStatusTo(String newStatus){
		systemStatusLabel.setText(newStatus);
	}

	public void changeSystemStatusTo(String newStatus, Color newColor){
		systemStatusLabel.setText(newStatus);
		systemStatusLabel.setForeground(newColor);
	}

	private JLabel setSystemStatusLabel() {
		JLabel systemStatusLabel = new JLabel();
		systemStatusLabel.setBounds(2,595,490,25);
		systemStatusLabel.setBackground(Color.LIGHT_GRAY);
		systemStatusLabel.setOpaque(true);
		systemStatusLabel.setForeground(Color.black);
		systemStatusLabel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(systemStatusLabel);
		systemStatusLabel.setFont(statusFont);
		return systemStatusLabel;
	}

	// The listener for the textfield.
	private class TextFieldListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String inputString = userInputField.getText();
			//displayArea.append(inputString + "\n");
			userInputField.setText("");
			try {
				interpreter.acceptUserInput(inputString);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}


	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				EpiphanyUI ex;
				try {
					ex = new EpiphanyUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
*/