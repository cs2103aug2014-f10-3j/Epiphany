//This is how we roll
/* Epiphany Engine v0.1 alpha release
 * Contains basic functionality to CRUD as well as Storage. 
 * More details about the methods will be added soon
 * 
 * @author Moazzam & Wei Yang
 * For date & time we will be using a custom class called Joda Time.
 * 
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

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
 * It currently has the abilty to add, delete, search and display. Additionally,
 * it has complete project management.
 * 
 * Missing: Storing projects in separate files (save).
 * 
 * @author Moazzam
 *
 */
public class Engine {

	public static ArrayList<String> projectNames;
	public static ArrayList<Task> defaultProject;
	public static ArrayList<Project> EpiphanyMain;
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
	 * @author Moazzam
	 *
	 */
	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH
	};

	public Engine() {
		run();
	}

	/**
	 * Performs the operations necessary for the execution of the Logic
	 * component of the program.
	 */
	private void run() {
		EpiphanyMain = new ArrayList<Project>();
		projectNames = new ArrayList<String>();
		createDefault();
	}

	/**
	 * This function creates a default project which is then automaticlly
	 * available for use.
	 */
	private void createDefault() {
		ArrayList<Task> defaultInsertion = new ArrayList<Task>();
		EpiphanyMain.add(new Project("default", defaultInsertion));
		projectNames.add("default");

	}

	private boolean checkContains(int i, String projectName) {
		if (EpiphanyMain.get(i).getProjectName().equals(projectName)) {
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

		for (int i = 0; i < EpiphanyMain.size(); i++) {
			if (checkContains(i, projectName)) {
				temp = EpiphanyMain.get(i).getTaskList();
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
		if (EpiphanyMain.size() == 1
				&& EpiphanyMain.get(0).getTaskList().isEmpty()) {
			System.out.println("Nothing to display.");// UI handler
		} else {
			for (int i = 0; i < EpiphanyMain.size(); i++) {
				Project curr = EpiphanyMain.get(i);
				System.out.println("Project: " + (i + 1)
						+ curr.getProjectName());
				ArrayList<Task> currentArrayList = curr.getTaskList();
				for (int j = 0; j < currentArrayList.size(); j++) {
					if (currentArrayList.isEmpty()) {
						System.out.println("IT IS EMPTY"); // UI handler
					} else {
						for (int k = 0; k < currentArrayList.size(); k++) {
							System.out.println((k + 1)
									+ curr.getTaskList().get(k)
											.getInstruction());
						}
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
		for (int i = 0; i < EpiphanyMain.size(); i++) {
			String nameTempProject = tempName.getProjectName().toLowerCase();
			String abc = EpiphanyMain.get(i).getProjectName().toLowerCase();
			if (abc.equals(nameTempProject)) {
				ArrayList<Task> temporaryProjectList = EpiphanyMain.get(i)
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

	/**
	 * Adds a task into the program. Adds the task into it specified project and
	 * sorts it according to its deadline.
	 * 
	 * @param instruction
	 * @param date
	 * @param projectName
	 */
	private void addTask(String instruction, Date date, String projectName) {
		if (instruction == null) {
			System.out.println("Please give a task name"); // UI Handler
		} else {
			if (date == null) {
				if (projectNames.contains(projectName.toLowerCase())) {
					for (int i = 0; i < EpiphanyMain.size(); i++) {
						if (EpiphanyMain.get(i).getProjectName()
								.equals(projectName)) {
							ArrayList<Task> currentProjectList = EpiphanyMain
									.get(i).getTaskList();
							currentProjectList.add(new Task(instruction,
									projectName));
						}
					}
				} else if (!projectNames.contains(projectName)) {
					projectNames.add(projectName);
					ArrayList<Task> latest = new ArrayList<Task>();
					latest.add(new Task(instruction, projectName));
					EpiphanyMain.add(new Project(projectName, latest));
					System.out.println("New project created");
					System.out.println("Task has been added!");
				}

			} else {// If date is not equal to null
				if (projectNames.contains(projectName)) {

					for (int i = 0; i < EpiphanyMain.size(); i++) {
						if (EpiphanyMain.get(i).getProjectName()
								.equals(projectName)) {
							ArrayList<Task> currentProjectList = EpiphanyMain.get(i).getTaskList();
							currentProjectList.add(new Task(instruction, date,
									projectName));
							sortDateInProject(EpiphanyMain.get(i).getTaskList());
							
						}
					}
				}
			}
		}

	}

	private void save(Project toBeUpdated) {
	}

	private void save() {
	}

	private void deleteTask(String instruction, String projectName) {
		if (!projectName.contains(projectName)) {
			System.out.println("Such a project does not exist");
		} else {
			for (int i = 0; i < EpiphanyMain.size(); i++) {
				String current = EpiphanyMain.get(i).getProjectName()
						.toLowerCase();
				if (current.contains(projectName.toLowerCase())) {
					ArrayList<Task> currTaskList = EpiphanyMain.get(i)
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
 * @param projectName	is the name of the project.
 */
	public void deleteProject(ArrayList<Task> projectName){
		if(EpiphanyMain.contains(projectName)
				&& projectNames.contains(projectName)) {
			int count = EpiphanyMain.lastIndexOf(projectName);
			int count1 = projectNames.lastIndexOf(projectName);
			EpiphanyMain.remove(count);
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
	 * @param dateStr	String that contains the date
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
	 * @param testDate  An ArryList of date that needs to be sorted.
	 */
	public void sortDateInList(ArrayList<Date> testDate) {
		Collections.sort(testDate, new customComparator());
	}

	/**
	 * Helps to sort the tasks within a project
	 * @param project The arrayList for a project that we update.
	 * @return	a project's taskList sorted by Date.
	 */
	private ArrayList<Task> sortDateInProject(ArrayList<Task> project) {
		Collections.sort(project, new taskDateComparator());
		return project;

	}
/**
 * This method helps compare two tasks by basing it on their deadlines.
 * @author Moazzam
 *
 */
	private class taskDateComparator implements Comparator<Task> {
		@Override
		public int compare(Task one, Task two) {
			return one.getDeadline().compareTo(two.getDeadline());
		}
	}
	/**
	 * This method helps compare two dates.
	 * @author Moazzam
	 *
	 */
	private class customComparator implements Comparator<Date> {
		@Override
		public int compare(Date one, Date two) {
			return one.compareTo(two);
		}
	}
	/**
	 * Helps to format the date and return the formatted date.
	 * @param deadLine
	 * @return the formatted date.
	 */
	public String toString(Date deadLine) {
		String dateFormat = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String stringDate = sdf.format(deadLine);
		return stringDate;
	}
/**
 * 
 * @param userCommand
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
// to-do in interpreter
/*
 * public void display(String userCommand) { if (userCommand.equals("all")) {
 * this.displayAll(EpiphanyMain); } else { // need to check if its a valid
 * project first this.displayProject(userCommand); } }
 */

// Helper function: Displays an ArrayList project
/*
 * private void addTask(String instruction, Date date, String project) { if
 * (instruction == null) { System.out.println("Please give a task name"); // UI
 * Handler } else { if (date == null && project.equals("default")) {
 * 
 * ArrayList<Task> currProjectList = EpiphanyMain.get(0) .getTaskList();
 * currProjectList.add(new Task(instruction, null, null));
 * System.out.println("Task has been added!"); // UI handler
 * 
 * } else if (date == null && !project.equals("default")) {// Project //
 * included // no date.
 * 
 * if (projectNames.contains(project)) { // does not deal with // upper or lower
 * cases // yet. for (int i = 0; i < EpiphanyMain.size(); i++) { if
 * (EpiphanyMain.get(i).getProjectName() .equals(project)) { ArrayList<Task>
 * currentProjectList = EpiphanyMain .get(i).getTaskList();
 * currentProjectList.add(new Task(instruction, null, project)); } } } else if
 * (!projectNames.contains(project)) { projectNames.add(project);
 * ArrayList<Task> latest = new ArrayList<Task>(); latest.add(new
 * Task(instruction, null, project)); EpiphanyMain.add(new Project(project,
 * latest)); System.out.println("Task has been added!"); }
 * 
 * } else if (date != null && project.equals("default")) { ArrayList<Task>
 * currList = EpiphanyMain.get(0).getTaskList(); currList.add(new
 * Task(instruction, date, "default")); sortDateInProject(currList); //
 * defaultProject.sort() System.out.println("Task has been added!"); } else { if
 * (projectNames.contains(project)) {
 * 
 * for (int i = 0; i < EpiphanyMain.size(); i++) { if
 * (EpiphanyMain.get(i).getProjectName() .equals(project)) { ArrayList<Task>
 * currentProjectList = EpiphanyMain .get(i).getTaskList();
 * currentProjectList.add(new Task(instruction, date, project));
 * sortDateInProject(currentProjectList); } } } } }
 * 
 * }
 */
