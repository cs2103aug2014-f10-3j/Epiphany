package Logic.Engine;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.AttributedString;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import Logic.Exceptions.CancelDeleteException;
import Logic.Exceptions.CancelEditException;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.Interpreter.UIHandler;
import Logic.CommandType.*;
import Storage.Writer;
import Storage.Reader;

/**
 * This singleton class is essentially the backbone of this program. All data
 * manipulation takes place in this class.
 * 
 * Using this class, a task is assigned into different projects of Type Project
 * which essentially contains an ArrayList of objects of type Task. These
 * objects store all the information essential for storage and manipulation of
 * tasks. The projects have their names stored in a global ArrayList for easy
 * reference and another ArrayList of Projects, projectsList is used to store
 * all the projects.
 * 
 * //@author A0110924R
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
	private static final int N = 999;
	private static final String MESSAGE_RESET = "System restarted!";

	/****************************** Data Structures and Objects ********************/

	private static EpiphanyInterpreter interp;
	private static Engine engine;
	public static ArrayList<String> projectNames;
	public static ArrayList<Project> projectsList;
	private static Stack<PastCommands> undoStack;
	private static Stack<PastCommands> redoStack;
	public static ArrayList<DisplayObject> ListByDate;
	private static String[] months;

	private Engine() throws IOException, ParseException {
		engine = this;
		run();
	}

	/**
	 * Singleton implementation of Engine
	 */
	public static Engine getInstance() throws IOException, ParseException {
		if (engine == null) {
			return new Engine();
		}
		return engine;
	}

	/***************************** Engine Initialization Methods ***********************************/

	/**
	 * Initializes the Engine. It also populates the engine with the files that
	 * are contained within storage. 
	 */
	
	private void run() throws IOException, ParseException {

		new ASCIIArt().generateArt("EPIPHANY");
		UIHandler.getInstance().printToDisplay(MESSAGE_WELCOME);
		initializeDS();
		initializeEngine();
	}

	/**
	 * Initializes the main data structures to be used by engine. 
	 * @author A0119264E
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
	 * Reads in relevant saved data and boots Epiphany Engine.
	 * 
	 */
	//@author A0119264E
	private void initializeEngine() throws IOException, FileNotFoundException,
			ParseException {

		int noOfProjects = countLines("projectMasterList");

		if (noOfProjects == 0) {

			createDefault(); // Default project is created.
		} else {

			populateProjectNames();
			populateProjectsWithTasks();
		}
	}

	/**
	 * Creates the default project //@author A0119264E
	 * 
	 */
	//@author A0119264E
	private void createDefault() throws IOException {
		projectNames.add("default");
		projectsList.add(new Project("default", new ArrayList<Task>()));
		Writer.generateDefault();
	}

	/**
	 * Adds all tasks to their projects within engine. 
	 * 
	 */
	//@author A0119264E
	private void populateProjectsWithTasks() throws FileNotFoundException,
			IOException, ParseException {
		Reader reader = new Reader(projectNames, projectsList);
		reader.readProjectData();
	}

	/**
	 * Adds all the project names to engine.
	 * 
	 * 
	 */
	//@author A0119264E
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

	/********************** Command Types Filter ***********************************/

	public enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, RESET, EXIT, INVALID, SEARCH, EDIT, UNDO, REDO, COMPLETE
	};

	/**
	 * Takes a command type input, as is given by the interpreter and returns
	 * the appropriate case to be executed. 
	 * @author A0118794R
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
		} else if (commandType.getType().equalsIgnoreCase("reset")) {
			return CommandTypesEnum.RESET;
		} else {
			return null;
		}
	}

	/**
	 * This method helps to execute the command as is expected from the above
	 * method.
	 * @author A0118794R
	 */
	public void executeCommand(CommandType userCommand) throws IOException,
			ParseException {
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
	 * This method helps to check if the task being entered is a duplicate and
	 * subsequently adds it to the appropriate project. 
	 */
	//@author A0110924R
	private void add(String taskDescription, Date dateFrom, Date dateTo,
			String projectName) throws IOException {

		Task incomingTask = new Task(taskDescription, dateFrom, dateTo,
				projectName, false);

		// Duplicate check.
		if (projectNames.contains(projectName)) {
			int indexProj = findIndex(projectName);
			Project proj = projectsList.get(indexProj);

			if (proj.containsTask(incomingTask)) {
				UIHandler.getInstance()
						.printToDisplayRed(MESSAGE_ADD_DUPLICATE);
				return;
			}
		}

		if (projectNames.contains(projectName)) {

			int index = findIndex(projectName);
			Project currProject = projectsList.get(index);
			currProject.addTask(incomingTask);
			projectsList.set(index, currProject);
		} else {

			if (projectName.equals("default")) {
				int index = 0;
				Project currProject = projectsList.get(index);
				currProject.addTask(incomingTask);
				projectsList.set(index, currProject);
			}

			else {
				projectNames.add(projectName);
				ArrayList<Task> temp = new ArrayList<Task>();
				temp.add(incomingTask);
				projectsList.add(new Project(projectName, temp));
				Writer.updateProjectMasterList(projectName, projectNames);
			}
		}

		UIHandler.getInstance().printToDisplayGreen(MESSAGE_ADD);
		addToUndoStack("add", projectName, incomingTask,
				incomingTask.getParity());
	}

	/********************** Delete Methods ***********************************/
	/**
	 * This method helps to delete tasks and projects. This method is subdivided
	 * into many components; by virtue of their unique circumstance. A phrase
	 * that is unique to any task would result in immediate deletion of that
	 * Task. If there are, however, more than one tasks with a common phrase,
	 * all those tasks would be reflected where the specific task or group of
	 * selected tasks may then be removed. Projects may be deleted in their
	 * entirety as well.
	 * 
	 */
	//@author A0110924R
	private void delete(String taskDescription, String projectName)
			throws IOException {
		if (taskDescription == null && !projectName.equals("default")) {
			Project projToDelete = projectsList.get(findIndex(projectName));
			ArrayList<Task> tasksToDelete = projToDelete.retrieveAllTasks();

			for (Task t : tasksToDelete) {
				t.flipParity();
				removeTaskFromProj(t);
			}

			return;
		}

		ArrayList<Task> tasksToDisplayForDelete = searchForTask(taskDescription);

		if (tasksToDisplayForDelete.size() == 0) {
			UIHandler.getInstance()
					.printToDisplayRed(MESSAGE_NOTHING_TO_DELETE);

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
					UIHandler.getInstance().printToDisplayRed(
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
			UIHandler.getInstance().printToDisplayRed("No such task exists!");
		}
	}

	/********************************* Edit Methods ***********************************/

	/**
	 * This method allows us to edit an already entered task. This method has a
	 * fail safe where it asks the user to confirm the index of the task they
	 * wish to update/edit even if that is the only one.
	 */
	 //@author A0110924R
	private void edit(String taskDescription, String projectName)
			throws IOException, ParseException {

		Task recentTask = new Task();

		ArrayList<Task> temp = search(taskDescription);

		if (!temp.isEmpty()) {

			try {
				UIHandler.getInstance().printToDisplay(
						MESSAGE_SPECIFY_INDEX_FOR_EDIT);

				int input = interp.askForAdditionalInformationForEdit();

				Task taskToBeEdited = temp.get(input - 1);
				recentTask = taskToBeEdited;
				projectName = taskToBeEdited.getProjectName();
				int index = findIndex(projectName);

				Project currProject = projectsList.get(index);
				currProject.deleteTask(taskToBeEdited);

				UIHandler.getInstance().printToDisplay(
						MESSAGE_UPDATE_TASK_FOR_EDIT);

				CommandType newUserCommand = interp.askForNewTaskForEdit();
				executeCommand(newUserCommand);

			} catch (CancelEditException e) {
				return;
			}

		} else {
			UIHandler.getInstance().printToDisplayRed(
					MESSAGE_ERROR_UNABLE_TO_EDIT);
		}

		addToUndoStack("edit", recentTask.getProjectName(), null, false);
	}

	/********************** Search Methods ***********************************/

	/**
	 * This method allows enables the user to search within a specified project
	 * and or in general for a certain phrase which could be a constituent of
	 * any of the tasks.
	 * 
	 * 
	 */
	//@author A0110924R
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
	 * This method helps to search for a particular phrase from within the
	 * entire program. 
	 * 
	 */
	//@author A0110924R
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

	/****************************** Display Method ***********************************/

	/**
	 * This method is used to print to terminal. It enables display by date,
	 * project as well as an overall display. The default display works by
	 * printing the a date which constitutes the deadline and then the tasks
	 * that fall on that deadline. It also enables support for tasks which are
	 * overdue. These would be presented first given their urgency followed by
	 * all other task with deadlines closest to the current date.
	 *
	 */
	//@author A0110924R
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

	/********************************** Undo Method ***********************************/
	/**
	 * The undo method helps to revert, infinitely until exhaustion, the recent
	 * add, delete- single task and entire project, edit and mark as complete
	 * operations.
	 * 
	 */
	//@author A0119264E
	private void undo() throws IOException {

		if (undoStack.isEmpty()) {
			UIHandler.getInstance().printToDisplayRed(MESSAGE_UNDO_ERROR);
		} else {
			PastCommands mostRecent = undoStack.pop();

			String typeOfCommand = mostRecent.getType();
			Task task = mostRecent.getTask();
			if (!projectNames.contains(mostRecent.getProjectName())) {

				Task mostRecentTask = mostRecent.getTask();
				add(mostRecentTask.getTaskDescription(),
						mostRecentTask.getStartDate(),
						mostRecentTask.getDeadline(),
						mostRecentTask.getProjectName());
				undoStack.pop();
				redoStack.push(mostRecent);

				PastCommands top = undoStack.peek();

				if (top.getTask().getParity()) {
					while (top.getType().equals("delete")
							&& top.getProjectName().equals(
									mostRecentTask.getProjectName())) {
						PastCommands recent = undoStack.pop();
						redoStack.push(recent);
						Task toAdd = recent.getTask();

						add(toAdd.getTaskDescription(), toAdd.getStartDate(),
								toAdd.getDeadline(), toAdd.getProjectName());
						undoStack.pop();
						if (undoStack.isEmpty()) {
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
					add(mostRecentTask.getTaskDescription(),
							mostRecentTask.getStartDate(),
							mostRecentTask.getDeadline(),
							mostRecentTask.getProjectName());
					undoStack.pop();
					redoStack.push(mostRecent);
					if (!undoStack.isEmpty()) {
						PastCommands top = undoStack.peek();

						while (top.getType().equals("delete")) {

							if (!top.getProjectName().equals(
									mostRecentTask.getProjectName())) {
								return;
							}

							PastCommands recent = undoStack.pop();
							redoStack.push(recent);
							Task toAdd = recent.getTask();

							add(toAdd.getTaskDescription(),
									toAdd.getStartDate(), toAdd.getDeadline(),
									toAdd.getProjectName());
							undoStack.pop();
							if (undoStack.isEmpty()) {
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

				UIHandler.getInstance().printToDisplayGreen(
						MESSAGE_UNDO_SUCCESS);
			}
		}
	}

	/********************************** Redo Method ***********************************/
	/**
	 * This method helps to revert undo's, infinitely until exhaustion.
	 * 
	 */
	//@author A0119264E
	private void redo() throws IOException {

		if (redoStack.isEmpty()) {
			UIHandler.getInstance().printToDisplayRed(MESSAGE_REDO_ERROR);
		} else {
			PastCommands mostRecent = redoStack.pop();

			String type = mostRecent.getType();
			Task task = mostRecent.getTask();

			if (type.equals("add")) {
				if (projectNames.contains(task.getProjectName())) {
					// Match found

					int index = findIndex(task.getProjectName());
					Project p = projectsList.get(index);
					p.addTask(task);

				}

			} else if (type.equals("delete")) {
				if (projectNames.contains(task.getProjectName())) {

					Task toAdd = mostRecent.getTask();
					deleteForRedo(toAdd.getTaskDescription(),
							toAdd.getProjectName());
					if (!undoStack.isEmpty()) {
						undoStack.pop();
					}
					if (!redoStack.isEmpty()) {
						PastCommands top = redoStack.peek();

						if (top.getTask().getParity()) {
							while (top.getType().equals("delete")
									&& top.getProjectName().equals(
											toAdd.getProjectName())) {
								Task recent = redoStack.pop().getTask();
								delete(recent.getTaskDescription(),
										recent.getProjectName());
								if (!undoStack.isEmpty()) {
									undoStack.pop();
								}
								if (redoStack.isEmpty()) {
									return;
								}
								top = redoStack.peek();
							}

						} else {
							while (top.getType().equals("delete")) {
								Task recent = redoStack.pop().getTask();
								deleteForRedo(recent.getTaskDescription(),
										recent.getProjectName());
								if (!undoStack.isEmpty()) {
									undoStack.pop();
								}
								if (redoStack.isEmpty()) {
									return;
								}
								top = redoStack.peek();
							}
						}
					}
				}
			} else if (type.equals("edit")) {
				PastCommands existingTask = redoStack.pop();

				PastCommands newTask = redoStack.pop();

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

			UIHandler.getInstance().printToDisplayGreen(MESSAGE_REDO_SUCCESS);
		}
	}

	private void addToUndoStack(String type, String projectName,
			Task incomingTask, boolean parity) {
		undoStack.push(new PastCommands(type, incomingTask, projectName));
	}

	/************************ Mark as complete method *************************/

	/**
	 * This method marks a task as complete and subsequently removes it from the
	 * project.
	 * @author A0118794R
	 */
	
	private void checkCompleteTask(String input) throws IOException {
		Task mostRecentTask = new Task();
		ArrayList<Task> tasksToBeCompleted = new ArrayList<Task>();
		tasksToBeCompleted = searchForTask(input);

		if (tasksToBeCompleted.size() == 0) {
			UIHandler.getInstance().printToDisplayRed(MESSAGE_NO_ENTRY);

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
					UIHandler.getInstance().printToDisplayRed(
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
			UIHandler.getInstance().printToDisplayRed(
					MESSAGE_ERROR_INVALID_TASK);
		}
	}
	

	/********************************* Helper methods *************************************/

	/********************************* Helpers for display ********************************/

	/**
	 * This method helps to display all the tasks according to their deadlines.
	 * 
	 */
	// @author A0110924R
	private void displayAll() {

		for (DisplayObject disp : ListByDate) {

			Date currDate = disp.getDate();

			if (currDate == null) {
				ArrayList<Task> listDisObj = disp.getList();

				if (!listDisObj.isEmpty()) {
					if (listDisObj.get(0).hasDeadLine()) {
						UIHandler.getInstance().printToDisplayRed(
								"------------");
						UIHandler.getInstance().printToDisplayRed(
								"> " + "Overdue:" + " |");
						UIHandler.getInstance().printToDisplayRed(
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

	/**
	 * This method is used to collate all the tasks for display of all tasks. It
	 * works by displaying the tasks under their deadline(in date format)
	 * header. Overdue tasks are shown first and floating tasks are shown last
	 * with all deadline and interval tasks in between according to their
	 * deadlines.
	 * 
	 */
	// @author A0110924R
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

	/**
	 * This method helps to display tasks for a specific date.
	 * 
	 */
	// @author A0110924R
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
					if(currTask.hasDeadLine()){
					if (isDateEqual(dateToBeDisplayedBy, currTask.getDeadline())) {

						toBeDisplayed.add(currTask);
					}else{continue;}
					}
				}
			}
			displayArrayList(toBeDisplayed);
		}
	}

	/**
	 * This method helps to display methods for search. The format here differs
	 * from the conventional display as it is not sorted by date.
	 * 
	 */
	// @author A0110924R
	private void displayArrayListForSearch(ArrayList<Task> projectList) {

		if (projectList.isEmpty()) {
			UIHandler.getInstance().printToDisplayRed(MESSAGE_INVALID_SEARCH);
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
	 * This method helps to display all the Tasks within a specified project.
	 * 
	 */
	// @author A0110924R
	private void displayProject(String projectName) {
		int index = findIndex(projectName);
		Project curr = projectsList.get(index);
		displayArrayList(curr.retrieveAllTasks());
	}

	/**
	 * This method helps to display an ArrayList of tasks.
	 * 
	 */
	// @author A0110924R
	private void displayArrayList(ArrayList<Task> taskList) {

		if (taskList.isEmpty()) {
			UIHandler.getInstance().printToDisplayRed(MESSAGE_INVALID_SEARCH);
		}

		int counter = 1;
		for (Task t : taskList) {
			UIHandler.getInstance().printToDisplay(
					counter + ". " + t.printTaskForDisplay("display"));
			counter++;
			UIHandler.getInstance().resetToDefault();
		}
		UIHandler.getInstance().printToDisplay("\n");
	}

	/**
	 * This method helps to check is a task's deadline has passed.
	 * 
	 */
	// @author A0110924R
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
	 * This method helps to check if a DisplayTask object exists for the given
	 * date.
	 * 
	 */
	// @author A0110924R
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
	 * This method helps to check if the program contains at least one task
	 * 
	 */
	// @author A0110924R
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

	/********************************** Helper for delete ******************************/
	/**
	 * This method helps to remove tasks from a certain project. Used for
	 * undo/redo functions where the an entire project deletion may be undone in
	 * an undo/redo command.
	 *
	 */
	// @author A0119264E

	private void removeTaskFromProj(Task taskToBeDeleted) throws IOException {
		String projectName = taskToBeDeleted.getProjectName();
		int indexProject = findIndex(projectName);

		Project currProject = projectsList.get(indexProject);

		currProject.deleteTask(taskToBeDeleted);
		ArrayList<Task> taskList = currProject.retrieveAllTasks();

		if (taskList.isEmpty()
				&& !currProject.getProjectName().equals("default")) {

			projectsList.remove(indexProject);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
			UIHandler.getInstance().printToDisplayGreen(
					taskToBeDeleted.getTaskDescription() + " has been removed");
			UIHandler.getInstance().printToDisplayGreen(
					projectName + MESSAGE_REMOVE_SUCCESS);
		} else {
			UIHandler.getInstance().printToDisplayGreen(
					taskToBeDeleted.getTaskDescription()
							+ MESSAGE_REMOVE_SUCCESS);
		}

		addToUndoStack("delete", taskToBeDeleted.getProjectName(),
				taskToBeDeleted, taskToBeDeleted.getParity());
	}

	/**
	 * THis method assists in the deletion of a task such that it facilitates
	 * future redo's.
	 * 
	 */
	// @author A0119264E
	private void deleteForRedo(String taskDescription, String projectName)
			throws IOException {
		if (taskDescription == null && !projectName.equals("default")) {
			// Deleting a project instead.
			Project projToDelete = projectsList.get(findIndex(projectName));
			ArrayList<Task> tasksToDelete = projToDelete.retrieveAllTasks();

			for (Task t : tasksToDelete) {
				t.flipParity();
				removeTaskFromProj(t);
			}

			return;
		}

		int index = findIndex(projectName);
		ArrayList<Task> taskList = projectsList.get(index).retrieveAllTasks();

		for (Task t : taskList) {
			if (t.getTaskDescription().equals(taskDescription)) {
				removeTaskFromProj(t);
			}
		}
	}

	/********************************** Helper for Search ******************************/

	/**
	 * This methods helps to search for all tasks matching a certain task
	 * description.
	 * 
	 * 
	 */
	// @author A0110924R
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

	/************************************ Helpers for Mark as complete ****************************/
	
	/**
	 * This method helps to mark tasks as complete.
	 * @author A0118794R
	 */

	public void markTaskDescriptionAsComplete(Task input) {
		if (input.isCompleted() == true) {

			String toDisplay = input.getDuplicateTaskDescription()
					+ MESSAGE_MARKED_AS_ONGOING;
			UIHandler.getInstance().printToDisplayGreen(toDisplay);
		} else {

			input.setInstruction(input.getDuplicateTaskDescription());
			String toDisplay = input.getDuplicateTaskDescription()
					+ MESSAGE_MARKED_AS_COMPLETE;
			UIHandler.getInstance().printToDisplayGreen(toDisplay);
		}
	}
	/**
	 * This method helps to strike-through text.
	 * @author A0118794R
	 */
	public String strikeThroughText(String input) {
		String output;
		AttributedString str_attribute = new AttributedString(input);
		str_attribute.addAttribute(TextAttribute.STRIKETHROUGH, input.length());
		output = str_attribute.toString();
		return output;
	}
	/**
	 * Updates the completion status of a task and ensures that the the update
	 * is reflected in storage.
	 * @author A0118794R
	 */
	private void updateBackEnd(Task mostRecentTask) throws IOException {
		String pName = mostRecentTask.getProjectName();
		int index = findIndex(pName);
		if (index < -1) {
			UIHandler.getInstance().printToDisplayRed("error");
		} else {
			completeTaskFromProj(mostRecentTask);
		}
	}

	/**
	 * Updates the completion status of a task which is in a project.
	 * @author A0118794R
	 */
	
	private void completeTaskFromProj(Task taskToBeDeleted) throws IOException {
		String projectName = taskToBeDeleted.getProjectName();
		int indexProject = findIndex(projectName);

		Project currProject = projectsList.get(indexProject);

		currProject.deleteTask(taskToBeDeleted);
		ArrayList<Task> taskList = currProject.retrieveAllTasks();

		if (taskList.isEmpty()
				&& !currProject.getProjectName().equals("default")) {

			projectsList.remove(indexProject);
			projectNames.remove(projectName);
			Writer.deleteProject(projectName, projectNames);
		} else {
		}

		addToUndoStack("delete", taskToBeDeleted.getProjectName(),
				taskToBeDeleted, taskToBeDeleted.getParity());
	}

	/******************************** Reset **********************************/
	/**
	 * This method helps clear all projects and tasks from the running copy of
	 * engine.
	 * 
	 */
	// @author A0110924R
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

	/************************************* Date Helpers ***************************************/
	/**
	 * This method helps to compare two dates. Helps in sorting them out when
	 * they are displayed.
	 * 
	 */
	// @author A0110924R
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
	 * This method helps to set the date in the format used internally within
	 * the program.
	 * 
	 */
	// @author A0110924R
	private Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, 0, 0, 0);
		Date date = cal.getTime();
		return date;
	}

	/**
	 * This method helps to parse the date input into a more readable string.
	 * 
	 */
	// @author A0110924R
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

	/********************************** Multi-purpose helpers ******************************/
	/**
	 * This method helps to check if two dates are equal.
	 * 
	 */
	// @author A0110924R
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
	 * This method helps find the index of a project given its name.
	 * 
	 */
	// @author A0110924R
	private int findIndex(String projectName) {
		for (int i = 0; i < projectNames.size(); i++) {
			if (projectNames.get(i).equals(projectName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method helps to find the index of the project given the name of a
	 * project
	 * 
	 */
	// @author A0110924R
	private Project findProject(String projectName) {
		int index = findIndex(projectName);
		Project p = projectsList.get(index);
		return p;
	}

}
