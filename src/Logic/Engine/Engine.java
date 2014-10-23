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

import sun.org.mozilla.javascript.internal.Interpreter;
import Logic.Exceptions.CancelDeleteException;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.Interpreter.UIHandler;
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
 * @author Moazzam and Wei Yang
 *
 */
public class Engine {

	/********************** Declaration of constants and variables ***********************************/

	public static ArrayList<String> projectNames;
	public static ArrayList<Project> projectsList;
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
	private static EpiphanyInterpreter interp;

	/**
	 * Enums are used for type safety.
	 * 
	 * @author Wei Yang
	 *
	 */
	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, EDIT
	};

	public Engine() throws IOException, ParseException {
		run();
	}

	/********************** Run and Engine Initialization Methods ***********************************/

	/**
	 * Initializes the Engine to begin running. Also, repopulates from existing
	 * text files.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	private void run() throws IOException, ParseException {
		projectsList = new ArrayList<Project>();
		projectNames = new ArrayList<String>();
		initializeEngine();
		interp = new EpiphanyInterpreter();
	}

	private void initializeEngine() throws IOException, FileNotFoundException,
			ParseException {
		// assume that projectNames exists.
		int noOfProjects = countLines("projectMasterList");

		if (noOfProjects == 0) {

			createDefault(); // default project does not exist. need to create.

		} else {
			// there is atleast 1 project. Read in project names and populate
			// tasks.

			populateProjectNames();
			populateProjectsWithTasks();
		}
	}

	private void createDefault() throws IOException {
		projectNames.add("default");
		projectsList.add(new Project("default", new ArrayList<Task>()));

		File file = new File(
				"../Epiphany/src/Logic/Engine/projectMasterList.txt");
		FileWriter f = new FileWriter(file, true);
		BufferedWriter writer = new BufferedWriter(f);

		writer.write("default");
		writer.flush();
		writer.close();
	}

	private void populateProjectsWithTasks() throws FileNotFoundException,
			IOException, ParseException {
		for (String fileName : projectNames) {
			ArrayList<Task> temp = new ArrayList<Task>();

			File f = new File("../Epiphany/src/Logic/Engine/Projects/"
					+ fileName);
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

				if (type.equals("deadline")) {
					t = new Task(description, null, to, projName, status);
				} else if (type.equals("interval")) {
					t = new Task(description, from, to, projName, status);
				} else if (type.equals("floating")) {
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

		if (!input.equals("null")) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy");
			date = sdf.parse(input);
		} else {
			return null;
		}

		return date;

		/*
		 * String[] components = input.split(" "); String dow = components[0];
		 * String month = components[1]; int date =
		 * Integer.parseInt(components[2]);
		 * 
		 * String[] time = components[3].split(":"); int hour =
		 * Integer.parseInt(time[0]); int min = Integer.parseInt(time[1]); int
		 * seconds = Integer.parseInt(time[2]);
		 * 
		 * int year = Integer.parseInt(components[5]);
		 */
	}

	private void populateProjectNames() throws FileNotFoundException,
			IOException {
		Scanner sc = new Scanner(new File(
				"../Epiphany/src/Logic/Engine/projectMasterList.txt"));
		while (sc.hasNextLine()) {
			projectNames.add(sc.nextLine());

		}
	}

	private boolean parseBool(String input) {
		return (input.equalsIgnoreCase("true")) ? true : false;
	}

	public static int countLines(String filename) throws IOException {
		File file = new File("../Epiphany/src/Logic/Engine/" + filename
				+ ".txt");
		int lineNumber = 0;

		if (file.exists()) {

			FileReader fr = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fr);

			while (lnr.readLine() != null) {
				lineNumber++;
			}

			lnr.close();
		}

		return lineNumber;
	}

	/********************** Command Type Filter ***********************************/

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

	public void executeCommand(CommandType userCommand) throws IOException {
		CommandTypesEnum commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD:// METHOD DONE
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			addTask(addUserCommand.getDescription(),
					addUserCommand.getDateFrom(), addUserCommand.getDateTo(),
					addUserCommand.getProjectName());
			break;
		case DISPLAY:
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			display(displayUserCommand.getModifiers());
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

		case EDIT: // WY WORKING ON THIS
			EditCommandType editUserCommand = (EditCommandType) userCommand;
			edit();
			break;

		default:
			// throw an error if the command is not recognized
			throw new Error(ERROR_WRONG_INPUT);
		}
	}

	/********************** Add Methods ***********************************/

	/**
	 * Interpreter passes in a command type object. This method determines which
	 * type of command it is and uses the appropriate methods using the switch
	 * statements.
	 * 
	 * @param userCommand
	 *            is the command that the interpreter send in.
	 * @throws IOException
	 */
	private void addTask(String taskDescription, Date dateFrom, Date dateTo,
			String projectName) throws IOException {
		if (projectNames.contains(projectName)) {
			// Existing project
			int index = findIndex(projectName);
			Project currProject = projectsList.get(index);
			currProject.addTask(new Task(taskDescription, dateFrom, dateTo,
					projectName, false));
			projectsList.set(index, currProject);
		} else {
			// default project
			if (projectName.equals("default")) {
				int index = 0;
				Project currProject = projectsList.get(index);
				currProject.addTask(new Task(taskDescription, dateFrom, dateTo,
						projectName, false));
				projectsList.set(index, currProject);
			}
			// create a new project
			else {
				projectNames.add(projectName);
				ArrayList<Task> temp = new ArrayList<Task>();
				temp.add(new Task(taskDescription, dateFrom, dateTo,
						projectName, false));
				projectsList.add(new Project(projectName, temp));
			}
		}
	}

	private int findIndex(String projectName) {
		int index = 0;
		for (Project p : projectsList) {
			if (!p.getProjectName().equals(projectName)) {
				index++;
			}
		}
		return index;
	}

	/********************** Delete Methods ********************************/

	private void deleteTask(String taskDescription, String projectName) {

		if (projectNames.contains(projectName)) {
			search(taskDescription);
		}

	}

	private void deleteTask(String taskDescription)
			throws CancelDeleteException, IOException {

		ArrayList<Task> temp = search(taskDescription);
		if (!temp.isEmpty()) {
			// displayArrayList(temp);
			// }
			int counter = 1;
			for (Task t : temp) {
				UIHandler.getInstance().printToDisplay(
						counter + ". " + t.printTaskForDisplay());
				counter++;
			}
			// Interpreter inter = new Interpreter();
			UIHandler.getInstance().printToDisplay(
					"Please enter the index number");
			int input = interp.askForAdditionalInformation();
			// Looks for the index and removes it
			Task taskToBeDeleted = temp.get(input - 1);
			String projectName = taskToBeDeleted.getProjectName();
			int indexProject = findIndex(projectName);

			Project currProject = projectsList.get(indexProject);
			currProject.deleteTask(taskToBeDeleted);
			ArrayList<Task> currList = currProject.displayAllTasks();
			if (currList.isEmpty()) {
				UIHandler.getInstance().printToDisplay(
						currProject.getProjectName() + "has been removed. ");
				projectsList.remove(indexProject);
			} else {
				UIHandler.getInstance().printToDisplay(
						taskToBeDeleted.getTaskDescription()
								+ "has been removed. ");
			}
		} else {
			UIHandler.getInstance().printToDisplay("No such task exists!");
		}
	}

	/**********************
	 * Edit Methods
	 * 
	 * @throws IOException
	 ***********************************/
	// convert to a task
	private void edit(String old, String edited) throws IOException {
		for (Project p : projectsList) {
			for (Task t : p.displayAllTasks()) {
				if (t.getTaskDescription().equals(old)) {
					Task updated = new Task(edited, t.getStartDate(),
							t.getDeadline(), t.getProjectName(),
							t.isCompleted());
					p.editTask(t, updated);
				}

			}
		}
	}

	/********************** Search Methods ***********************************/

	/**
	 * This function returns an ArrayList of tasks that matches the search
	 * phrase
	 * 
	 * @param taskDescription
	 *            is the search phrase
	 * @param projectName
	 *            is the name of the project that we wish to search in
	 */
	private void search(String searchPhrase, String projectName) {
		int index = findIndex(projectName);
		Project curr = projectsList.get(index);
		ArrayList<Task> currList = curr.searchForTask(searchPhrase);
		displayArrayList(currList);

	}

	/**
	 * THis function helps to search for a given phrase from all the projects
	 * 
	 * @param searchPhrase
	 *            is the phrase that the user wishes to search
	 */
	private ArrayList<Task> search(String searchPhrase) {
		ArrayList<Task> tempResultsForProject = new ArrayList<Task>();
		ArrayList<Task> allInclusive = new ArrayList<Task>();
		for (Project p : projectsList) {
			tempResultsForProject = (p.searchForTask(searchPhrase));
			if (!tempResultsForProject.isEmpty()) {
				allInclusive.addAll(tempResultsForProject);
			}
		}
		displayArrayList(allInclusive);
		return allInclusive;
	}

	/********************** Display Methods ***********************************/
	/**
	 * This function is a helper function that outputs the ArrayList that we
	 * wish to display
	 * 
	 * @param projectList
	 *            is the ArrayList that we wish to display
	 */
	private void displayArrayList(ArrayList<Task> projectList) {
		for (Task t : projectList) {
			UIHandler.getInstance().printToDisplay(t.printTaskForDisplay());
		}
	}

	/**
	 * Displays all the Tasks within the project
	 * 
	 * @param projectName
	 *            is the name of the project that the user wishes to display
	 */
	private void displayProject(String projectName) {
		int index = findIndex(projectName);
		Project curr = projectsList.get(index);
		displayArrayList(curr.displayAllTasks());
	}

	// display project and display all
	private void display(String input) {
		if (input.equals("all")) {
			// display everything

			for (int i = 0; i < projectsList.size(); i++) {
				UIHandler.getInstance().printToDisplay(
						"Project: " + projectNames.get(i));
				Project currProj = projectsList.get(i);

				ArrayList<Task> deadLineList = currProj.getDeadlineList();
				ArrayList<Task> intervalList = currProj.getIntervalList();
				ArrayList<Task> floatList = currProj.getFloatingList();

				int counter = 0;

				// for deadline tasks
				for (int j = 0; j < deadLineList.size(); j++) {
					UIHandler.getInstance().printToDisplay(
							counter + ". " + deadLineList.get(j).toString());
					counter++;
				}

				counter = 0; // reset counter

				// for interval tasks
				for (int k = 0; k < intervalList.size(); k++) {
					UIHandler.getInstance().printToDisplay(
							counter + ". " + intervalList.get(k).toString());
					counter++;
				}

				counter = 0;

				// for floating tasks
				for (int r = 0; r < floatList.size(); r++) {
					UIHandler.getInstance().printToDisplay(
							counter + ". " + floatList.get(r).toString());
					counter++;
				}
			}

		}
	}

}