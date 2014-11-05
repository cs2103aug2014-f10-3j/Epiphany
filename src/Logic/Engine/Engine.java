package Logic.Engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Logic.Exceptions.CancelDeleteException;
import Logic.Exceptions.CancelEditException;
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
	public static final String MESSAGE_WELCOME = "";
	public static final String MESSAGE_WRONG_ENTRY = "Wrong entry, please re-enter input.";
	public static final String MESSAGE_SORTED = "Tasks sorted alphabetically!";
	public static final String MESSAGE_DELETE_INVALID = " %s, is already empty, please re-enter command.";
	public static final String MESSAGE_NO_ENTRY = "No such entry exists in %s.";
	public static final String MESSAGE_DELETE = "Deleted from %s  %s.";
	public static final String MESSAGE_CLEAR_EMPTY = "%s ";
	public static final String MESSAGE_ADD = "Task Added!";
	public static final String MESSAGE_ADD_DUPLICATE = "This task already exists!";
	public static final String MESSAGE_NOTHING_TO_DISPLAY_ERROR = "No items to display.";
	public static final String MESSAGE_CLEAR = "All content deleted from %s.";
	public static final String MESSAGE_DISPLAY = "%d. %s";
	public static final String MESSAGE_EXIT = "Thank you for using %s, good bye!";
	public static final String MESSAGE_SORT = "All lines are now sorted.";
	public static final String MESSAGE_INVALID_SEARCH = "No results to display.";
	public static final String MESSAGE_PROVIDE_ARGUMENT = "Argument missing, please re-enter command.";
	public static final String MESSAGE_UNDO_ERROR = "Nothing to undo!";
	public static final String MESSAGE_UNDO_SUCCESS = "Undone!";
	public static final String MESSAGE_REDO_ERROR = "Nothing to redo!";
	public static final String MESSAGE_REDO_SUCCESS = "Redone!";
	private static final String ERROR_WRONG_INPUT = null;
	private static final String ERROR_COMMAND_TYPE_NULL = null;
	private static EpiphanyInterpreter interp;
	private static Engine engine;
	private static Stack<PastCommands> undoStack;
	private static Stack<PastCommands> redoStack;
	private static ArrayList<DisplayObject> printByDate;

	/**
	 * Enums are used for type safety.
	 * 
	 * @author Wei Yang
	 *
	 */
	public enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, EDIT, UNDO, REDO
	};

	private Engine() throws IOException, ParseException {
		engine = this;
		run();
	}

	/**
	 * Singleton implementation of Engine
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static Engine getInstance() throws IOException, ParseException {
		if (engine == null) {
			return new Engine();
		}

		return engine;
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

		new ASCIIArt().generateArt("EPIPHANY");
		UIHandler.getInstance().printToDisplay(MESSAGE_WELCOME);
		projectsList = new ArrayList<Project>();
		projectNames = new ArrayList<String>();
		initializeEngine();
		interp = new EpiphanyInterpreter();
		undoStack = new Stack<PastCommands>();
		redoStack = new Stack<PastCommands>();
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

				Task t = null;

				if (type.equals("deadline")) {
					t = new Task(description, null, to, projName);
				} else if (type.equals("interval")) {
					t = new Task(description, from, to, projName);
				} else if (type.equals("floating")) {
					t = new Task(description, null, null, projName);
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
		} else if (commandType.getType().equalsIgnoreCase("edit")) {
			return CommandTypesEnum.EDIT;
		} else if (commandType.getType().equalsIgnoreCase("undo")) {
			return CommandTypesEnum.UNDO;
		} else if (commandType.getType().equalsIgnoreCase("redo")) {
			return CommandTypesEnum.REDO;
		} else {
			return null;
		}
	}

	public void executeCommand(CommandType userCommand) throws IOException {
		CommandTypesEnum commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD:// METHOD DONE
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			add(addUserCommand.getDescription(), addUserCommand.getDateFrom(),
					addUserCommand.getDateTo(), addUserCommand.getProjectName());
			break;
		case DISPLAY:
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			display(displayUserCommand.getModifiers());
			break;
		case DELETE:
			DeleteCommandType deleteUserCommand = (DeleteCommandType) userCommand;
			delete(deleteUserCommand.getTaskDescription(),
					deleteUserCommand.getProjectName());
			break;
		case SEARCH:
			SearchCommandType searchUserCommand = (SearchCommandType) userCommand;
			search(searchUserCommand.getTaskDescription(),
					searchUserCommand.getProjectName());
			break;

		case EDIT:
			EditCommandType editUserCommand = (EditCommandType) userCommand;
			edit(editUserCommand.getTaskDescription(),
					editUserCommand.getProjectName());

			break;

		case UNDO:
			UndoCommandType undoUserCommand = (UndoCommandType) userCommand;
			undo();
			break;

		case REDO:
			RedoCommandType redoUserCommand = (RedoCommandType) userCommand;
			redo();
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
	private void add(String taskDescription, Date dateFrom, Date dateTo,
			String projectName) throws IOException {

		Task incomingTask = new Task(taskDescription, dateFrom, dateTo,
				projectName);

		// Duplicate check.
		if (projectNames.contains(projectName)) {
			int indexProj = findIndex(projectName);
			Project proj = projectsList.get(indexProj);

			// need to check if this incoming task already exists.
			if (proj.containsTask(incomingTask)) {
				UIHandler.getInstance().printToTerminal(MESSAGE_ADD_DUPLICATE);
				return;
			}
		}

		// else, continue
		if (projectNames.contains(projectName)) {
			// Existing project
			int index = findIndex(projectName);
			Project currProject = projectsList.get(index);
			currProject.addTask(incomingTask);
			projectsList.set(index, currProject);
		} else {
			// default project
			if (projectName.equals("default")) {
				int index = 0;
				Project currProject = projectsList.get(index);
				currProject.addTask(incomingTask);
				projectsList.set(index, currProject);
			}
			// create a new project
			else {
				projectNames.add(projectName);
				ArrayList<Task> temp = new ArrayList<Task>();
				temp.add(incomingTask);
				projectsList.add(new Project(projectName, temp));

				File file = new File(
						"../Epiphany/src/Logic/Engine/projectMasterList.txt");
				FileWriter f = new FileWriter(file, true);
				BufferedWriter writer = new BufferedWriter(f);
				writer.newLine();
				writer.write(projectName);
				writer.close();
			}
		}
		UIHandler.getInstance().printToTerminal(MESSAGE_ADD);
		undoStack.push(new PastCommands("add", incomingTask, projectName));
	}

	private int findIndex(String projectName) {
		for (int i = 0; i < projectNames.size(); i++) {
			if (projectNames.get(i).equals(projectName)) {
				return i;
			}
		}

		return -1;
	}

	/**********************
	 * Delete Methods
	 * 
	 * @throws IOException
	 * @throws CancelDeleteException
	 ********************************/

	// private void deleteTask(String taskDescription, String projectName)
	// throws IOException, CancelDeleteException {

	// if (projectNames.contains(projectName)) {
	// search(taskDescription);
	// deleteTaskProperly(taskDescription);
	// }

	// }

	private void delete(String taskDescription, String projectName)
			throws IOException {

		Task historyTask = new Task();

		// Need to check if this is a delete project call.
		// if(taskDescription.equals(null)){
		// deleteProject(projectName);
		// }

		ArrayList<Task> temp = search(taskDescription);

		if (!temp.isEmpty()) {

			UIHandler
					.getInstance()
					.printToTerminal(
							"Please enter the index number of the task you want to delete");

			int input;

			try {
				input = interp.askForAdditionalInformationForDelete();
				Task taskToBeDeleted = temp.get(input - 1);

				historyTask = taskToBeDeleted; // to keep track for undo
												// purposes

				projectName = taskToBeDeleted.getProjectName();
				int indexProject = findIndex(projectName);

				Project currProject = projectsList.get(indexProject);
				currProject.deleteTask(taskToBeDeleted);
				ArrayList<Task> currList = currProject.retrieveAllTasks();
				if (currList.isEmpty()
						&& !currProject.getProjectName().equals("default")) {
					UIHandler.getInstance().printToDisplay(
							currProject.getProjectName()
									+ " has been removed. ");
					projectsList.remove(indexProject);
				} else {
					UIHandler.getInstance().printToDisplay(
							taskToBeDeleted.getTaskDescription()
									+ " has been removed. ");
				}
			} catch (CancelDeleteException e) {
				return;
			}
			// Looks for the index and removes it
		} else {
			UIHandler.getInstance().printToDisplay("No such task exists!");
		}

		undoStack.push(new PastCommands("delete", historyTask, historyTask
				.getProjectName()));

	}

	/**********************
	 * Edit Methods
	 * 
	 * @throws IOException
	 * @throws CancelEditException
	 ***********************************/

	// convert to a task
	private void edit(String taskDescription, String projectName)
			throws IOException {

		Task historyTask = new Task();

		ArrayList<Task> temp = search(taskDescription);

		if (!temp.isEmpty()) {
			// DELETE OLD TASK
			try {
				UIHandler
						.getInstance()
						.printToTerminal(
								"Please enter the index of the task you want to edit: ",
								"inline");

				int input = interp.askForAdditionalInformationForEdit();

				Task taskToBeEdited = temp.get(input - 1);

				historyTask = taskToBeEdited; // to keep track for undo purposes

				projectName = taskToBeEdited.getProjectName();
				int index = findIndex(projectName);

				Project currProject = projectsList.get(index);
				currProject.deleteTask(taskToBeEdited);

				// ADD NEW TASK
				UIHandler.getInstance().printToTerminal(
						"Please update your task:", "inline");

				CommandType newUserCommand = interp.askForNewTaskForEdit();
				executeCommand(newUserCommand);
			} catch (CancelEditException e) {
				return;
			}

		} else {
			UIHandler.getInstance().printToDisplay("Cannot edit!");
		}

		undoStack.push(new PastCommands("edit", historyTask.getProjectName()));

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
	private void search(String searchPhrase, String projectName)
			throws IOException {
		if (projectName.equals("")) {
			search(searchPhrase);
		} else {
			int index = findIndex(projectName);
			Project curr = projectsList.get(index);
			ArrayList<Task> currList = curr.searchForTask(searchPhrase);
			displayArrayList(currList);
		}
	}

	/**
	 * THis function helps to search for a given phrase from all the projects
	 * 
	 * @param searchPhrase
	 *            is the phrase that the user wishes to search
	 */
	private ArrayList<Task> search(String searchPhrase) throws IOException {
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
		if (projectList.isEmpty()) {
			UIHandler.getInstance().printToTerminal(MESSAGE_INVALID_SEARCH);
		}

		int counter = 1;
		for (Task t : projectList) {
			UIHandler.getInstance().printToDisplay(
					counter + ". " + t.printTaskForDisplay());
			counter++;
		}
		UIHandler.getInstance().printToDisplay("\n");
	}

	/**
	 * Displays all the Tasks within a specified project
	 * 
	 * @param projectName
	 *            is the name of the project that the user wishes to display
	 */
	private void displayProject(String projectName) {
		int index = findIndex(projectName);
		Project curr = projectsList.get(index);
		displayArrayList(curr.retrieveAllTasks());
	}

	/**
	 * This method is routed to directly from the interpreter. If it recieves a
	 * "display all" command, it displays all the projects and their associated
	 * tasks. If it says "display #projectName" then this methods reroutes the
	 * query to the displayProjects method.
	 * 
	 * @param input
	 *            is the phrase entered by the user, this could either be "all"
	 *            or "#projectName"
	 * @throws IOException
	 */
	private void display(String input) throws IOException {
		if (input.equals("all")) {

			if (projectsList.size() == 1 && projectsList.get(0).isEmpty()) {
				UIHandler.getInstance().printToTerminal(
						MESSAGE_NOTHING_TO_DISPLAY_ERROR);
			}
			collateAllForDisplay();
			displayAll();
			/*int counter = 1;
			for (int i = 0; i < projectsList.size(); i++) {

				Project currProj = projectsList.get(i);
				ArrayList<Task> taskList = currProj.retrieveAllTasks();

				for (Task t : taskList) {
					UIHandler.getInstance().printToDisplay(
							counter + ". " + t.printTaskForDisplay());
					counter++;
				}*/
			

		} else if (projectNames.contains(input)) {
			displayProject(input);
		} else {
			UIHandler.getInstance().printToTerminal(
					MESSAGE_NOTHING_TO_DISPLAY_ERROR);
		}

	}

	/**
	 * This method is only used if all the tasks need to be displayed. It works
	 * by displaying the tasks under their deadline(in date format) header. The
	 * last ones to be displayed would be floating tasks
	 */
	private void collateAllForDisplay() {
		printByDate = new ArrayList<DisplayObject>();
		ArrayList<Task> floating = new ArrayList<Task>();
		
		// Loop Through List of Projects.
		for (int i = 0; i < projectNames.size(); i++) {
			ArrayList<Task> currProjectTasks = projectsList.get(i).retrieveAllTasks();
			
			// sort tasks into many arrayLIST which shall then be printed
			// according to date
			for (int j = 0; j < currProjectTasks.size(); j++) {
				Task currTask = currProjectTasks.get(j);
				
				if (!currTask.hasDeadLine()) {
					floating.add(currTask);
				} else if (checkIfDeadlineListExists(currTask) >= 0) {
					DisplayObject currDisplayObject = printByDate.get(checkIfDeadlineListExists(currTask));
					currDisplayObject.addTaskToList(currTask);
				} else {
					DisplayObject newDisplayObject = new DisplayObject(currTask.getDeadline());
					newDisplayObject.addTaskToList(currTask);
					printByDate.add(newDisplayObject);
				}
			}
			printByDate.add(new DisplayObject(null, floating));// check if null
																// causes
																// problems
		}
		
	}

	private void displayAll() {
		//Collections.sort((List<T>) printByDate); SORT IT SOMEHOW
		for(int i = 0; i < printByDate.size(); i++){
			DisplayObject currDisplayObject = printByDate.get(i);
			Date currDate = currDisplayObject.getDate();
			if(currDate == null){
				// Floating
				UIHandler.getInstance().printToDisplay("Untimed Tasks:");
				displayArrayList(currDisplayObject.getList());
				
			}else{
				UIHandler.getInstance().printToDisplay(currDate.toString());
				displayArrayList(currDisplayObject.getList());	
			}
			
		}
		
		
	}

	// check if the format of the date created is correct

	/**
	 * This method helps to check if a DisplayTask object exists for the given
	 * date
	 * 
	 * @param curr
	 *            is the task whose date is currently being checked
	 * @return
	 */
	private int checkIfDeadlineListExists(Task currTask) {
		// does this work?
		
		for (int i = 0; i < printByDate.size(); i++) {
			if (isDateEqual(currTask.getDeadline(), printByDate.get(i).getDate())) {
				return i;
			} else {
				continue;
			}
		}
		return -1;
	}

	private Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, 0, 0, 0);
		Date date = cal.getTime(); // get back a Date object
		return date;
	}

	private void displayByDate(String input) {

		if (input.matches("\\d+-\\d+-\\d+")) {
			String[] dateRequired = input.split("\\");
			int dummy = 0;
			// Date dateToBeDisplayedBy =
			// Date.UTC(Integer.parseInt(dateRequired[2]),
			// Integer.parseInt(dateRequired[1]),
			// Integer.parseInt(dateRequired[0]), 0, 0, 0);
			Date dateToBeDisplayedBy = getDate(
					Integer.parseInt(dateRequired[2]),
					Integer.parseInt(dateRequired[1]),
					Integer.parseInt(dateRequired[0]));

			UIHandler.getInstance().printToDisplay(input);
			ArrayList<Task> toBeDisplayed = new ArrayList<Task>();

			for (int i = 0; i < projectNames.size(); i++) {
				ArrayList<Task> tasksInCurrentProject = projectsList.get(i)
						.retrieveAllTasks();
				for (int j = 0; j < tasksInCurrentProject.size(); j++) {
					Task currTask = tasksInCurrentProject.get(j);
					if (currTask.getDeadline().equals(dateToBeDisplayedBy)) {// check
																				// is
																				// override
																				// is
																				// required
						toBeDisplayed.add(currTask);
					}
				}
			}

			displayArrayList(toBeDisplayed);
		}
	}

	/********************** Undo/Redo Methods ***********************************/
	private void undo() throws IOException {

		if (undoStack.isEmpty()) {
			UIHandler.getInstance().printToTerminal(MESSAGE_UNDO_ERROR);
		} else {
			PastCommands mostRecent = undoStack.pop();

			// Type check.
			String type = mostRecent.getType();
			Task task = mostRecent.getTask();

			if (type.equals("add")) {
				// undo add
				if (projectNames.contains(task.getProjectName())) {
					// Match found

					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.deleteTask(task);

				}

				redoStack.push(mostRecent);

			} else if (type.equals("delete")) {
				if (projectNames.contains(task.getProjectName())) {
					// Match found

					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.addTask(task);

				}

				redoStack.push(mostRecent);

			} else if (type.equals("edit")) {
				PastCommands first = undoStack.pop(); // (now add) del

				// PastCommands second = undoStack.pop(); //add

				if (projectNames.contains(first.getProjectName())) {

					int index = findIndex(first.getProjectName());
					Project p = projectsList.get(index);

					if (first.getType().equals("add")) {
						p.deleteTask(first.getTask());
					}
				}

				PastCommands toPut = undoStack.peek();

				if (projectNames.contains(toPut.getProjectName())) {
					int index = findIndex(toPut.getProjectName());
					Project p = projectsList.get(index);
					p.addTask(toPut.getTask());
				}

				redoStack.push(first);
				redoStack.push(toPut);
				redoStack.push(mostRecent);
			}

			UIHandler.getInstance().printToTerminal(MESSAGE_UNDO_SUCCESS);
		}
	}

	private void redo() throws IOException {

		if (redoStack.isEmpty()) {
			UIHandler.getInstance().printToTerminal(MESSAGE_REDO_ERROR);
		} else {
			PastCommands mostRecent = redoStack.pop();

			// Type check.
			String type = mostRecent.getType();
			Task task = mostRecent.getTask();

			if (type.equals("add")) {
				// undo add
				if (projectNames.contains(task.getProjectName())) {
					// Match found

					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.addTask(task);

				}
			} else if (type.equals("delete")) {
				if (projectNames.contains(task.getProjectName())) {
					// Match found

					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.deleteTask(task);

				}
			} else if (type.equals("edit")) {
				PastCommands existingTask = redoStack.pop(); // (now add) del

				PastCommands newTask = redoStack.pop(); // add

				if (projectNames.contains(existingTask.getProjectName())) {

					int index = findIndex(existingTask.getProjectName());
					Project p = projectsList.get(index);

					if (existingTask.getType().equals("add")) {
						p.deleteTask(existingTask.getTask());
					}
				}

				if (projectNames.contains(newTask.getProjectName())) {
					int index = findIndex(newTask.getProjectName());
					Project p = projectsList.get(index);
					p.addTask(newTask.getTask());
				}

				// redoStack.push(existingTask);
				// redoStack.push(second);
				// redoStack.push(mostRecent);
			}

			UIHandler.getInstance().printToTerminal(MESSAGE_REDO_SUCCESS);
		}
	}
	
	private boolean isDateEqual(Date d1, Date d2){
		if(d1.getDate() != d2.getDate()){
			return false;
		}
		
		if(d1.getMonth() != d2.getMonth()){
			return false;
		}
		
		if(d1.getYear() != d2.getYear()){
			return false;
		}
		
		return true;
	}
}
/**
 * This class is created to create the compare method which compares two dates
 * and returns 0 if they are equal
 * 
 * @author Moazzam
 *
 */
/*
 * private class dateComparator implements Comparator<Date> { public int
 * compare(Date date1, Date date2) { if (Date1 == null && task2.getDeadline() ==
 * null) { return 0; } else if (task1.getDeadline() != null &&
 * task2.getDeadline() == null) { return 1; } else if (task1.getDeadline() ==
 * null && task2.getDeadline() != null) { return -1; } else if
 * (task1.getDeadline() != null && task2.getDeadline() != null) { return
 * task1.getDeadline().compareTo(task2.getDeadline()); }
 * 
 * return 0; }
 * 
 * } }
 */