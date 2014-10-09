package EpiphanyEngine;
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

import Logic.CommandType.*;

public class Engine {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<String> projectNames;
	public static ArrayList<Task> defaultProject;
	public static ArrayList<ArrayList<Task>> EpiphanyMain;
	public static ArrayList<Date> testDate1;
	public static ArrayList<Task> testDateSort;

	//public static final String MESSAGE_WELCOME = " Welcome to Epiphany!\n";
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
	private Scanner sc;
	private Scanner sc2;

	// Global variable used to scan all the input from the users
	private static Scanner scanner = new Scanner(System.in);

	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH
	};
	
	public Engine() {
		run();
	} 

	public void testSort() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date one = sdf.parse("31-4-2014");
			Date two = sdf.parse("10-10-2014");
			Date three = sdf.parse("31-12-2014");
			Date four = sdf.parse("17-6-2014");
			testDate1.add(one);
			testDate1.add(two);
			testDate1.add(three);
			testDate1.add(four);
			Collections.sort(testDate1, new customComparator());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		EpiphanyMain = new ArrayList<ArrayList<Task>>();
		projectNames = new ArrayList<String>();
		defaultProject = new ArrayList<Task>();
		EpiphanyMain.add(defaultProject);
		testDate1 = new ArrayList<Date>();
		testDateSort = new ArrayList<Task>();
		projectNames.add("default");
	}
		
	public ArrayList<Task> search(String phrase, String projectName) {
		ArrayList<Task> searchResult = new ArrayList<Task>();
		for (int i = 0; i < EpiphanyMain.size(); i++) {
			for (int j = 0; j < EpiphanyMain.get(i).size(); j++) {
				Task line = (EpiphanyMain.get(i)).get(j);
				if (line.instruction.toLowerCase().contains(phrase.toLowerCase())) {
					searchResult.add(line);
				}
			}
		}
		if (searchResult.isEmpty()) {
			System.out.println(MESSAGE_INVALID_SEARCH);
			return searchResult; // empty
		}
		System.out.println("Search result:");
		displayArrayList(searchResult); // returns an arraylist of tasks
		return searchResult;
	}

	// Displays all projects
	public void displayAll(ArrayList<ArrayList<Task>> Epiphany) {
		if (Epiphany.size() == 1 && Epiphany.get(0).isEmpty()) {
			System.out.println("Nothing to display.");
		} else {
			for (int i = 0; i < Epiphany.size(); i++) {
				Task name = Epiphany.get(i).get(0);
				if (i == 0) {
					System.out.println("Project: Default");
				} else {
					System.out.println("Project: " + i + ". "
							+ name.ProjectName);
				}
				int counter = 1;
				for (int j = 0; j < Epiphany.get(i).size(); j++) {
					Task s = Epiphany.get(i).get(j);
					System.out.print(String.format(MESSAGE_DISPLAY, counter,
							s.instruction));
					System.out.println("	" + s.getDeadline());
					counter++;
				}
			}
		}
	}
	
	//to-do
	public void display(String userCommand) {
		if (userCommand.equals("all")){
			this.displayAll(EpiphanyMain);
		} else {
			// need to check if its a valid project first
			this.displayProject(userCommand);
		}
	}
	
	// Helper function: Displays an ArrayList project
	public ArrayList<Task> displayArrayList(ArrayList<Task> expected) {

		if (expected.isEmpty()) {
			System.out.println(String.format(MESSAGE_DISPLAY_ERROR));
		} else {

			int counter = 1;
			for (Task s : expected) {
				System.out.println(String.format(MESSAGE_DISPLAY, counter,
						s.instruction));
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
	public void displayProject(String name) {
		for (int i = 0; i < EpiphanyMain.size(); i++) {
			if (EpiphanyMain.get(i).get(0).getProjectName().equals(name)) {
				ArrayList<Task> temporaryProject = EpiphanyMain.get(i);
				displayArrayList(temporaryProject);
			} else {
				System.out.println("No such project exists.");
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
			System.out.println("Please give a task name"); // convert this to
															// static final
		} else {
			if (date == null && project.equals("default")) { // for tasks without deadline
				defaultProject.add(new Task(instruction, null, null));
				System.out.println("Task has been added!");
				
			} else if (date == null && !project.equals("default")) {//Project included no date.

				// check if the project exists first, if not
				// create a new file for the new project.

				if (projectNames.contains(project)) { // does not deal with
														// upper or lower cases
														// yet.
					int count = 1; // start from 1, 0 is the default project
					EpiphanyMain.get(count).add(
							new Task(instruction, null, project));

				} else if (!projectNames.contains(project)) { // create a new
																// file for the
																// project
					projectNames.add(project);
					ArrayList<Task> latest = new ArrayList<Task>();
					latest.add(new Task(instruction, null, project)); // add new
																		// task
																		// inside
																		// this
																		// new
																		// project
																		// called
																		// latest
					// Project newProject = new Project(project, latest);
					EpiphanyMain.add(latest);
					// Create a new file to store the new data
					try {
						createNewFile(project, latest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Task has been added!");

			} else if (date != null && project.equals("default")) { //Have date, no project

				// add tasks with deadline into the default
				// and sort according to priority12/12/2019
				defaultProject.add(new Task(instruction, date, null));
				sortDateInProject(defaultProject);
				// defaultProject.sort()
				System.out.println("Task has been added!");
			}
		}
	}

	public void deleteTask(String instruction, String projectName) {
		for (String s : projectNames) {
			int count = 0;
			if (s.equals("default")) {
				for (int i = 0; i < EpiphanyMain.get(count).size(); i++) {
					if (EpiphanyMain.get(count).get(i).getInstruction().equals(instruction)) {
						EpiphanyMain.get(count).remove(i);
						System.out.println("Removed: " + instruction
								+ " successfully");

					}
				}
			} else {
				count++;
			}
		}
	}

	public void deleteTask(String instruction) {
		for (int i = 0; i < EpiphanyMain.size(); i++) {
			for (int j = 0; j < EpiphanyMain.get(i).size(); j++) {
			if (EpiphanyMain.get(i).get(j).getInstruction().equals(instruction)) {
				EpiphanyMain.get(i).remove(j);
				System.out.println("Removed: " + instruction
							+ " successfully");
				}
				else {
					continue;
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
			addTask(addUserCommand.getDescription(), addUserCommand.getDate(), addUserCommand.getProjectName());
			break;
		case DISPLAY: // displays the entire arraylist
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			display(displayUserCommand.getModifiers());
			break;
		case DELETE:
			DeleteCommandType deleteUserCommand = (DeleteCommandType) userCommand;
			deleteTask(deleteUserCommand.getTaskDescription(), deleteUserCommand.getProjectName());
			break;
		case SEARCH:
			SearchCommandType searchUserCommand = (SearchCommandType) userCommand;
			search(searchUserCommand.getTaskDescription(),searchUserCommand.getProjectName());
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

	public class Task {
		private String instruction;
		private Date deadLine;
		private String date;
		private String ProjectName;
		private boolean isCompleted;

		// Constructor for Task
		public Task(String i, Date date, String ProjectName) {
			this.instruction = i;
			this.deadLine = date;

			this.ProjectName = ProjectName;
			this.isCompleted = false; // Set as false as a default
		}

		// Accessors
		String getInstruction() {
			return this.instruction;
		}

		Date getDeadline() {
			return this.deadLine;
		}

		public String getProjectName() {
			return this.ProjectName;
		}

		public boolean isStatus() {
			return isCompleted;
		}

		// Mutators
		void setInstruction(String newInstruction) {
			this.instruction = newInstruction;
		}

		void setDeadLine(Date newDate) {
			this.deadLine = newDate;
		}

		void setProjectName(String newProjectName) {
			this.ProjectName = newProjectName;
		}

		void isCompletedUpdate(boolean value) {
			this.isCompleted = value;
		}
		
		@Override
		public String toString() {
			return this.getInstruction() + "has the deadline: " + this.getDeadline();
		}
	}

	// Should this be a class or should I simply convert this into a method?
	class Project {
		private String projectName;
		private ArrayList<Task> items;

		// Constructor
		public Project(String name, ArrayList<Task> items) {
			this.setProjectName(name);
			// this.items = items;
			try {
				createNewFile(projectName, items);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String getProjectName() {
			return projectName;
		}

		public ArrayList<Task> getTaskList() {
			return items;
		}

		// This function would allow us to change the name of a project
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
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
