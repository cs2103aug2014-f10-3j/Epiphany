//This is how we roll
/* Epiphany Engine v0.1 alpha release
 * Project & Class under a separate class file
 * 
 * @author Moazzam & Wei Yang
 */

import java.util.*;

public class Epiphany {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<ArrayList<Task>> EpiphanyMain;

	public static final String FILE_DEFAULT = "Default";
	public static final String FILE_DEADLINE = "Deadline";

	// Default list to contain all floating tasks without deadline
	Project Default = new Project(FILE_DEFAULT);

	// Default list to contain all tasks WITH deadline
	Project withDeadLine = new Project(FILE_DEADLINE);

	enum CommandTypes {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, SORT
	};

	public static final String MESSAGE_WELCOME = " Welcome to Epiphany, your file, %s, is ready to use.";
	public static final String MESSAGE_WRONG_ENTRY = "Wrong entry, please re-enter input.";
	public static final String MESSAGE_SORTED = "Tasks sorted alphabetically!";
	public static final String MESSAGE_DELETE_INVALID = " %s, is already empty, please re-enter command.";
	public static final String MESSAGE_NO_ENTRY = "No such entry exists in %s.";
	public static final String MESSAGE_DELETE = "Deleted from %s  %s.";
	public static final String MESSAGE_CLEAR_EMPTY = "%s ";
	public static final String MESSAGE_ADD = "Added to %s: %s.";
	public static final String MESSAGE_DISPLAY_ERROR = "No items to display.";
	public static final String MESSAGE_CLEAR = "All content deleted from %s.";
	public static final String MESSAGE_DISPLAY = "%d. %s";
	public static final String MESSAGE_EXIT = "Thank you for using %s, good bye!";
	public static final String MESSAGE_SORT = "All lines are now sorted.";
	public static final String MESSAGE_INVALID_SEARCH = "No results to display.";
	public static final String MESSAGE_PROVIDE_ARGUMENT = "Argument missing, please re-enter command.";

	public static void main(String[] args) throws Exception {
		//checkArguments(args);
		EpiphanyMain = new ArrayList<ArrayList<Task>>();
		Epiphany L1 = new Epiphany();
		L1.run();
	}

	void run() throws Exception {
		//printWelcomeMessage();
		//processAllCommands();
	}

	// basic input recognition from the program. User keys in a specific command,
	// and then it is processed accordingly.
	private static CommandTypes determineCommandType(String commandTypeString) {
		if (commandTypeString == null)
			throw new Error(ERROR_COMMAND_TYPE_NULL);

		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandTypes.ADD;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
			return CommandTypes.DISPLAY;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return CommandTypes.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("clear")) {
			return CommandTypes.CLEAR;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
			return CommandTypes.SEARCH;
		} else if (commandTypeString.equalsIgnoreCase("sort")) {
			return CommandTypes.SORT;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
			return CommandTypes.EXIT;
		} else {
			return null;
		}
	}


	/**
	 * Searches through the entire project for the given phrase.
	 * 
	 * @param phrase
	 *            the search phrase provided by the use
	 * @return an ArrayList of tasks that match the given phrase
	 */
	public ArrayList<Task> search(String phrase) {
		// Temp array list created to contain the list of tasks that are inside the search result
		ArrayList<Task> searchResult = new ArrayList<Task>(); 
		// Searches the entire list
		for (int i = 0; i < EpiphanyMain.size(); i++) {				
			// Searches for each project
			for (int j = 0; j < EpiphanyMain.get(i).size(); j++) {
				Task line = (EpiphanyMain.get(i)).get(j);
				if (line.getInstruction().contains(phrase)) {
					searchResult.add(line);
				}
			}
			if (searchResult.isEmpty()) {
				System.out.println(MESSAGE_INVALID_SEARCH);
			}
			display(searchResult);
		}
		return searchResult;
	}

	//Displays all projects
	public void displayAll(ArrayList<ArrayList<Task>> Epiphany){
		if(Epiphany.isEmpty()){
			System.out.println("Nothing to display.");
		}
		else{
			for(int i = 0; i < Epiphany.size(); i++){
				Task name = Epiphany.get(i).get(0);
				System.out.print("Project: " + i + name.getProjectName());
				int counter = 1;
				for(int j = 0; j < Epiphany.get(i).size(); j++){
					Task task = Epiphany.get(i).get(j);
					System.out.println(String.format(MESSAGE_DISPLAY, counter, task.getInstruction()));
					counter++;
				}
			}
		}
	}

	// Displays a specific project
	public ArrayList<Task> display(ArrayList<Task> lines) {
		if (lines.isEmpty()) {
			System.out.println(String.format(MESSAGE_DISPLAY_ERROR));
		} else {
			int counter = 1;
			for (Task task : lines) {
				System.out.println(String.format(MESSAGE_DISPLAY, counter, task.getInstruction()));
				counter++;
			}
		}
		return lines;
	}
}
