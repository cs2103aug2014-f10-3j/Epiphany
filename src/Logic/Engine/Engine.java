package Logic.Engine;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.AttributedString;
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
 * 
 * @author Moazzam and Wei Yang
 *
 */
public class Engine {

	/**************************************** Message Prompts ************************************************/

	private static final String MESSAGE_WELCOME = "";
	private static final String MESSAGE_NO_ENTRY = "No such entry exists.";
	private static final String MESSAGE_ADD = "Task Added!";
	private static final String MESSAGE_ADD_DUPLICATE = "This task already exists!";
	private static final String MESSAGE_NOTHING_TO_DISPLAY_ERROR = "No items to display.";
	private static final String MESSAGE_INVALID_SEARCH = "No results to display.";
	private static final String MESSAGE_UNDO_ERROR = "Nothing to undo!";
	private static final String MESSAGE_UNDO_SUCCESS = "Undone!";
	private static final String MESSAGE_REDO_ERROR = "Nothing to redo!";
	private static final String MESSAGE_REDO_SUCCESS = "Redone!";
	private static final String MESSAGE_ERROR_WRONG_CMDTYPE = null;
	private static final String MESSAGE_ERROR_COMMAND_TYPE_NULL = null;
	private static final String MESSAGE_UPDATE_TASK_FOR_EDIT = "Please update your task";
	private static final String MESSAGE_ERROR_UNABLE_TO_EDIT = "Cannot edit!";
	private static final String MESSAGE_NO_INDEX_SPECIFIED = "No index has been specified!";
	private static final String MESSAGE_FOR_DELETE = "Please enter the index number of the task you want to delete:";
	private static final String MESSAGE_NOTHING_TO_DELETE = "Nothing to delete!";
	private static final String MESSAGE_SPECIFY_INDEX_FOR_ISCOMPLETE = "Please specify an index to be marked as completed";
	private static final String MESSAGE_SPECIFY_INDEX_FOR_EDIT = "Please specify an index you wish to edit";
	private static final String MESSAGE_REMOVE_SUCCESS = " has been removed.";
	private static final String MESSAGE_MARKED_AS_ONGOING = " marked as ongoing.";
	private static final String MESSAGE_MARKED_AS_COMPLETE = " marked as complete.";
	private static final String MESSAGE_ERROR_INVALID_TASK = " No such task exists!";
	private static final String MESSAGE_MARKED_AS_DONE = " [DONE]";
	private static final int N = 999;
	private static final String MESSAGE_RESET = "System restarted!";

