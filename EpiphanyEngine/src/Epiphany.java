//This is how we roll
/* Epiphany Engine v0.1 alpha release
 * Project & Class under a separate class file
 * 
 * @author Moazzam & Wei Yang
 */

import java.util.*;

import com.sun.javafx.tk.Toolkit.Task;

public class Epiphany {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<String> projectNames;
	public static ArrayList<ArrayList<Task>> EpiphanyMain;
		
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

	public static void main(String[] args) {
		EpiphanyMain = new ArrayList<ArrayList<Task>>();
		projectNames = new ArrayList<String>();
		MLogic L1 = new MLogic();
		L1.addTask("we are still working", null, "CS");
		L1.addTask("we are really still working", null, "CS");
		L1.addTask("Hello there", null, "We rock");
		// L1.displayAll(EpiphanyMain);
		L1.displayProjects();
		L1.test();
		// L1.displayAL(EpiphanyMain.get(0));
		for (String s : projectNames) {
			System.out.println(s);
		}
		// L1.addTask("we ", null, null);

		L1.run();
	}
	
	public void test() {
		ArrayList<ArrayList<Task>> testing = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> partTest = new ArrayList<Task>();
		Task hello = new Task("hello");
		Task hello1 = new Task("hello there");
		Task hello2 = new Task("hello, how are you");
		Task hello3 = new Task("hello new ", "", "CS2103");
		partTest.add(hello);
		partTest.add(hello1);
		partTest.add(hello2);
		partTest.add(hello3);
		testing.add(partTest);
		displayAll(testing);
		displayProjects();
		//System.out.print(hello3.ProjectName);

		EpiphanyMain = testing;
		search("are");
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
	
	// add task
	public void addTask(String input, String date, String project) {
		
		if (date == null && project == null) {
			// Adds it to the default project
			EpiphanyMain.get(0).add(new Task(input));
		} else if (date == null && project != null) {
			// Finds the project name and appends the task in
			// THis would terminate right?
			for(String s: projectNames){
				int count = 1;
				if(s.equals(project)){
					EpiphanyMain.get(count).add(new Task(input, null, project));
				}
				else if(!s.equals(project)){
					count++;
				}
			// Create a new project if one does not exist
				projectNames.add(project);
				ArrayList<Task> latest = new ArrayList<Task>();
				latest.add(new Task(input, null, project));
				EpiphanyMain.add(latest);
				// Create a new file to store the new data
				try {
					createNewFile(project, latest);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// I have not yet incorporated the add with date.
			

		} else if (date != null){
			// add to default project with deadline
			for(int i = 0; i < projectNames.size(); i++){
				if(projectNames.get(i).equals(project)){
					EpiphanyMain.get(i).add(new Task(input, date, project));
				}
			}
			
		}
	}
	
	// find project
	public ArrayList<Task> findProject(String ProjectName) {
		return EpiphanyMain.contains(ProjectName);
	}
	
	// delete task
	public void deleteTask(Integer indexToDelete, String phrase) {
		Scanner sc = new Scanner(System.in;)
		int input = sc.nextInt();
		if ((indexToDelete == null) && this.getTaskList().contains(phrase)) {
			EpiphanyMain.search(phrase);
			System.out.println("Which task would you like to delete?");
			// accept input
			this.deleteTask(input, nill);
			
			
		} else if (indexToDelete != null) {
			
		}
	}
	
	//sort ()
	// sort by deadline
	
	
	

}
