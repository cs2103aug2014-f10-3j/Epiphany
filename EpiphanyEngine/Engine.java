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

public class Engine {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<String> projectNames;
	public static ArrayList<Task> defaultProject;
	public static ArrayList<Project> EpiphanyMain;
	public static ArrayList<Date> testDate1;
	public static ArrayList<Task> testDateSort;

	// public static final String MESSAGE_WELCOME = " Welcome to Epiphany!\n";
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
	private static final String ERROR_MESSAGE_INVALID_FORMAT = null;
	private static final String ERROR_WRONG_INPUT = null;
	private static final String ERROR_COMMAND_TYPE_NULL = null;
	private static final String MESSAGE_COMMAND = "command: \n";

	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH
	};

	public Engine() {
		run();
	}

	public void run() {
		EpiphanyMain = new ArrayList<Project>();
		projectNames = new ArrayList<String>();
		defaultProject = new ArrayList<Task>();
		EpiphanyMain.add(new Project("default", defaultProject));// TEST

		testDate1 = new ArrayList<Date>();
		testDateSort = new ArrayList<Task>();
		projectNames.add("default");
	}

	public ArrayList<Task> search(String phrase, String projectName) {
		ArrayList<Task> searchResult = new ArrayList<Task>();
		ArrayList<Task> temp;
		for (int i = 0; i < EpiphanyMain.size(); i++) {
			if (EpiphanyMain.get(i).getProjectName().equals(projectName)) {
				temp = EpiphanyMain.get(i).getTaskList();
				for (int j = 0; j < temp.size(); j++) {
					if (temp.get(j).getInstruction().toLowerCase()
							.contains(phrase.toLowerCase())) {
						searchResult.add(temp.get(j));
					}

					else {
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

	// Displays all projects
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

	// to-do in interpreter
	/*
	 * public void display(String userCommand) { if (userCommand.equals("all"))
	 * { this.displayAll(EpiphanyMain); } else { // need to check if its a valid
	 * project first this.displayProject(userCommand); } }
	 */

	// Helper function: Displays an ArrayList project
	public ArrayList<Task> displayArrayList(ArrayList<Task> expected) {

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
		return expected;
	}

	/**
	 * displays the contents of any one project
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
	 * Displays the names and indices of all the projects that exist without
	 * showing the default project
	 */
	public void displayProjects() {
		int count = 1;
		if (projectNames.isEmpty()) {
			System.out.println("Projects folder is empty");
		} else {
			for (String s : projectNames) {
				System.out.println(count + ". " + s + ".");
				count++;
			}
		}
	}

	public void addTask(String instruction, Date date, String project) {
		if (instruction == null) {
			System.out.println("Please give a task name"); // UI Handler
		} else {
			if (date == null && project.equals("default")) {

				ArrayList<Task> currProjectList = EpiphanyMain.get(0)
						.getTaskList();
				currProjectList.add(new Task(instruction, null, null));
				System.out.println("Task has been added!"); // UI handler

			} else if (date == null && !project.equals("default")) {// Project
																	// included
																	// no date.

				if (projectNames.contains(project)) { // does not deal with
														// upper or lower cases
														// yet.
					for (int i = 0; i < EpiphanyMain.size(); i++) {
						if (EpiphanyMain.get(i).getProjectName()
								.equals(project)) {
							ArrayList<Task> currentProjectList = EpiphanyMain
									.get(i).getTaskList();
							currentProjectList.add(new Task(instruction, null,
									project));
						}
					}
				} else if (!projectNames.contains(project)) {
					projectNames.add(project);
					ArrayList<Task> latest = new ArrayList<Task>();
					latest.add(new Task(instruction, null, project));
					EpiphanyMain.add(new Project(project, latest));
					System.out.println("Task has been added!");
				}

			} else if (date != null && project.equals("default")) {
				ArrayList<Task> currList = EpiphanyMain.get(0).getTaskList();
				currList.add(new Task(instruction, date, "default"));
				sortDateInProject(currList);
				// defaultProject.sort()
				System.out.println("Task has been added!");
			} else {
				if (projectNames.contains(project)) {

					for (int i = 0; i < EpiphanyMain.size(); i++) {
						if (EpiphanyMain.get(i).getProjectName()
								.equals(project)) {
							ArrayList<Task> currentProjectList = EpiphanyMain
									.get(i).getTaskList();
							currentProjectList.add(new Task(instruction, date,
									project));
							sortDateInProject(currentProjectList);
						}
					}
				}
			}
		}

	}

	public void save(Project toBeUpdated) {

	}

	public void save() {

	}

	public void deleteTask(String instruction, String projectName) {
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

	public void deleteProject(ArrayList<Task> projectName) {
		if (EpiphanyMain.contains(projectName)
				&& projectNames.contains(projectName)) {
			int count = EpiphanyMain.lastIndexOf(projectName);
			int count1 = projectNames.lastIndexOf(projectName);
			EpiphanyMain.remove(count);
			projectNames.remove(count1);
		}
	}

	public void displayTaskToDelete(String phrase, ArrayList<Task> temp) {
		for (int i = 0; i < temp.size(); i++) {
			System.out.println(i + 1 + ". " + temp.get(i).getInstruction()
					+ " " + temp.get(i).getProjectName());
		}
	}

	// sorts by deadline. meaning the phrase with the earliest date will show
	// first.
	// date format is quite crude for now, can be polymorphised later.
	// for now date is sorted according to DDMMYYYY
	// will specify to include time as well. TTTT to settle the cases of tasks
	// on the same day.

	public Date formatDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

	public void sortDateInList(ArrayList<Date> testDate) {
		Collections.sort(testDate, new customComparator());
	}

	public ArrayList<Task> sortDateInProject(ArrayList<Task> project) {
		Collections.sort(project, new taskDateComparator());
		return project;

	}

	public class taskDateComparator implements Comparator<Task> {
		@Override
		public int compare(Task one, Task two) {
			return one.getDeadline().compareTo(two.getDeadline());
		}
	}

	public class customComparator implements Comparator<Date> {
		@Override
		public int compare(Date one, Date two) {
			return one.compareTo(two);
		}
	}

	public String toString(Date deadLine) {
		String dateFormat = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String stringDate = sdf.format(deadLine);
		return stringDate;
	}

	// User Interface Methods:
	// We receive instruction from the interpreter:
	/*
	 * e.g.: add buy groceries for upcoming week need some way to filter what is
	 * <Date> & <ProjectName>
	 * 
	 * for now its just the basic
	 */
	public void executeCommand(CommandType userCommand) {
		CommandTypesEnum commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD:
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			addTask(addUserCommand.getDescription(), addUserCommand.getDate(),
					addUserCommand.getProjectName());
			break;
		case DISPLAY: // displays the entire arraylist
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

	private String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	private void printToUser(String text, String arg1, String arg2) {
		String printText = String.format(text, arg1, arg2);
		System.out.print(printText);
	}

	/**
	 * Creates a new text file to store the new project file
	 * 
	 * @param fileName
	 *            is the name of the file/project
	 * @param items
	 *            is the ArrayList of items that is inside this project
	 * @throws IOException
	 */
	public void createNewFile(String fileName, ArrayList<Task> items)
			throws IOException {

		FileWriter f = new FileWriter(fileName);
		BufferedWriter writer = new BufferedWriter(f);

		int counter = 1;

		for (Task s : items) {
			writer.write(counter + ". " + s.getInstruction());// or
			// s.instruction
			counter++;
			writer.newLine();
			writer.flush();
		}
		writer.close();
	}

}
// treeset for iteration using sort//