	/***************** Data Structures and Objects ********************/
	private static EpiphanyInterpreter interp;
	private static Engine engine;
	public static ArrayList<String> projectNames;
	public static ArrayList<Project> projectsList;
	private static Stack<PastCommands> undoStack;
	private static Stack<PastCommands> redoStack;
	private static ArrayList<DisplayObject> ListByDate;
	private static String[] months;

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
		months = new String[12];
		populateMonths();
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
		ADD, DISPLAY, DELETE, RESET, EXIT, INVALID, SEARCH, EDIT, UNDO, REDO, COMPLETE
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
			throw new Error(MESSAGE_ERROR_COMMAND_TYPE_NULL);
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
		} else if (commandType.getType().equalsIgnoreCase("complete")) {
			return CommandTypesEnum.COMPLETE;
		}  else if (commandType.getType().equalsIgnoreCase("reset")) {
			return CommandTypesEnum.RESET;
		}else {
			return null;
		}
	}

	public void executeCommand(CommandType userCommand) throws IOException, ParseException {
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
		
		case RESET:
			reset();
			break;

		case COMPLETE:
			CompleteCommandType completeUserCommand = (CompleteCommandType) userCommand;
			checkCompleteTask(completeUserCommand.getTaskDescription());
			break;

		default:
			throw new Error(MESSAGE_ERROR_WRONG_CMDTYPE); 
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
	private void add(String taskDescription, Date dateFrom, Date dateTo,String projectName) throws IOException {

		Task incomingTask = new Task(taskDescription, dateFrom, dateTo, projectName, false);

		// Duplicate check.
		if (projectNames.contains(projectName)) {
			int indexProj = findIndex(projectName);
			Project proj = projectsList.get(indexProj);

			// need to check if this incoming task already exists.
			if (proj.containsTask(incomingTask)) {
				UIHandler.getInstance().printToDisplay(MESSAGE_ADD_DUPLICATE);
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
				// not sure about this line please look into it.
				Writer.updateProjectMasterList(projectName, projectNames);
			}
		}

		UIHandler.getInstance().printToDisplay(MESSAGE_ADD);
		addToUndoStack("add", projectName, incomingTask, incomingTask.getParity());
	}

	/********************** Delete Methods ***********************************/

	private void delete(String taskDescription, String projectName)
			throws IOException {
		if (taskDescription == null && !projectName.equals("default")) {
			// Deleting a project instead.
			Project projToDelete = projectsList.get(findIndex(projectName));
			ArrayList<Task> tasksToDelete = projToDelete.retrieveAllTasks();
			
			for(Task t : tasksToDelete){
				t.flipParity();
				removeTaskFromProj(t);
			}
			
		//	UIHandler.getInstance().printToDisplay(MESSAGE_FOR_DELETE);

			/*
			int indexOfProjectToDelete = findIndex(projectName);

			projectsList.remove(indexOfProjectToDelete);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
			UIHandler.getInstance().printToDisplay(projectName + " has been removed. ");
			*/
		
			return;
		}

		ArrayList<Task> tasksToDisplayForDelete = searchForTask(taskDescription);

		if (tasksToDisplayForDelete.size() == 0) {
			UIHandler.getInstance().printToDisplay(MESSAGE_NOTHING_TO_DELETE);

		} else if (tasksToDisplayForDelete.size() == 1) {

			Task taskToBeDeleted = tasksToDisplayForDelete.get(0);
			removeTaskFromProj(taskToBeDeleted);

		} else if (tasksToDisplayForDelete.size() > 1) {

			ArrayList<Task> temp;

			temp = tasksToDisplayForDelete;
			displayArrayList(tasksToDisplayForDelete);

			UIHandler.getInstance().printToDisplay(MESSAGE_FOR_DELETE);

			try {
				int[] input = interp.askForAdditionalInformationForDelete();

				if (input.length == 0) {
					UIHandler.getInstance().printToDisplay(
							MESSAGE_NO_INDEX_SPECIFIED);
				} else {
					for (int i = 0; i < input.length; i++) {
						if (input[i] <= temp.size()) {
							Task taskToBeDeleted = temp.get(input[i] - 1);
							removeTaskFromProj(taskToBeDeleted);
						}
					}
				}
			} catch (CancelDeleteException e) {
				return;
			}

		} else {
			UIHandler.getInstance().printToDisplay("No such task exists!");
		}
	}
	
	private void deleteForRedo(String taskDescription, String projectName)
			throws IOException {
		if (taskDescription == null && !projectName.equals("default")) {
			// Deleting a project instead.
			Project projToDelete = projectsList.get(findIndex(projectName));
			ArrayList<Task> tasksToDelete = projToDelete.retrieveAllTasks();
			
			for(Task t : tasksToDelete){
				t.flipParity();
				removeTaskFromProj(t);
			}
			
		//	UIHandler.getInstance().printToDisplay(MESSAGE_FOR_DELETE);

			/*
			int indexOfProjectToDelete = findIndex(projectName);

			projectsList.remove(indexOfProjectToDelete);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
			UIHandler.getInstance().printToDisplay(projectName + " has been removed. ");
			*/
		
			return;
		}
		
		int index = findIndex(projectName);
		ArrayList<Task> taskList = projectsList.get(index).retrieveAllTasks();
		
		for(Task t : taskList){
			if(t.getTaskDescription().equals(taskDescription)){
				removeTaskFromProj(t);
			}
		}
		
		/*

		ArrayList<Task> tasksToDisplayForDelete = searchForTask(taskDescription);

		if (tasksToDisplayForDelete.size() == 0) {
			UIHandler.getInstance().printToDisplay(MESSAGE_NOTHING_TO_DELETE);

		} else if (tasksToDisplayForDelete.size() == 1) {

			Task taskToBeDeleted = tasksToDisplayForDelete.get(0);
			removeTaskFromProj(taskToBeDeleted);

		} else if (tasksToDisplayForDelete.size() > 1) {
			/*
			ArrayList<Task> temp;

			temp = tasksToDisplayForDelete;
			displayArrayList(tasksToDisplayForDelete);

			UIHandler.getInstance().printToDisplay(MESSAGE_FOR_DELETE);

			try {
				int[] input = interp.askForAdditionalInformationForDelete();

				if (input.length == 0) {
					UIHandler.getInstance().printToDisplay(
							MESSAGE_NO_INDEX_SPECIFIED);
				} else {

					for (int i = 0; i < input.length; i++) {
						Task taskToBeDeleted = temp.get(input[i] - 1);

						removeTaskFromProj(taskToBeDeleted);
					}
				}
				
			} catch (CancelDeleteException e) {
				return;
			}
		} else {
			UIHandler.getInstance().printToDisplay("No such task exists!");
		}
		
		*/
	}

	/********************************* Edit Methods 
	 * @throws ParseException ***********************************/

	// convert to a task
	private void edit(String taskDescription, String projectName)
			throws IOException, ParseException {

		Task historyTask = new Task();

		ArrayList<Task> temp = search(taskDescription);

		if (!temp.isEmpty()) {
			// DELETE OLD TASK
			try {
				UIHandler.getInstance().printToDisplay(
						MESSAGE_SPECIFY_INDEX_FOR_EDIT);

				int input = interp.askForAdditionalInformationForEdit();

				Task taskToBeEdited = temp.get(input - 1);

				historyTask = taskToBeEdited; // to keep track for undo purposes

				projectName = taskToBeEdited.getProjectName();
				int index = findIndex(projectName);

				Project currProject = projectsList.get(index);
				currProject.deleteTask(taskToBeEdited);

				// ADD NEW TASK
				UIHandler.getInstance().printToDisplay(
						MESSAGE_UPDATE_TASK_FOR_EDIT);

				CommandType newUserCommand = interp.askForNewTaskForEdit();
				executeCommand(newUserCommand);

			} catch (CancelEditException e) {
				return;
			}

		} else {
			UIHandler.getInstance()
					.printToDisplay(MESSAGE_ERROR_UNABLE_TO_EDIT);
		}

		addToUndoStack("edit", historyTask.getProjectName(), null, false);
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
			displayArrayListForSearch(currList);
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
		displayArrayListForSearch(allInclusive);
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
			UIHandler.getInstance().printToDisplay(MESSAGE_INVALID_SEARCH);
		}

		int counter = 1;
		for (Task t : projectList) {
			UIHandler.getInstance().printToDisplay(
					counter + ". " + t.printTaskForDisplay("display"));
			counter++;
			UIHandler.getInstance().resetToDefault();
		}
		UIHandler.getInstance().printToDisplay("\n");
	}

	private void displayArrayListForSearch(ArrayList<Task> projectList) {

		if (projectList.isEmpty()) {
			UIHandler.getInstance().printToDisplay(MESSAGE_INVALID_SEARCH);
		}

		int counter = 1;
		for (Task t : projectList) {
			UIHandler.getInstance().printToDisplay(
					counter + ". " + t.printTaskForDisplay("search"));
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
		if (input.matches("\\d+-\\d+-\\d+")) {
			displayByDate(input);
		} else if (input.equals("all")) {

			if (projectsList.size() == 1 && projectsList.get(0).isEmpty()
					|| !isThereATask()) {
				UIHandler.getInstance().printToDisplay(
						MESSAGE_NOTHING_TO_DISPLAY_ERROR);
			}
			collateAllForDisplay();
			displayAll();

		} else if (projectNames.contains(input)) {

			displayProject(input);
		} else {
			UIHandler.getInstance().printToDisplay(
					MESSAGE_NOTHING_TO_DISPLAY_ERROR);
		}

	}

	private void reset() throws IOException, ParseException {
		
		for (int i = 0; i < projectNames.size(); i++) {
			Project currProject = projectsList.get(i);
		
			ArrayList<Task> currTaskList = currProject.retrieveAllTasks();
			for (Task t : currTaskList) {
				currProject.deleteTask(t);
			}
		}
		
		UIHandler.getInstance().printToDisplay(MESSAGE_RESET);
	}

	/**
	 * This method is only used if all the tasks need to be displayed. It works
	 * by displaying the tasks under their deadline(in date format) header. The
	 * last ones to be displayed would be floating tasks
	 */
	private void collateAllForDisplay() {
		ListByDate = new ArrayList<DisplayObject>();
		ArrayList<Task> floating = new ArrayList<Task>();
		ArrayList<Task> overdue = new ArrayList<Task>();

		for (String projectName : projectNames) {
			ArrayList<Task> currProjectTasks = projectsList.get(
					findIndex(projectName)).retrieveAllTasks();

			for (Task currTask : currProjectTasks) {

				if (!currTask.hasDeadLine()) {
					floating.add(currTask);
				} else if (isTaskOverDue(currTask)) {
					
				//	currTask.setInstruction("\033[31m" + currTask.getTaskDescription()); // added red colour 
					
					currTask.setOverdue();
					overdue.add(currTask);
					

				} else if (checkIndexOfDeadlineObject(currTask) >= 0) {

					DisplayObject currDisplayObject = ListByDate
							.get(checkIndexOfDeadlineObject(currTask));
					currDisplayObject.addTaskToList(currTask);
				} else {
					DisplayObject newDisplayObject = new DisplayObject(
							currTask.getDeadline());
					newDisplayObject.addTaskToList(currTask);
					ListByDate.add(newDisplayObject);
				}
			}
		}
		ListByDate.add(new DisplayObject(null, floating));
		ListByDate.add(0, new DisplayObject(null, overdue));
	}

	private void displayAll() {

		for (DisplayObject disp : ListByDate) {

			Date currDate = disp.getDate();

			if (currDate == null) {
				ArrayList<Task> listDisObj = disp.getList(); // Floating and
																// overdue

				if (!listDisObj.isEmpty()) {
					if (listDisObj.get(0).hasDeadLine()) {
						UIHandler.getInstance().printToDisplay(
								"------------");
						UIHandler.getInstance().printToDisplay(
								"> " + "Overdue:" + " |");
						UIHandler.getInstance().printToDisplay(
								"------------");
						displayArrayList(listDisObj);

					} else {
						UIHandler.getInstance().printToDisplay(
								"----------------");
						UIHandler.getInstance().printToDisplay(
								"> " + "Bucket List:" + " |");
						UIHandler.getInstance().printToDisplay(
								"----------------");
						displayArrayList(listDisObj);
					}
				}

			} else {
				UIHandler.getInstance().printToDisplay(
						"------------------------");
				UIHandler.getInstance().printToDisplay(
						"> " + disp.dateToString() + " |");
				UIHandler.getInstance().printToDisplay(
						"------------------------");

				displayArrayList(disp.getList());

			}
		}

	}

	private void displayByDate(String input) {

		if (input.matches("\\d+-\\d+-\\d+")) {
			String[] dateRequired = input.split("-");

			int date = Integer.parseInt(dateRequired[2]);
			int month = Integer.parseInt(dateRequired[1]);
			int year = Integer.parseInt(dateRequired[0]);
			Date dateToBeDisplayedBy = getDate(date, month, year);

			UIHandler.getInstance().printToDisplay(parseDate(input) + ":");
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
			UIHandler.getInstance().printToDisplay(MESSAGE_UNDO_ERROR);
		} else {
			PastCommands mostRecent = undoStack.pop();

			String typeOfCommand = mostRecent.getType();
			Task task = mostRecent.getTask();
			if (!projectNames.contains(mostRecent.getProjectName())) {
			//	UIHandler.getInstance().printToDisplay(MESSAGE_ERROR_INVALID_PROJECT);
				
				Task mostRecentTask = mostRecent.getTask();
				add(mostRecentTask.getTaskDescription(), mostRecentTask.getStartDate(), mostRecentTask.getDeadline(), mostRecentTask.getProjectName());
				undoStack.pop();
				redoStack.push(mostRecent);
				
				PastCommands top = undoStack.peek();
				
				if(top.getTask().getParity()){
					// is a project
					while(top.getType().equals("delete") && top.getProjectName().equals(mostRecentTask.getProjectName())){
						PastCommands recent = undoStack.pop();
						redoStack.push(recent);
						Task toAdd = recent.getTask();
						
						add(toAdd.getTaskDescription(), toAdd.getStartDate(), toAdd.getDeadline(), toAdd.getProjectName());
						undoStack.pop();
						if(undoStack.isEmpty()){
							break;
						}
						top = undoStack.peek();
					}
				}

			} else {
				if (typeOfCommand.equals("add")) {
					Project p = findProject(mostRecent.getProjectName());
					p.deleteTask(task);
					redoStack.push(mostRecent);

				} else if (typeOfCommand.equals("delete")) {
					Task mostRecentTask = mostRecent.getTask();
					add(mostRecentTask.getTaskDescription(), mostRecentTask.getStartDate(), mostRecentTask.getDeadline(), mostRecentTask.getProjectName());
					undoStack.pop();
					redoStack.push(mostRecent);
					if(!undoStack.isEmpty()){
						PastCommands top = undoStack.peek();
	
						while(top.getType().equals("delete")){
							
							if(!top.getProjectName().equals(mostRecentTask.getProjectName())){
								return;
							}
							
							PastCommands recent = undoStack.pop();
							redoStack.push(recent);
							Task toAdd = recent.getTask();
							
							add(toAdd.getTaskDescription(), toAdd.getStartDate(), toAdd.getDeadline(), toAdd.getProjectName());
							undoStack.pop();
							if(undoStack.isEmpty()){
								break;
							}
							top = undoStack.peek();
						}
					}

				} else if (typeOfCommand.equals("edit")) {

					PastCommands first = undoStack.pop();

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

				UIHandler.getInstance().printToDisplay(MESSAGE_UNDO_SUCCESS);
			}
		}
	}

	private void redo() throws IOException {

		if (redoStack.isEmpty()) {
			UIHandler.getInstance().printToDisplay(MESSAGE_REDO_ERROR);
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
					/*
					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.deleteTask(task);
					 */
									
					Task toAdd = mostRecent.getTask();
					deleteForRedo(toAdd.getTaskDescription(), toAdd.getProjectName());
					if(!undoStack.isEmpty()){
						undoStack.pop();
					}
					if(!redoStack.isEmpty()){
						PastCommands top = redoStack.peek();

						if(top.getTask().getParity()){
							while(top.getType().equals("delete") && top.getProjectName().equals(toAdd.getProjectName())){
								Task recent = redoStack.pop().getTask();
								delete(recent.getTaskDescription(), recent.getProjectName());
								if(!undoStack.isEmpty()){
									undoStack.pop();
								}
								if(redoStack.isEmpty()){
									return;
								}
								top = redoStack.peek();
							}

						}else{
							while(top.getType().equals("delete")){
								Task recent = redoStack.pop().getTask();
								deleteForRedo(recent.getTaskDescription(), recent.getProjectName());
								if(!undoStack.isEmpty()){
									undoStack.pop();
								}							
								if(redoStack.isEmpty()){
									return;
								}
								top = redoStack.peek();
							}
						}
					}
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

			UIHandler.getInstance().printToDisplay(MESSAGE_REDO_SUCCESS);
		}
	}

	private void addToUndoStack(String type, String projectName, Task incomingTask, boolean parity) {
		undoStack.push(new PastCommands(type, incomingTask, projectName));
	}

	/************************ isComplete *************************/

	/**
	 * This method marks a task as complete and users can unmark the task by
	 * calling this method again
	 * 
	 * @param input
	 * @throws IOException
	 */
	private void checkCompleteTask(String input) throws IOException {
		Task mostRecentTask = new Task();
		ArrayList<Task> tasksToBeCompleted = new ArrayList<Task>();
		tasksToBeCompleted = searchForTask(input);

		if (tasksToBeCompleted.size() == 0) {
			UIHandler.getInstance().printToDisplay(MESSAGE_NO_ENTRY);

		} else if (tasksToBeCompleted.size() == 1) {
			Task targetTask = tasksToBeCompleted.get(0);
			mostRecentTask = targetTask;
			updateBackEnd(mostRecentTask);
			markTaskDescriptionAsComplete(mostRecentTask);

		} else if (tasksToBeCompleted.size() > 1) {

			ArrayList<Task> temp;
			temp = tasksToBeCompleted;
			displayArrayList(temp);
			UIHandler.getInstance().printToDisplay(
					MESSAGE_SPECIFY_INDEX_FOR_ISCOMPLETE);
			int[] inputForComplete = new int[N];

			try {
				inputForComplete = interp
						.askForAdditionalInformationForDelete();
				if (inputForComplete.length == 0) {
					UIHandler.getInstance().printToDisplay(
							MESSAGE_NO_INDEX_SPECIFIED);
				} else {
					for (int i = 0; i < inputForComplete.length; i++) {
						if (inputForComplete[i] <= temp.size()) {
							Task taskToBeDeleted = temp
									.get(inputForComplete[i] - 1);
							mostRecentTask = taskToBeDeleted;
							updateBackEnd(mostRecentTask);
							markTaskDescriptionAsComplete(mostRecentTask);
						}
					}
				}
			} catch (CancelDeleteException e) {
				return;
			}
		} else {
			UIHandler.getInstance().printToDisplay(MESSAGE_ERROR_INVALID_TASK);
		}
	}

	// some special effect to denote that the task has been complete
	public void markTaskDescriptionAsComplete(Task input) {
		if (input.isCompleted() == true) {
		//	String originalInstruction = input.getTaskDescription();
			//input.setInstruction("\033[1;32m" + originalInstruction); // set it to green to denote Done

			String toDisplay = input.getTempTaskDescription() + MESSAGE_MARKED_AS_COMPLETE;
			UIHandler.getInstance().printToDisplay(toDisplay);
		} else { // if false, undo the operation and display the original task
					// description
			input.setInstruction(input.getTempTaskDescription());
			String toDisplay = input.getTempTaskDescription() + MESSAGE_MARKED_AS_ONGOING;
			UIHandler.getInstance().printToDisplay(toDisplay);
		}
	}

	public String strikeThroughText(String input) {
		String output;
		AttributedString str_attribute = new AttributedString(input);
		str_attribute.addAttribute(TextAttribute.STRIKETHROUGH, input.length());
		output = str_attribute.toString();
		return output;
	}

	/********************************* Helper methods ********************************/
	private void removeTaskFromProj(Task taskToBeDeleted) throws IOException {
		String projectName = taskToBeDeleted.getProjectName();
		int indexProject = findIndex(projectName);

		Project currProject = projectsList.get(indexProject);

		currProject.deleteTask(taskToBeDeleted);
		ArrayList<Task> taskList = currProject.retrieveAllTasks();

		if (taskList.isEmpty()
				&& !currProject.getProjectName().equals("default")) {

			// UIHandler.getInstance().printToDisplay(currProject.getProjectName()
			// + " has been removed. ");
			projectsList.remove(indexProject);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
			UIHandler.getInstance().printToDisplay(taskToBeDeleted.getTaskDescription() + " has been removed");
			UIHandler.getInstance().printToDisplay(
					projectName + MESSAGE_REMOVE_SUCCESS);
		} else {
			UIHandler.getInstance().printToDisplay(
					taskToBeDeleted.getTaskDescription()
							+ MESSAGE_REMOVE_SUCCESS);
		}
		
		addToUndoStack("delete", taskToBeDeleted.getProjectName(), taskToBeDeleted, taskToBeDeleted.getParity());
	}

	/**
	 * This methods helps to search for all tasks matching a certain task
	 * description
	 * 
	 * @param taskDescription
	 *            is the description of the task we wish to find
	 * @return and ArrayList containing all the tasks that match the task
	 *         description
	 */
	private ArrayList<Task> searchForTask(String taskDescription) {

		ArrayList<Task> positiveMatches = new ArrayList<Task>();
		for (int i = 0; i < projectsList.size(); i++) {
			ArrayList<Task> tasksInCurrProject = projectsList.get(i)
					.retrieveAllTasks();
			for (int j = 0; j < tasksInCurrProject.size(); j++) {
				Task currTask = tasksInCurrProject.get(j);
				if (currTask.getTaskDescription().contains(taskDescription)) {
					positiveMatches.add(currTask);
				}
			}
		}
		return positiveMatches;
	}

	/**
	 * This method helps find the index of a project given its index
	 * 
	 * @param projectName
	 *            is the name of the project whose index we wish to find
	 * @return
	 */
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

	/**
	 * This method helps to check is a task's deadline has passed.
	 * 
	 * @param currTask
	 *            is the task that is being compared
	 * @return
	 */
	private boolean isTaskOverDue(Task currTask) {
		Date deadLineForCurrTask = currTask.getDeadline();
		Date date = new Date();
		if (deadLineForCurrTask.before(date)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method helps to find the index of the project given the name of a
	 * project
	 * 
	 * @param projectName
	 * @return
	 */
	private Project findProject(String projectName) {

		int index = findIndex(projectName);
		Project p = projectsList.get(index);
		return p;
	}

	/**
	 * This method helps to check if the program contains at least one task
	 * 
	 * @return true if a task exists
	 */
	private boolean isThereATask() {

		for (int i = 0; i < projectNames.size(); i++) {
			Project currProject = projectsList.get(i);
			if (currProject.retrieveAllTasks().isEmpty()) {
				continue;
			} else if (!currProject.retrieveAllTasks().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method helps to check if a DisplayTask object exists for the given
	 * date
	 * 
	 * @param curr
	 *            is the task whose date is currently being checked
	 * @return
	 */
	private int checkIndexOfDeadlineObject(Task currTask) {

		for (int i = 0; i < ListByDate.size(); i++) {
			if (isDateEqual(currTask.getDeadline(), ListByDate.get(i).getDate())) {
				return i;
			} else {
				continue;
			}
		}
		return -1;
	}

	/**
	 * This method helps to set the date in the format used internally within
	 * the program
	 */
	private Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, 0, 0, 0);
		Date date = cal.getTime(); // get back a Date object
		return date;
	}

	/**
	 * This method helps to compare two dates. Helps in sorting them out when
	 * they are displayed.
	 */
	public int compareDate(Task task1, Task task2) {
		if (task1.getDeadline() == null && task2.getDeadline() == null) {
			return 0;
		} else if (task1.getDeadline() != null && task2.getDeadline() == null) {
			return 1;
		} else if (task1.getDeadline() == null && task2.getDeadline() != null) {
			return -1;
		} else if (task1.getDeadline() != null && task2.getDeadline() != null) {
			return task1.getDeadline().compareTo(task2.getDeadline());
		}

		return 0;
	}

	/**
	 * Updates the completion status of a task and ensures that the backend is
	 * updated too.
	 * 
	 * @param mostRecentTask
	 * @throws IOException
	 */
	private void updateBackEnd(Task mostRecentTask) throws IOException {
		String pName = mostRecentTask.getProjectName();
		int index = findIndex(pName);
		if (index < -1) {
			UIHandler.getInstance().printToDisplay("error");
		} else {
			completeTaskFromProj(mostRecentTask);
			
		//	mostRecentTask.setStatus(); // flips completion status
		//	projectsList.get(index).addTask(new Task(mostRecentTask.getTaskDescription(), mostRecentTask.getStartDate(), mostRecentTask.getDeadline(), mostRecentTask.getProjectName(), mostRecentTask.isCompleted()));
		}
	}
	
	private void completeTaskFromProj(Task taskToBeDeleted) throws IOException {
		String projectName = taskToBeDeleted.getProjectName();
		int indexProject = findIndex(projectName);

		Project currProject = projectsList.get(indexProject);

		currProject.deleteTask(taskToBeDeleted);
		ArrayList<Task> taskList = currProject.retrieveAllTasks();

		if (taskList.isEmpty()
				&& !currProject.getProjectName().equals("default")) {

			// UIHandler.getInstance().printToDisplay(currProject.getProjectName()
			// + " has been removed. ");
			projectsList.remove(indexProject);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
		//	UIHandler.getInstance().printToDisplay(taskToBeDeleted.getTaskDescription() + " has been removed");
		//	UIHandler.getInstance().printToDisplay(projectName + MESSAGE_REMOVE_SUCCESS);
		} else {
		//	UIHandler.getInstance().printToDisplay(taskToBeDeleted.getTaskDescription() + MESSAGE_REMOVE_SUCCESS);
		}
		
		addToUndoStack("delete", taskToBeDeleted.getProjectName(), taskToBeDeleted, taskToBeDeleted.getParity());
	}
	
	

	/**
	 * This method helps to parse the date input into a more readable string
	 * 
	 * @param input
	 * @return
	 */
	private String parseDate(String input) {
		String[] components = input.split("-");
		String date = components[0];
		String month = components[1];
		String year = components[2];

		return date + " " + convertToMonth(month) + " " + year;
	}

	private String convertToMonth(String month) {
		return months[Integer.parseInt(month)];
	}

	private void populateMonths() {
		months[0] = "Jan";
		months[1] = "Feb";
		months[2] = "Mar";
		months[3] = "Apr";
		months[4] = "May";
		months[5] = "Jun";
		months[6] = "Jul";
		months[7] = "Aug";
		months[8] = "Sep";
		months[9] = "Oct";
		months[10] = "Nov";
		months[11] = "Dec";
	}
}
