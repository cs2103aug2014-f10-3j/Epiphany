//@author A0118905A
package Logic.Interpreter;

import java.io.*; 
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date; 
import java.util.TreeSet;

import Logic.Interpreter.DateInterpreter.*;
import Logic.CommandType.*; 
import Logic.Engine.*;
import Logic.Exceptions.*;
import jline.TerminalFactory;
import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;

/**
 * This class contains the main method to run Epiphany. It accepts input from users and parses the input and subsequently
 * creates a CommandType(command pattern) and passes it to the Engine class which then executes to command appropriately.
 * This process loops until the user enters 'exit'.
 * 
 * This class follows the Observer pattern by implementing deleteObserver and editObserver.
 */
public class EpiphanyInterpreter implements deleteObserver, editObserver{
	///all the string constants that are involved in displaying things to the user.
	private static final String MESSAGE_COMMAND_PROMPT = "command: ";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command!";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //Contains a list of supported English words.
	//References to objects that we will need to perform commands that the user enters.
	Engine engine;
	Scanner input; //This scanner will deal with all input from user.
	ConsoleReader console;
	UIHandler uiHandler; 

	public EpiphanyInterpreter() throws IOException, ParseException
	{
		engine = Engine.getInstance();
		input = new Scanner(System.in);
		console = new ConsoleReader();
		uiHandler = UIHandler.getInstance();
		this.populateDictionary(); //populates the actionWords TreeSet with valid English words.
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
	 * operations are carried out in this function - all the operations are left to the Engine.
	 * @throws IOException 
	 * @throws CancelDeleteException 
	 * @throws CancelEditException 
	 * @throws ParseException 
	 */
	void acceptUserInputUntilExit() throws IOException, CancelEditException, CancelDeleteException, ParseException {
		try {
			engine.executeCommand(new DisplayCommandType());
			console.setPrompt(MESSAGE_COMMAND_PROMPT);
			console.addCompleter(new StringsCompleter(this.getCommandsList()));
			String line = null;
			while ((line = console.readLine()) != null) {
				CommandType toPassToEngine;
				try {
					if(line.length()<2){
						throw new InvalidCommandException();
					}
					toPassToEngine = interpretCommand(line);
					assert(toPassToEngine != null);
					console.setPrompt("");
					console.clearScreen();
					console.setPrompt(MESSAGE_COMMAND_PROMPT);
					//console.flush();
					engine.executeCommand(toPassToEngine);
					//console.flush();
					if(!toPassToEngine.getType().equals("display") && !toPassToEngine.getType().equals("search")){
						uiHandler.printToTerminal("");
						engine.executeCommand(new DisplayCommandType());
					}
				} catch (InvalidCommandException e) {
					console.setPrompt("");
					console.clearScreen();
					console.setPrompt(MESSAGE_COMMAND_PROMPT);
					uiHandler.printToDisplayRed(MESSAGE_INVALID_COMMAND);
					uiHandler.printToTerminal("");
					engine.executeCommand(new DisplayCommandType());
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
		}
		/*String userInput;
		do{
			uiHandler.resetToDefault();
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
		input.close();*/
	}

	/**
	 * This returns a list of commands that the system supports, which will be used for auto-complete purposes.
	 * @return ArrayList of supported commands (String)
	 */
	private ArrayList<String> getCommandsList(){
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("delete");
		commands.add("edit");
		commands.add("search");
		commands.add("display");
		commands.add("exit");
		commands.add("undo");
		commands.add("redo");
		commands.add("reset");
		commands.add("-m");
		return commands;
	}

	/**
	 * This function recognizes the type of input by user, and then redirects to the appropriate function to
	 * handle interpretation of that typr of command.
	 * @param userInput
	 * @return CommandType of the input.
	 * @throws InvalidCommandException 
	 * @throws ExitExeption 
	 */
	public CommandType interpretCommand(String userInput) throws InvalidCommandException, ExitException {
		userInput = userInput.trim();
		if(userInput.equalsIgnoreCase("undo")||userInput.equalsIgnoreCase("-u")){
			return new UndoCommandType();
		} else if(userInput.equalsIgnoreCase("redo")||userInput.equalsIgnoreCase("-r")){
			return new RedoCommandType();
		} else if(userInput.equalsIgnoreCase("reset")){
			return new ResetCommandType();
		} else if(userInput.matches("(display|view|-ls).*")){
			return interpretDisplayCommand(userInput);
		} else if(userInput.matches("(-m).*")){
			return interpretCompleteCommand(userInput);
		} else if(userInput.equals("exit")) {
			return exitProgram();
		} else if(userInput.matches("(search|find|-f).*")) {
			return interpretSearchCommand(userInput);
		} else if(userInput.matches("(delete|remove|-rm).*")) {
			return interpretDeleteCommand(userInput);
		} else if(userInput.matches("(edit|-e).*")) {
			return interpretEditCommand(userInput);
		} else { 
			return interpretAddCommand(userInput);
		} 
	}

	/**
	 * This function handles to interpretation of 'complete task' commands and returns the standardized complete command according to CommandType Interface.
	 * @param userInput
	 * @return
	 * @throws InvalidCommandException
	 */
	private CommandType interpretCompleteCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			//the complete command must have some modifiers. ie "-m" would be invalid.
			throw new InvalidCommandException();
		}
		//the modifier is the task description (can be a part of) of the task to mark as completed.
		String completeMe = userInput.substring(userInput.indexOf(' ') + 1);
		if(completeMe.contains("#")){
			//if the command contains # it means that the user has specified a particular project to carry this command out in.
			if(completeMe.substring(completeMe.indexOf('#')+1).contains("#")){
				//the command can have only one project (and by extension only one #).
				throw new InvalidCommandException();
			}
			return new CompleteCommandType(completeMe.substring(0,completeMe.indexOf('#')-1),completeMe.substring(completeMe.indexOf('#')+1));
		}
		return new CompleteCommandType(completeMe);
	}

	/**
	 * This function handles to interpretation of 'edit task' commands and returns the standardized edit command according to CommandType Interface.
	 * @param userInput
	 * @return
	 * @throws InvalidCommandException
	 */
	private CommandType interpretEditCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			//the edit command must have some modifiers. ie "edit" would be invalid.
			throw new InvalidCommandException();
		}
		//the modifier is the task description (can be a part of) of the task to edit.
		String toEdit = userInput.substring(userInput.indexOf(' ') + 1); 
		if(toEdit.contains("#")){
			//if the command contains # it means that the user has specified a particular project to carry this command out in.
			if(toEdit.substring(toEdit.indexOf('#')+1).contains("#")){
				//the command can have only one project (and by extension only one #).
				throw new InvalidCommandException();
			}
			return new EditCommandType(toEdit.substring(0,toEdit.indexOf('#')-1),toEdit.substring(toEdit.indexOf('#')+1));
		}
		return new EditCommandType(toEdit);
	}

	/**
	 * This function handles to interpretation of 'display' commands and returns the standardized display command according to CommandType Interface.
	 * @param userInput
	 * @return DisplayCommandType
	 * @throws InvalidCommandException 
	 */
	@SuppressWarnings("deprecation")
	private CommandType interpretDisplayCommand(String userInput) throws InvalidCommandException {
		String[] commandTokens = userInput.split(" ");
		if(commandTokens.length == 1){
			// if the user enters just "display", he wants to display all tasks across all projects and times.
			return new DisplayCommandType();
		}else if(commandTokens.length >= 2){
			if(commandTokens[1].startsWith("#")){
				// if the user enters just "display #projectName", he wants to display all tasks in a particular project.
				if(commandTokens[1].substring(1).contains("#")){
					//the command can have only one project (and by extension only one #).
					throw new InvalidCommandException();
				}
				return new DisplayCommandType(userInput.split("#")[1]);
			} else if(commandTokens[1].equalsIgnoreCase("all")){
				// if the user enters just "display all", he wants to display all tasks across all projects and times.
				return new DisplayCommandType(commandTokens[1]);
			} else{
				// if the user enters just "display some date", he wants to display all tasks on a particular date.
				ArrayList<Date> displayDate = new ArrayList<Date>();
				parseDate(" by "+userInput.substring(userInput.indexOf(' ')+1), displayDate);
				if(displayDate.size()>=1){
					return new DisplayCommandType(String.format("%d-%d-%d", displayDate.get(0).getDate(),displayDate.get(0).getMonth(),displayDate.get(0).getYear()+1900));
				}
			}
		}
		throw new InvalidCommandException();
	}

	/**
	 * Exits the program.
	 * @return CommandType(this function will never return anything).
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
			//the search command must have some modifiers. ie "search" would be invalid.
			throw new InvalidCommandException();
		}
		//the modifier is the task description (can be a part of) of the task to search for.
		String findMe = userInput.substring(userInput.indexOf(' ') + 1);
		if(findMe.contains("#")){
			if(findMe.startsWith("#")){
				//the search command must have some modifiers. ie "search #projectName" would be invalid.
				throw new InvalidCommandException();
			}
			//if the command contains # it means that the user has specified a particular project to carry this command out in.
			if(findMe.substring(findMe.indexOf('#')+1).contains("#")){
				//the command can have only one project (and by extension only one #).
				throw new InvalidCommandException();
			}
			return new SearchCommandType(findMe.substring(0,findMe.indexOf('#')-1),findMe.substring(findMe.indexOf('#')+1));
		}
		return new SearchCommandType(findMe);
	}

	private CommandType interpretDeleteCommand(String userInput) throws InvalidCommandException {
		if(userInput.indexOf(' ')==-1){
			//the delete command must have some modifiers. ie "delete" would be invalid.
			throw new InvalidCommandException();
		}
		//the modifier is the task description (can be a part of) of the task to search for.
		String toDelete = userInput.substring(userInput.indexOf(' ') + 1); 
		if(toDelete.contains("#")){
			//if the command contains # it means that the user has specified a particular project to carry this command out in.
			if(toDelete.substring(toDelete.indexOf('#')+1).contains("#")){
				//the command can have only one project (and by extension only one #).
				throw new InvalidCommandException();
			}
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
			//unless the task description is within double quotes, the first word should be a valid English word.
			throw new InvalidCommandException();
		}
		String projectName = "";
		if(userInput.contains("#")){
			//if the command contains # it means that the user has specified a particular project to add this task to.
			if(userInput.substring(userInput.indexOf('#')+1).contains("#")){
				//a task can be added to only one project.
				throw new InvalidCommandException();
			}
			projectName = userInput.substring(userInput.indexOf('#')+1);
			if(projectName.equals("default")){
				//a user can't explicitly add things to the "default" project. that is reserved for task without any project.
				throw new InvalidCommandException();
			}
			userInput = userInput.substring(0,userInput.indexOf('#')-1);
		}
		if(userInput.length()<2){
			//there can be no task descriptions smaller that 3 letters.
			throw new InvalidCommandException();
		}
		ArrayList<Date> dates = new ArrayList<Date>();
		String taskDescription;
		if(userInput.startsWith("\"")){
			//it is possible that the user has entered the task description without double quotes. The command would be invalid if there
			//is no closing quote.
			try{
				taskDescription = userInput.substring(1,userInput.lastIndexOf('\"'));
				if(userInput.length()>userInput.lastIndexOf('\"')+1){
					parseDate(userInput.substring(userInput.lastIndexOf('\"')+1), dates);
				}
			} catch (Exception e){
				throw new InvalidCommandException();
			}
		} else{
			//parseDate adds the dates the user entered in his command to the dates ArrayList and additionally returns the appropriate
			//task description after removing the parsed dates.
			taskDescription = parseDate(userInput, dates);
		}
		//task desciption can never be null at this point. parseDate throws an InvalidCommandException that does not allow this.
		assert(taskDescription!=null);
		if(dates.size()==0){
			//user has entered a floating task.
			if(projectName.equals("")){
				return new AddCommandType(taskDescription);
			} else{
				return new AddCommandType(taskDescription, null, projectName);
			}
		} else if(dates.size()==1){
			//the user has entered a deadline task.
			if(projectName.equals("")){
				return new AddCommandType(taskDescription, dates.get(0));
			} else{
				return new AddCommandType(taskDescription, dates.get(0), projectName);
			}

		} else{
			//user has entered a timed task.
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
		//if the first word is not a valid English word, then it is considered gibberish.
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
	 * This function asks the user for the indexes of the tasks to be deleted (from the enumerated list)
	 * @throws IOException 
	 */
	public int[] askForAdditionalInformationForDelete() throws CancelDeleteException, IOException {
		String inputFromUser = console.readLine();
		try{
			//the user can enter multiple indexed separated by commas, the system will delete them all.
			String[] stringIndeces = inputFromUser.split(",");
			int[] intIndeces = new int[stringIndeces.length];
			for (int i = 0; i < stringIndeces.length; i++) {
				intIndeces[i] = Integer.parseInt(stringIndeces[i].trim());
				if(intIndeces[i]<1){
					throw new NumberFormatException("Invalid input : Negative index");
				}
			}
			console.clearScreen();
			return intIndeces;
		} catch (NumberFormatException e){
			//if the user enters an invalid number (or something other than the number), then we ask if he wants to try again.
			uiHandler.printToTerminal("You have entered an invalid number. Press y to try again, press n to cancel.");
			String userResponse = console.readLine();
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
	public int askForAdditionalInformationForEdit() throws CancelEditException, IOException {
		String inputFromUser = console.readLine();
		try{
			int indexFromUser = Integer.parseInt(inputFromUser);
			if(indexFromUser<1){
				throw new NumberFormatException("Invalid input : Negative index");
			}
			console.clearScreen();
			return indexFromUser;
		} catch (NumberFormatException e){
			//if the user enters an invalid number (or something other than the number), then we ask if he wants to try again.
			uiHandler.printToTerminal("You have entered an invalid number. Press y to try again, press n to cancel edit.");
			String userResponse = console.readLine();
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
	 * @throws IOException 
	 */
	public CommandType askForNewTaskForEdit() throws CancelEditException, IOException {
		String inputFromUser = console.readLine();
		CommandType toPassToEngine;
		try {
			toPassToEngine = interpretAddCommand(inputFromUser);
			assert(toPassToEngine != null);
			return toPassToEngine;
		} catch (InvalidCommandException e) {
			//if the user enters an invalid task , then we ask if he wants to try again.
			uiHandler.printToTerminal("You have entered an invalid task. Press y to try again, press n to cancel edit.");
			String userResponse = console.readLine();
			if(userResponse.equalsIgnoreCase("y")){
				return askForNewTaskForEdit();
			} else{
				throw new CancelEditException();
			}
		}
	}
}
