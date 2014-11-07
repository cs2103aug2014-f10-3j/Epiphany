package Logic.Interpreter;

import java.io.*; 
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date; 
import java.util.TreeSet;

import Logic.Interpreter.DateInterpreter.*;
import Logic.Interpreter.CommandType.*; 
import Logic.Engine.*;
import Logic.Exceptions.*;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;

/**
 * This class parses the input from the user. The intepreter draws on several helper classes to accept simple
 * and natural English, parses it and passes it onto the Engine Class via the CommandType Interface. 
 * 
 * This class follows the Observer pattern by implementing deleteObserver.
 * @author Amit and Abdulla
 *
 */


public class EpiphanyInterpreter implements deleteObserver, editObserver{
	///all the string constants that are involved in displaying things to the user.
	private static final String MESSAGE_COMMAND_PROMPT = "command: ";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command!";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //dictionary
	Engine engine;
	Scanner input; //This scanner will deal with all input from user.
	UIHandler uiHandler; 

	public EpiphanyInterpreter() throws IOException, ParseException
	{
		engine = Engine.getInstance();
		input = new Scanner(System.in);
		uiHandler = UIHandler.getInstance();
		this.populateDictionary(); //adds an English dictionary
	}

	/**
	 * This is the main function which dictates the flow of the program. All the functionality is
	 * abstracted out to other methods.
	 * @param args which contains the file name (at index 0) entered by the user.
	 * @throws ParseException 
	 * @throws IOException
	 * @throws CancelDeleteException 
	 * @throws CancelEditException 
	 */
	public static void main(String[] args) throws IOException, ParseException, CancelEditException, CancelDeleteException {
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		interpreter.acceptUserInputUntilExit();
	}

	/**
	 * This method accepts the user inputs until the 'exit' command is entered. None of the actual
	 * operations are carried out in this function - all the operations are left to the 'route' function.
	 * @throws IOException 
	 * @throws CancelDeleteException 
	 * @throws CancelEditException 
	 */
	void acceptUserInputUntilExit() throws IOException, CancelEditException, CancelDeleteException {
		/*try {
			ConsoleReader console = new ConsoleReader();
			console.setPrompt("command: ");
			console.addCompleter(new StringsCompleter(this.getCommandsList()));
			String line = null;
			while ((line = console.readLine()) != null) {
				CommandType toPassToEngine;
				try {
					if(line.equals("clear")){
						console.clearScreen();
						continue;
					} else if(line.length()<2){
						throw new InvalidCommandException();
					}
					toPassToEngine = interpretCommand(line);
					assert(toPassToEngine != null);
					engine.executeCommand(toPassToEngine);
				} catch (InvalidCommandException e) {
					uiHandler.printToTerminal(MESSAGE_INVALID_COMMAND);
				}
				catch (ExitException e) {
					System.exit(0);
				}

			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				TerminalFactory.get().restore();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}*/
		String userInput;
		do{
			uiHandler.printToTerminal(MESSAGE_COMMAND_PROMPT, "inline");
			userInput = input.nextLine();
			CommandType toPassToEngine;
			try {
				toPassToEngine = interpretCommand(userInput);
				assert(toPassToEngine != null);
				engine.executeCommand(toPassToEngine);
			} catch (InvalidCommandException e) {
				uiHandler.printToTerminal(MESSAGE_INVALID_COMMAND);
			}
			catch (ExitException e) {
				System.exit(0);
			}
		} while(!userInput.equalsIgnoreCase("exit"));
		input.close();
	}

	private ArrayList<String> getCommandsList(){
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("delete");
		commands.add("edit");
		commands.add("search");
		commands.add("display");
		commands.add("exit");
		commands.add("undo");
		commands.add("redo");
		commands.add("-complete");
		return commands;
	}

	/*public void acceptUserInput(String userInput) throws IOException{
		CommandType toPassToEngine;
		try {
			toPassToEngine = interpretCommand(userInput);
			assert(toPassToEngine != null);
			engine.executeCommand(toPassToEngine);
		} catch (InvalidCommandException e) {
			uiHandler.printToTerminal(MESSAGE_INVALID_COMMAND);
		}
		catch (ExitException e) {
			System.exit(0);
		}
	}*/

