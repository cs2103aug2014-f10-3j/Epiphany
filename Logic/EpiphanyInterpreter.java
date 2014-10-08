import java.util.Date;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;

import DateInterpreter.*;
import CommandType.*;


public class EpiphanyInterpreter {
	///all the string constants that are involved in displaying things to the user.
	private static final String MESSAGE_COMMAND_PROMPT = "command: ";
	private static final String REGEX_ADD_COMMAND = ".*\\s(by|on)\\s.*";
	private static final String REGEX_SPLIT_ADD_COMMAND = "\\s(by|on)\\s(?!.*\\s(by|on)\\s)";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //dictionary
	private static final HashMap<Integer, String> months = new HashMap<Integer, String>();

	/**
	 * This is the main function which dictates the flow of the program. All the functionality is
	 * abstracted out to other methods.
	 * @param args which contains the file name (at index 0) entered by the user.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();	
		interpreter.populateDictionary(); // adds an english dictionary
		interpreter.acceptUserInputUntilExit();
	}

	/**
	 * This method accepts the user inputs until the 'exit' command is entered. None of the actual
	 * operations are carried out in this function - all the operations are left to the 'route' function.
	 */
	void acceptUserInputUntilExit() {
		Scanner input = new Scanner( System.in );
		String userInput;
		do{
			System.out.print(MESSAGE_COMMAND_PROMPT);
			userInput = input.nextLine();
			interpretCommand(userInput);
		} while(!userInput.equalsIgnoreCase("exit"));
		input.close();
	}

	/**
	 * This function recognizes the type of input by user.
	 * @param userInput
	 * @return CommandType of the input.
	 */
	private CommandType interpretCommand(String userInput) {
		if(userInput.matches("(display|view).*")){
			return interpretDisplayCommand(userInput);
		} else if(userInput.equals("exit")) {
			return exitProgram();
		} else if(userInput.matches("(search|find).*")) {
			return interpretSearchCommand(userInput);
		} else { 
			return interpretAddCommand(userInput);
		} 
	}

	/**
	 * This function returns the standardized display command according to CommandType Interface. 
	 * @param userInput
	 * @return DisplayCommandType
	 */
	private CommandType interpretDisplayCommand(String userInput) {
		String[] commandTokens = userInput.split(" ");

		if(commandTokens.length == 1){
			return new DisplayCommandType();
		}else if(commandTokens.length == 2){
			return new DisplayCommandType(commandTokens[1]);
		}
		return null;
	}

	/**
	 * Exits program. Duh
	 * @return CommandType(matters it does not, hmm)
	 */
	private CommandType exitProgram() {
		System.exit(0);
		return null;
	}

	/**
	 * This function returns the standardized search command according to CommandType Interface. 
	 * @param userInput
	 * @return SearchCommandType
	 */
	private CommandType interpretSearchCommand(String userInput) {
		String findMe = userInput.substring(userInput.indexOf(' ') + 1);   
		return new SearchCommandType(findMe);
	}

	/**
	 * This function returns the standardized add command according to CommandType Interface. 
	 * @param userInput
	 * @return AddCommandType
	 */
	private CommandType interpretAddCommand(String userInput) {
		if(userInput.matches(REGEX_ADD_COMMAND)){
			String[] tokens = userInput.split(REGEX_SPLIT_ADD_COMMAND);
			if(isValidTask(tokens[0])){
				Date date = parseDate(tokens[1]);
				return (date != null) ? new AddCommandType(tokens[0], date) : null;
			}

			return null;

		} else {
			//floating task
			return (isValidTask(userInput)) ? new AddCommandType(userInput) : null;
		}
	}

	/**
	 * Checks if a task is gibberish
	 * @param string
	 */
	private boolean isValidTask(String taskDescription) {
		return actionWords.contains(taskDescription.split(" ")[0]);
	}

	/**
	 * Parses the dates.  
	 * @param string
	 * @return Date
	 */
	private Date parseDate(String string) {
		return strtotime.convert(string);
	}

	/**
	 * This function draws on an English Dictionary to check if the action words of the user are legitimate.
	 */
	private void populateDictionary() throws FileNotFoundException{
		File dict = new File("dictionary.txt");
		Scanner dictScan = new Scanner(dict);
		while(dictScan.hasNextLine()){
			actionWords.add(dictScan.nextLine());
		}
	}
}
