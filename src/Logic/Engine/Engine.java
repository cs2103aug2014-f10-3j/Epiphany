package Logic.Engine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Logic.Interpreter.CommandType.*;

/**
 * This class can be instantiated to perform all the operations that the program
 * needs to perform.
 * 
 * All the tasks would be stored in different projects of type project. Within
 * these projects, we would have an ArrayList of Task which is used to store the
 * instruction, the date as well as the name of the project that it belongs to.
 * 
 * The interpreter would pass in the commands from the user and this class would
 * be used to store, modify and update the projects that the user creates.
 * 
 * It currently has the ability to add, delete, search and display.
 * Additionally, it has complete project management.
 * 
 * Missing: Storing projects in separate files (save).
 * 
 * @author Moazzam and Wei Yang
 *
 */
public class Engine {	

	public static ArrayList<String> projectNames; // to give quick access to list of projects
	public static ArrayList<Project> projectsList; 
	public static ArrayList<Date> testDate1;
	public static ArrayList<Task> testDateSort;
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
	private static final String ERROR_WRONG_INPUT = null;
	private static final String ERROR_COMMAND_TYPE_NULL = null;

	/**
	 * Enums are used for type safety.
	 * 
	 * @author Wei Yang
	 *
	 */
	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH
	};
		
	public static void main(String[] args) throws IOException, ParseException{
		//TEST METHOD
		
		Engine e = new Engine();
		Date test = new Date();
		
		test.setDate(11);
		test.setHours(22);
		test.setMinutes(21);
		test.setMonth(2);
		test.setSeconds(21);
		test.setYear(92);
		
		Task t = new Task("RULE THE WORLD", null, test, "default", false);
		
		e.projectsList.get(0).addTask(t);

	}

	public Engine() throws IOException, ParseException {
		run();
	}

	/**
	 * Initializes the Engine to begin running.
	 * Also, repopulates from existing text files.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private void run() throws IOException, ParseException {
		projectsList = new ArrayList<Project>(); 
		projectNames = new ArrayList<String>();  

		initializeEngine();
		
	}

	private void initializeEngine() throws IOException, FileNotFoundException,
			ParseException {
		//assume that projectNames exists.
		int noOfProjects = countLines("projectMasterList");
		
		if(noOfProjects == 0){
			
			createDefault(); 	//default project does not exist. need to create.
			
		}else{
			// there is atleast 1 project. Read in project names and populate tasks.
		
			populateProjectNames();
			populateProjectsWithTasks();
		}
	}

	private void createDefault() throws IOException {
		projectNames.add("default");
		projectsList.add(new Project("default", new ArrayList<Task>()));
		
		File file = new File("../Epiphany/src/Logic/Engine/projectMasterList.txt");
		FileWriter f = new FileWriter(file, true);
		BufferedWriter writer = new BufferedWriter(f);
		
		writer.write("default");
		writer.flush();
		writer.close();
	}

	private void populateProjectsWithTasks() throws FileNotFoundException,
			IOException, ParseException {
		for(String fileName : projectNames){
			ArrayList<Task> temp = new ArrayList<Task>();
			
			File f = new File("../Epiphany/src/Logic/Engine/Projects/" + fileName);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] taskComponents = line.split("~");
				
				String type = taskComponents[0];
				String description = taskComponents[1];
				Date from = parseDate(taskComponents[2]);
				Date to = parseDate(taskComponents[3]);
				String projName = taskComponents[4];
				boolean status = parseBool(taskComponents[5]);
				
				Task t = null;
				
				if(type.equals("deadline")){
					t = new Task(description, null, to, projName, status);
				}else if(type.equals("interval")){
					t = new Task(description, from, to, projName, status);
				}else if(type.equals("floating")){
					t = new Task(description, null, null, projName, status);
				}
				
				temp.add(t);
			}
			
			reader.close();
			
			Project p = new Project(fileName, temp);
			projectsList.add(p);
		}
	}

	private Date parseDate(String input) throws ParseException {
		Date date = new Date();
		
		if(!input.equals("null")){
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			date = sdf.parse(input);
		}else{
			return null;
		}
		
		return date;

		/*
		String[] components = input.split(" ");
		String dow = components[0];
		String month = components[1];
		int date = Integer.parseInt(components[2]);

		String[] time = components[3].split(":");
		int hour = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		int seconds = Integer.parseInt(time[2]);
		
		int year = Integer.parseInt(components[5]);
		*/		
	}

	private void populateProjectNames() throws FileNotFoundException,
			IOException {
		Scanner sc = new Scanner(new File("../Epiphany/src/Logic/Engine/projectMasterList.txt"));
		while(sc.hasNextLine()){
			projectNames.add(sc.nextLine());

		}
	}
	
	private boolean parseBool(String input) {
		return (input.equalsIgnoreCase("true")) ? true : false;
	}

	public static int countLines(String filename) throws IOException {
		File file =new File("../Epiphany/src/Logic/Engine/" + filename + ".txt");
	    int lineNumber = 0;
 
		if(file.exists()){

		    FileReader fr = new FileReader(file);
		    LineNumberReader lnr = new LineNumberReader(fr);


	        while (lnr.readLine() != null){
	        	lineNumber++;
	        }

	        lnr.close();
		}
		
        return lineNumber;
	}
	
	/**
	 * Takes a command type input, as is given by the interpreter and returns
	 * the appropriate case.
	 * 
	 * @param Input
	 *            from the interpreter that needs to be filtered.
	 * @return the type of command
	 */
	private CommandTypesEnum determineCommandType(CommandType commandType) {
		if (commandType == null)
			throw new Error(ERROR_COMMAND_TYPE_NULL);

		if (commandType.getType().equalsIgnoreCase("add")) {
			return CommandTypesEnum.ADD;
		} else if (commandType.getType().equalsIgnoreCase("display")) {
			return CommandTypesEnum.DISPLAY;
		} else if (commandType.getType().equalsIgnoreCase("delete")) {
			return CommandTypesEnum.DELETE;
		} else if (commandType.getType().equalsIgnoreCase("search")) {
			return CommandTypesEnum.SEARCH;
		} else {
			return null;
		}
	}
	
	/**
	 * Interpreter passes in a command type object. This method determines which
	 * type of command it is and uses the appropriate methods using the switch
	 * statements.
	 * 
	 * @param userCommand
	 *            is the command that the interpreter send in.
	 */
	public void executeCommand(CommandType userCommand) {
		CommandTypesEnum commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD:
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			addTask(addUserCommand.getDescription(), addUserCommand.getDate(),
					addUserCommand.getProjectName());
			break;
		case DISPLAY: // displays the entire ArrayList
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			// display(displayUserCommand.getModifiers()); I dont know what this
			// is???
			displayAll();
			break;
		case DELETE:
			DeleteCommandType deleteUserCommand = (DeleteCommandType) userCommand;
			deleteTask(deleteUserCommand.getTaskDescription(),
					deleteUserCommand.getProjectName());
			break;
		case SEARCH:
			SearchCommandType searchUserCommand = (SearchCommandType) userCommand;
			search(searchUserCommand.getTaskDescription(),
					searchUserCommand.getProjectName());
			break;

		default:
			// throw an error if the command is not recognized
			throw new Error(ERROR_WRONG_INPUT);
		}
	}
	
	
	/*******************************OLDER METHODS*****************************************/
	
	private boolean checkContains(int i, String projectName) {
		if (projectsList.get(i).getProjectName().equals(projectName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function helps to search through a project to return all the Task's
	 * that positively match the given phrase.
	 * 
	 * @param phrase
	 *            Is a phrase that the user intends to search for
	 * @param projectName
	 *            is the name of the project that the user specifies
	 * @return an ArrayList of the Tasks that matched the search phrase.
	 */
	private ArrayList<Task> search(String phrase, String projectName) {
		ArrayList<Task> searchResult = new ArrayList<Task>();
		ArrayList<Task> temp;

		for (int i = 0; i < projectsList.size(); i++) {
			if (checkContains(i, projectName)) {
				temp = projectsList.get(i).getTaskList();
				for (int j = 0; j < temp.size(); j++) {
					if (temp.get(j).getInstruction().toLowerCase()
							.contains(phrase.toLowerCase())) {
						searchResult.add(temp.get(j));
					} else {
						continue;
					}
				}
				if (searchResult.isEmpty()) {
					System.out.println(MESSAGE_INVALID_SEARCH);

				}
				System.out.println("Search result:"); // UI handler
				displayArrayList(searchResult);
			}
		}
		return searchResult;
	}

	/**
	 * A function that helps to display all the projects including all the tasks
	 * stored within them.
	 */
	public void displayAll() {
		if (projectsList.size() == 1
				&& projectsList.get(0).getTaskList().isEmpty()) {
			System.out.println("Nothing to display.");// UI handler
		} else {
			for (int i = 0; i < projectsList.size(); i++) {
				Project curr = projectsList.get(i);
				System.out.println("Project: " + "\n" + (i + 1) + ". "
						+ curr.getProjectName() + ":");
				ArrayList<Task> currentArrayList = curr.getTaskList();

				if (currentArrayList.isEmpty()) {
					System.out.println("IT IS EMPTY"); // UI handler
				} else {
					for (int k = 0; k < currentArrayList.size(); k++) {
						Task currTask = currentArrayList.get(k);
						// should i try a try-catch block

						Date currDate = currTask.getDeadline();
						// Stem.out.println(currDate);
						System.out.println("\t" + (k + 1) + ". "
								+ currTask.getInstruction() + "\t" + currDate);

					}
				}
			}
		}
	}

	/**
	 * This function helps to output an ArrayList of tasks. It is currently
	 * being used by the search function to print out the tasks that contain the
	 * search phrase.
	 * 
	 * @param expected
	 *            an ArrayList to be displayed to the user.
	 */
	private void displayArrayList(ArrayList<Task> expected) {

		if (expected.isEmpty()) {
			System.out.println(String.format(MESSAGE_DISPLAY_ERROR));
		} else {

			int counter = 1;
			for (Task s : expected) {
				System.out.println(String.format(MESSAGE_DISPLAY, counter,
						s.getInstruction()));
				counter++;
			}
		}
	}

	/**
	 * Displays all the tasks within any one of the projects.
	 * 
	 * @param name
	 *            The name of the project that we wish to display.
	 */
	public void displayProject(Project tempName) {
		for (int i = 0; i < projectsList.size(); i++) {
			String nameTempProject = tempName.getProjectName().toLowerCase();
			String abc = projectsList.get(i).getProjectName().toLowerCase();
			if (abc.equals(nameTempProject)) {
				ArrayList<Task> temporaryProjectList = projectsList.get(i)
						.getTaskList();
				displayArrayList(temporaryProjectList);
			} else {
				System.out.println("No such project exists."); // UI handler
			}
		}
	}

	/**
	 * Displays the names and indices of all the projects that exist.
	 */
	public void displayProjects() {
		int count = 1;
		if (projectNames.isEmpty()) {
			System.out.println("There are currently no projects.");
		} else {
			for (String s : projectNames) {
				System.out.println(count + ". " + s + ".");
				count++;
			}
		}
	}

	private void createNewProject(String projectName, String instruction,
			Date date) {
		projectNames.add(projectName);
		ArrayList<Task> latest = new ArrayList<Task>();
		if (date == null) {
			latest.add(new Task(instruction, projectName));
		} else if (date != null) {
			latest.add(new Task(instruction, date, projectName));
		}
		projectsList.add(new Project(projectName, latest));
		System.out.println("New project created");
		System.out.println("Task has been added!");
	}

	private void save(Project toBeUpdated) {
	}

	private void save() {
	}

	private void deleteTask(String instruction, String projectName) {
		if (!projectName.contains(projectName)) {
			System.out.println("Such a project does not exist");
		} else {
			for (int i = 0; i < projectsList.size(); i++) {
				String current = projectsList.get(i).getProjectName()
						.toLowerCase();
				if (current.contains(projectName.toLowerCase())) {
					ArrayList<Task> currTaskList = projectsList.get(i)
							.getTaskList();

					for (int j = 0; j < currTaskList.size(); j++) {
						if (currTaskList.contains(instruction)) {
							currTaskList.remove(j);
							System.out.print("Removed successfully");
						}
					}
				}

			}

		}
	}

	/**
	 * Finds the project are removes it entirely.
	 * 
	 * @param projectName
	 *            is the name of the project.
	 */
	public void deleteProject(ArrayList<Task> projectName) {
		if (projectsList.contains(projectName)
				&& projectNames.contains(projectName)) {
			int count = projectsList.lastIndexOf(projectName);
			int count1 = projectNames.lastIndexOf(projectName);
			projectsList.remove(count);
			projectNames.remove(count1);
		}
	}

	// sorts by deadline. meaning the phrase with the earliest date will show
	// first.
	// date format is quite crude for now, can be polymorphised later.
	// for now date is sorted according to DDMMYYYY
	// will specify to include time as well. TTTT to settle the cases of tasks
	// on the same day.

	/**
	 * This function helps to format the date.
	 * 
	 * @param dateStr
	 *            String that contains the date
	 * @return formatted date.
	 */
	public Date formatDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Sorts the date.
	 * 
	 * @param testDate
	 *            An ArryList of date that needs to be sorted.
	 */
	public void sortDateInList(ArrayList<Date> testDate) {
		Collections.sort(testDate, new customComparator());
	}

	/**
	 * Helps to sort the tasks within a project
	 * 
	 * @param project
	 *            The arrayList for a project that we update.
	 * @return a project's taskList sorted by Date.
	 */
	private ArrayList<Task> sortDateInProject(ArrayList<Task> project) {
		Collections.sort(project, new taskDateComparator());
		return project;

	}

	/**
	 * This method helps compare two tasks by basing it on their deadlines.
	 * 
	 * @author Moazzam
	 *
	 */
	private class taskDateComparator implements Comparator<Task> {
		public int compare(Task one, Task two) {
			return one.getDeadline().compareTo(two.getDeadline());
		}
	}

	/**
	 * This method helps compare two dates.
	 * 
	 * @author Moazzam
	 *
	 */
	private class customComparator implements Comparator<Date> {
		public int compare(Date one, Date two) {
			return one.compareTo(two);
		}
	}

	/**
	 * Helps to format the date and return the formatted date.
	 * 
	 * @param deadLine
	 * @return the formatted date.
	 */
	public String toString(Date deadLine) {
		String dateFormat = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String stringDate = sdf.format(deadLine);
		return stringDate;
	}

	

	

	private String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}

	public String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	public void printToUser(String text, String arg1, String arg2) {
		String printText = String.format(text, arg1, arg2);
		System.out.print(printText);
	}

}