	/**
	 * This function recognizes the type of input by user.
	 * @param userInput
	 * @return CommandType of the input.
	 * @throws InvalidCommandException 
	 * @throws ExitExeption 
	 */
	public CommandType interpretCommand(String userInput) throws InvalidCommandException, ExitException {
		userInput = userInput.trim();
		if(userInput.equalsIgnoreCase("undo")){
			return new UndoCommandType();
		} else if(userInput.equalsIgnoreCase("redo")){
			return new RedoCommandType();
		} else if(userInput.matches("(display|view|ls).*")){
			return interpretDisplayCommand(userInput);
		} else if(userInput.matches("(-m).*")){
			return interpretCompleteCommand(userInput);
		} else if(userInput.equals("exit")) {
			return exitProgram();
		} else if(userInput.matches("(search|find).*")) {
			return interpretSearchCommand(userInput);
		} else if(userInput.matches("(delete|remove).*")) {
			return interpretDeleteCommand(userInput);
		} else if(userInput.matches("(edit).*")) {
			return interpretEditCommand(userInput);
		} else { 
			return interpretAddCommand(userInput);
		} 
	}

	private CommandType interpretCompleteCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			throw new InvalidCommandException();
		}
		String completeMe = userInput.substring(userInput.indexOf(' ') + 1);
		if(completeMe.contains("#")){
			return new CompleteCommandType(completeMe.substring(0,completeMe.indexOf('#')-1),completeMe.substring(completeMe.indexOf('#')+1));
		}
		return new CompleteCommandType(completeMe);
	}

	private CommandType interpretEditCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			throw new InvalidCommandException();
		}
		String toEdit = userInput.substring(userInput.indexOf(' ') + 1); 
		if(toEdit.contains("#")){
			return new EditCommandType(toEdit.substring(0,toEdit.indexOf('#')-1),toEdit.substring(toEdit.indexOf('#')+1));
		}
		return new EditCommandType(toEdit);
	}

	/**
	 * This function returns the standardized display command according to CommandType Interface. 
	 * @param userInput
	 * @return DisplayCommandType
	 * @throws InvalidCommandException 
	 */
	private CommandType interpretDisplayCommand(String userInput) throws InvalidCommandException {
		String[] commandTokens = userInput.split(" ");

		if(commandTokens.length == 1){
			return new DisplayCommandType();
		}else if(commandTokens.length >= 2){
			if(commandTokens[1].startsWith("#")){
				if(commandTokens[1].substring(1).contains("#")){
					throw new InvalidCommandException();
				}
				return new DisplayCommandType(userInput.split("#")[1]);
			} else if(commandTokens[1].equalsIgnoreCase("all")){
				return new DisplayCommandType(commandTokens[1]);
			}
		}
		throw new InvalidCommandException();
	}

	/**
	 * Exits program. Duh
	 * @return CommandType(matters it does not, hmm)
	 * @throws ExitException 
	 */
	private CommandType exitProgram() throws ExitException {
		throw new ExitException();
	}

	/**
	 * This function returns the standardized search command according to CommandType Interface. 
	 * @param userInput
	 * @return SearchCommandType
	 * @throws InvalidCommandException 
	 */
	private CommandType interpretSearchCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			throw new InvalidCommandException();
		}
		String findMe = userInput.substring(userInput.indexOf(' ') + 1);
		if(findMe.contains("#")){
			return new SearchCommandType(findMe.substring(0,findMe.indexOf('#')-1),findMe.substring(findMe.indexOf('#')+1));
		}
		return new SearchCommandType(findMe);
	}

	private CommandType interpretDeleteCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			throw new InvalidCommandException();
		}
		String toDelete = userInput.substring(userInput.indexOf(' ') + 1); 
		if(toDelete.contains("#")){
			if(toDelete.startsWith("#")){
				return new DeleteCommandType(null, toDelete.substring(1));
			}
			return new DeleteCommandType(toDelete.substring(0,toDelete.indexOf('#')-1),toDelete.substring(toDelete.indexOf('#')+1));
		}
		return new DeleteCommandType(toDelete);
	}

	/**
	 * This function returns the standardized add command according to CommandType Interface. 
	 * @param userInput
	 * @return AddCommandType
	 * @throws InvalidCommandException 
	 */
	private CommandType interpretAddCommand(String userInput) throws InvalidCommandException {
		if(!userInput.startsWith("\"") && !isValidTask(userInput)){
			throw new InvalidCommandException();
		}
		String projectName = "";
		if(userInput.contains("#")){
			projectName = userInput.substring(userInput.indexOf('#')+1);
			if(projectName.equals("default")){
				throw new InvalidCommandException();
			}
			userInput = userInput.substring(0,userInput.indexOf('#')-1);
		}
		ArrayList<Date> dates = new ArrayList<Date>();
		String taskDescription;
		if(userInput.startsWith("\"")){
			try{
				taskDescription = userInput.substring(1,userInput.lastIndexOf('\"'));
				if(userInput.length()>userInput.lastIndexOf('\"')+1){
					parseDate(userInput.substring(userInput.lastIndexOf('\"')+1), dates);
				}
			} catch (Exception e){
				throw new InvalidCommandException();
			}
		} else{
			taskDescription = parseDate(userInput, dates);
		}
		assert(taskDescription!=null);
		if(dates.size()==0){
			if(projectName.equals("")){
				return new AddCommandType(taskDescription);
			} else{
				return new AddCommandType(taskDescription, null, projectName);
			}
		} else if(dates.size()==1){
			if(projectName.equals("")){
				return new AddCommandType(taskDescription, dates.get(0));
			} else{
				return new AddCommandType(taskDescription, dates.get(0), projectName);
			}

		} else{
			if(projectName.equals("")){
				return new AddCommandType(taskDescription, dates.get(0), dates.get(1));
			} else{
				return new AddCommandType(taskDescription, dates.get(0), dates.get(1), projectName);
			}		
		}
	}

	/**
	 * Checks if a task is gibberish
	 * @param string
	 */
	private boolean isValidTask(String taskDescription) {
		return actionWords.contains(taskDescription.split(" ")[0].toLowerCase());
	}

	/**
	 * Parses the dates.  
	 * @param string
	 * @return Date
	 * @throws InvalidCommandException 
	 */
	private String parseDate(String string, ArrayList<Date> d) throws InvalidCommandException {
		return strtotime.convert(string, d);
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
		dictScan.close();
	}


	/**
	 * When delete is called we will perform a search for the given key, tasks that contain this
	 * key will be enumerated to the user.
	 * This function asks the user for the index of the task to be deleted (from the enumerated list)
	 */
	public int[] askForAdditionalInformationForDelete() throws CancelDeleteException {
		String inputFromUser = input.nextLine();
		try{
			String[] stringIndeces = inputFromUser.split(",");
			int[] intIndeces = new int[stringIndeces.length];
			for (int i = 0; i < stringIndeces.length; i++) {
				intIndeces[i] = Integer.parseInt(stringIndeces[i]);
				if(intIndeces[i]<1){
					throw new NumberFormatException("Invalid input : Negative index");
				}
			}
			return intIndeces;
		} catch (NumberFormatException e){
			uiHandler.printToTerminal("You have entered an invalid number. Press y to try again, press n to cancel delete.");
			String userResponse = input.nextLine();
			if(userResponse.equalsIgnoreCase("y")){
				return askForAdditionalInformationForDelete();
			} else{
				throw new CancelDeleteException();
			}
		}
	}

	/**
	 * When edit is called we will perform a search for the given key, tasks that contain this
	 * key will be enumerated to the user.
	 * This function asks the user for the index of the task to be edited (from the enumerated list)
	 */
	public int askForAdditionalInformationForEdit() throws CancelEditException {
		String inputFromUser = input.nextLine();
		try{
			int indexFromUser = Integer.parseInt(inputFromUser);
			if(indexFromUser<1){
				throw new NumberFormatException("Invalid input : Negative index");
			}
			return indexFromUser;
		} catch (NumberFormatException e){
			uiHandler.printToTerminal("You have entered an invalid number. Press y to try again, press n to cancel edit.");
			String userResponse = input.nextLine();
			if(userResponse.equalsIgnoreCase("y")){
				return askForAdditionalInformationForEdit();
			} else{
				throw new CancelEditException();
			}
		}
	}

	/**
	 * When we have selected to task to be edited, we then delete it and add
	 * a new task in its place, this function accepts the new task from the user.
	 * @return CommandType
	 */
	public CommandType askForNewTaskForEdit() throws CancelEditException {
		String inputFromUser = input.nextLine();
		CommandType toPassToEngine;
		try {
			toPassToEngine = interpretAddCommand(inputFromUser);
			assert(toPassToEngine != null);
			return toPassToEngine;
		} catch (InvalidCommandException e) {
			uiHandler.printToTerminal("You have entered an invalid task. Press y to try again, press n to cancel edit.");
			String userResponse = input.nextLine();
			if(userResponse.equalsIgnoreCase("y")){
				return askForNewTaskForEdit();
			} else{
				throw new CancelEditException();
			}
		}
	}
}
