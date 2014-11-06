package Logic.Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.util.*;

import Logic.Exceptions.CancelDeleteException;
import Logic.Exceptions.CancelEditException;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.Interpreter.UIHandler;
import Logic.Interpreter.CommandType.*;
import Storage.Writer;
import Storage.Reader;

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

	/**************************************** Message Prompts ************************************************/

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
	public static final String ERROR_INVALID_PROJECT = "Project does not exist!";
	private static final String ERROR_WRONG_CMDTYPE = null;
	private static final String ERROR_COMMAND_TYPE_NULL = null;

	/***************** Data Structures and Objects ********************/
	private static EpiphanyInterpreter interp;
	private static Engine engine;
	public static ArrayList<String> projectNames;
	public static ArrayList<Project> projectsList;
	private static Stack<PastCommands> undoStack;
	private static Stack<PastCommands> redoStack;
	private static ArrayList<DisplayObject> ListByDate;

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

		// new ASCIIArt().generateArt("EPIPHANY");
		UIHandler.getInstance().printToDisplay(MESSAGE_WELCOME);
		initializeDS();
		initializeEngine();
	}

	/**
	 * Initializes the main data structures to be used by engine.
	 * 
	 * @author amit
	 * @throws IOException
	 * @throws ParseException
	 */
	private void initializeDS() throws IOException, ParseException {
		projectsList = new ArrayList<Project>();
		projectNames = new ArrayList<String>();
		interp = new EpiphanyInterpreter();
		undoStack = new Stack<PastCommands>();
		redoStack = new Stack<PastCommands>();
	}

	/**
	 * Reads in relevant saved data and boots Epiphany Engine
	 * 
	 * @author amit
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
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

	/**
	 * Creates the default project
	 * 
	 * @throws IOException
	 */
	private void createDefault() throws IOException {
		projectNames.add("default");
		projectsList.add(new Project("default", new ArrayList<Task>()));
		Writer.generateDefault();
	}

	private void populateProjectsWithTasks() throws FileNotFoundException,
			IOException, ParseException {
		Reader reader = new Reader(projectNames, projectsList);
		reader.readProjectData();
	}

	private void populateProjectNames() throws FileNotFoundException,
			IOException {
		Reader reader = new Reader(projectNames, projectsList);
		reader.readProjectTitles();
	}

	public static int countLines(String filename) throws IOException {
		File file = new File("../Epiphany/src/Storage/" + filename + ".txt");
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
	 * Enums are used for type safety.
	 * 
	 * @author Wei Yang
	 *
	 */
	public enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, EDIT, UNDO, REDO
	};

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
		case ADD:
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			add(addUserCommand.getDescription(), addUserCommand.getDateFrom(),
					addUserCommand.getDateTo(), addUserCommand.getProjectName());
			break;

		case DISPLAY:
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			displayOverall(displayUserCommand.getModifiers());
			break;

		case DELETE:
			DeleteCommandType deleteUserCommand = (DeleteCommandType) userCommand;
			delete(deleteUserCommand.getTaskDescription(),
					deleteUserCommand.getProjectName());
			break;

		case SEARCH:
			SearchCommandType searchUserCommand = (SearchCommandType) userCommand;
			searchInProj(searchUserCommand.getTaskDescription(),
					searchUserCommand.getProjectName());
			break;

		case EDIT:
			EditCommandType editUserCommand = (EditCommandType) userCommand;
			edit(editUserCommand.getTaskDescription(),
					editUserCommand.getProjectName());
			break;

		case UNDO:
			undo();
			break;

		case REDO:
			redo();
			break;

		default:
			throw new Error(ERROR_WRONG_CMDTYPE); // throw an error if the
													// command is not recognized

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

				Writer.updateProjectMasterList(projectName);
			}
		}

		UIHandler.getInstance().printToTerminal(MESSAGE_ADD);
		addToUndoStack("add", projectName, incomingTask);
	}

	/********************** Delete Methods ***********************************/

	private void delete(String taskDescription, String projectName)
			throws IOException {

		Task historyTask = new Task();

		ArrayList<Task> temp = search(taskDescription);

		if (!temp.isEmpty()) {

			UIHandler.getInstance().printToTerminal("Please enter the index number of the task you want to delete");

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

					UIHandler.getInstance().printToTerminal(
							currProject.getProjectName()
									+ " has been removed. ");
					projectsList.remove(indexProject);

				} else {
					UIHandler.getInstance().printToTerminal(
							taskToBeDeleted.getTaskDescription()
									+ " has been removed. ");
				}
			} catch (CancelDeleteException e) {
				return;
			}
			// Looks for the index and removes it
		} else {
			UIHandler.getInstance().printToTerminal("No such task exists!");
		}

		addToUndoStack("delete", historyTask.getProjectName(), historyTask);

	}

	/********************************* Edit Methods ***********************************/

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
								"Please enter the index of the task you want to edit");

				int input = interp.askForAdditionalInformationForEdit();

				Task taskToBeEdited = temp.get(input - 1);

				historyTask = taskToBeEdited; // to keep track for undo purposes

				projectName = taskToBeEdited.getProjectName();
				int index = findIndex(projectName);

				Project currProject = projectsList.get(index);
				currProject.deleteTask(taskToBeEdited);

				// ADD NEW TASK
				UIHandler.getInstance().printToTerminal(
						"Please update your task");

				CommandType newUserCommand = interp.askForNewTaskForEdit();
				executeCommand(newUserCommand);

			} catch (CancelEditException e) {
				return;
			}

		} else {
			UIHandler.getInstance().printToTerminal("Cannot edit!");
		}

		addToUndoStack("edit", historyTask.getProjectName(), null);
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
	private void searchInProj(String searchPhrase, String projectName)
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
			UIHandler.getInstance().printToDisplay(counter + ". " + t.printTaskForDisplay());
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
	private void displayOverall(String input) throws IOException {
		if (input.equals("all")) {
			UIHandler.getInstance().resetDisplay();
			if (projectsList.size() == 1 && projectsList.get(0).isEmpty()) {
				UIHandler.getInstance().printToTerminal(
						MESSAGE_NOTHING_TO_DISPLAY_ERROR);
			}
			collateAllForDisplay();
			displayAll();

		} else if (projectNames.contains(input)) {
			UIHandler.getInstance().resetDisplay();
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
		ListByDate = new ArrayList<DisplayObject>();
		ArrayList<Task> floating = new ArrayList<Task>();

		// Loop Through List of Projects.
		for (String projectName : projectNames) {
			ArrayList<Task> currProjectTasks = projectsList.get(
					findIndex(projectName)).retrieveAllTasks();

			// sort tasks into many arrayLIST which shall then be printed,
			// according to date
			for (Task currTask : currProjectTasks) {

				if (!currTask.hasDeadLine()) {
					floating.add(currTask);
				} else if (checkIfDeadlineListExists(currTask) >= 0) {
					DisplayObject currDisplayObject = ListByDate
							.get(checkIfDeadlineListExists(currTask));
					currDisplayObject.addTaskToList(currTask);
				} else {
					DisplayObject newDisplayObject = new DisplayObject(
							currTask.getDeadline());
					newDisplayObject.addTaskToList(currTask);
					ListByDate.add(newDisplayObject);
				}
			}
			ListByDate.add(new DisplayObject(null, floating));// check if null
																// causes
																// problems
		}

	}

	private void displayAll() {
		// Collections.sort((List<T>) printByDate); SORT IT SOMEHOW
		for (DisplayObject disp : ListByDate) {

			Date currDate = disp.getDate();

			if (currDate == null) {
				ArrayList<Task> listDisObj = disp.getList(); // Floating

				if (!listDisObj.isEmpty()) {
					UIHandler.getInstance().printToDisplay("Bucket List:");
					displayArrayList(listDisObj);
				}

			} else {
				UIHandler.getInstance().printToDisplay(disp.dateToString());
				displayArrayList(disp.getList());
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

		for (int i = 0; i < ListByDate.size(); i++) {
			if (isDateEqual(currTask.getDeadline(), ListByDate.get(i).getDate())) {
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

	// TODO// need to add support from the interpreter side
	@SuppressWarnings("unused")
	private void displayByDate(String input) {

		if (input.matches("\\d+-\\d+-\\d+")) {
			String[] dateRequired = input.split("\\");

			int date = Integer.parseInt(dateRequired[2]);
			int month = Integer.parseInt(dateRequired[1]);
			int year = Integer.parseInt(dateRequired[0]);
			Date dateToBeDisplayedBy = getDate(date, month, year);

			UIHandler.getInstance().printToDisplay(input);
			ArrayList<Task> toBeDisplayed = new ArrayList<Task>();

			for (int i = 0; i < projectNames.size(); i++) {
				ArrayList<Task> tasksInCurrentProject = projectsList.get(i)
						.retrieveAllTasks();
				for (int j = 0; j < tasksInCurrentProject.size(); j++) {
					Task currTask = tasksInCurrentProject.get(j);
					if (isDateEqual(dateToBeDisplayedBy, currTask.getDeadline())) {

						toBeDisplayed.add(currTask);
					}
				}
			}
			displayArrayList(toBeDisplayed);
		}
	}

	/********************** Undo/Redo Methods ***********************************/
	/**
	 * The undo method helps to revert the most recent add, delete or edit
	 * command.
	 * 
	 * @throws IOException
	 */
	private void undo() throws IOException {

		if (undoStack.isEmpty()) {
			UIHandler.getInstance().printToTerminal(MESSAGE_UNDO_ERROR);
		} else {
			PastCommands mostRecent = undoStack.pop();

			String typeOfCommand = mostRecent.getType();
			Task task = mostRecent.getTask();
			if(!projectNames.contains(mostRecent.getProjectName())){
					UIHandler.getInstance().printToTerminal(ERROR_INVALID_PROJECT);
				
			}
			else{
			if (typeOfCommand.equals("add")) {
				Project p = findProject(mostRecent.getProjectName());
				p.deleteTask(task);
				redoStack.push(mostRecent);

			} else if (typeOfCommand.equals("delete")) {
				Project p = findProject(mostRecent.getProjectName());
				p.addTask(task);
				redoStack.push(mostRecent);
				// TODO// I dont think this would work.
			} else if (typeOfCommand.equals("edit")) {

				PastCommands first = undoStack.pop();
				// Project p = findProject(first.getProjectName());
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

			}

			UIHandler.getInstance().printToTerminal(MESSAGE_REDO_SUCCESS);
		}
	}

	private void addToUndoStack(String type, String projectName,
			Task incomingTask) {
		undoStack.push(new PastCommands(type, incomingTask, projectName));
	}

	/********************************* Helper methods ********************************/
	private int findIndex(String projectName) {
		for (int i = 0; i < projectNames.size(); i++) {
			if (projectNames.get(i).equals(projectName)) {
				return i;
			}
		}

		return -1;
	}

	@SuppressWarnings("deprecation")
	private boolean isDateEqual(Date d1, Date d2) {
		if (d1.getDate() != d2.getDate()) {
			return false;
		}

		if (d1.getMonth() != d2.getMonth()) {
			return false;
		}

		if (d1.getYear() != d2.getYear()) {
			return false;
		}

		return true;
	}

	private Project findProject(String projectName) {
		
			int index = findIndex(projectName);
			Project p = projectsList.get(index);
			return p;
		}

	}


