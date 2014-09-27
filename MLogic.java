//This is how we roll
/* Epiphany Engine v0.1 alpha release
 * Contains basic functionality to CRUD as well as Storage. 
 * More details about the methods will be added soon
 * 
 * @author Moazzam & Wei Yang
 */


import java.util.*;
import java.io.*;

public class MLogic {
	// EpiphanyMain contains all the current projects which is stored in an array List
	public static ArrayList<ArrayList<Task>> EpiphanyMain;
	public static final String MESSAGE_WELCOME = " Welcome to TextBuddy++, your file, %s, is ready to use.";
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
	
	public static void main (String[] args) {
	 EpiphanyMain = new ArrayList<ArrayList<Task>>();
	MLogic L1 = new MLogic();
	L1.run();
	}
	public void run(){
		
	}
	/**
	 * Searches through the entire project for the given phrase.
	 * @param phrase 	the search phrase provided by the use
	 * @return			an ArrayList of tasks that match the given phrase
	 */
	public ArrayList<Task> search(String phrase) {
		ArrayList<Task> searchResult = new ArrayList<Task>(); // Would contain the list of tasks
															// that are in the search result

		for (int i = 0; i < EpiphanyMain.size(); i++) {// Searches the entire list
			for (int j = 0; j < EpiphanyMain.get(i).size(); j++){// Searches each project
				
			Task line = (EpiphanyMain.get(i)).get(j);
			if (line.instruction.contains(phrase)) {
				searchResult.add(line);
			}
		}
		if (searchResult.isEmpty()) {
			System.out.println(MESSAGE_INVALID_SEARCH);
		}
		display(searchResult);
		return searchResult;
	}
}




class Task {
	
	private String instruction;
	private String deadLine;
	private String ProjectName;
	private boolean isCompleted;

	public Task() {
		
	}
	
	//Accessors
	String getInstruction() {
		return this.instruction;
	}
	
	String getDeadline() {
		return this.deadLine;
	}
	
	//Mutators	
	void setInstruction(String newInstruction) {
		this.instruction = newInstruction;
	}
	
	void setDeadLine(String newDeadline) {
		this.deadLine = newDeadline;
	}
}
}
