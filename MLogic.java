//This is how we roll
/* Epiphany Engine v0.1 alpha release
 * Contains basic functionality to CRUD as well as Storage. 
 * More details about the methods will be added soon
 * 
 * @author Moazzam & Wei Yang
 */

import java.util.*;
import java.io.*;

public class MLogic {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<String> projectNames;
	public static ArrayList<ArrayList<Task>> EpiphanyMain;
	public static final String MESSAGE_WELCOME = " Welcome to TextBuddy++, your file, %s, is ready to use.";
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
		L1.run();
	}

	public void run() {

	}

	/**
	 * Searches through the entire project for the given phrase.
	 * 
	 * @param phrase
	 *            the search phrase provided by the use
	 * @return an ArrayList of tasks that match the given phrase
	 */
	public ArrayList<Task> search(String phrase) {
		ArrayList<Task> searchResult = new ArrayList<Task>(); // Would contain
																// the list of
																// tasks
																// that are in
																// the search
																// result

		for (int i = 0; i < EpiphanyMain.size(); i++) {// Searches the entire
														// list
			for (int j = 0; j < EpiphanyMain.get(i).size(); j++) {// Searches
																	// each
																	// project

				Task line = (EpiphanyMain.get(i)).get(j);
				if (line.instruction.contains(phrase)) {
					searchResult.add(line);
				}
			}
		}
			if (searchResult.isEmpty()) {
				System.out.println(MESSAGE_INVALID_SEARCH);
			}
			display(searchResult);
			return searchResult;
		}
	

	

	// Displays all projects
	public void displayAll(ArrayList<ArrayList<Task>> Epihany) {
		if (Epihany.isEmpty()) {
			System.out.println("Nothing to display.");
		} else {
			System.out.println("Project: Default");
			for (int i = 1; i < Epihany.size(); i++) {
				Task name = Epihany.get(i).get(0);
				System.out.print("Project: " + i + "." + name.ProjectName);
				int counter = 1;
				for (int j = 0; j < Epihany.get(i).size(); j++) {
					Task s = Epihany.get(i).get(j);
					System.out.println(String.format(MESSAGE_DISPLAY, counter,
							s.instruction));
					counter++;
				}
			}
		}
	}

	// Displays a specific project
	public ArrayList<Task> display(ArrayList<Task> expected) {

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
	 * @param name 		The name of the project that we wish to display.
	 */
	public void displayProjects(String name){
		for(int i = 0; i < EpiphanyMain.size(); i++){
			if(EpiphanyMain.get(i).get(0).ProjectName.equals(name)){
				ArrayList<Task> temporaryProject = EpiphanyMain.get(i);
				display(temporaryProject);
			}
			else{
				System.out.println("No such project exists.");
			}
		}
	}
	/**
	 * Displays the names and indices of all the projects that exist
	 */
	public void displayProjects(){
		int count = 1;
		for(String s: projectNames){
			System.out.println(count + "." + s + ".");
		}
	}
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
	

	class Task {

		private String instruction;
		private String deadLine;
		private String ProjectName;
		//private boolean isCompleted;

		public Task(String input) {
			this.instruction = input;
		}
		public Task(String input, String date, String project){
			this.instruction = input;
			this.deadLine = date;
			this.ProjectName = project;
		}

		// Accessors
		String getInstruction() {
			return this.instruction;
		}

		String getDeadline() {
			return this.deadLine;
		}

		// Mutators
		void setInstruction(String newInstruction) {
			this.instruction = newInstruction;
		}

		void setDeadLine(String newDeadline) {
			this.deadLine = newDeadline;
		}
	}

	// Should this be a class or should I simply convert this into a method?
	class Project {
		private String projectName;
		//private ArrayList items;

		// Constructor
		public Project(String name, ArrayList<Task> items) {
			this.setProjectName(name);
			//this.items = items;
			try {
				createNewFile(projectName, items);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String getProjectName() {
			return projectName;
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
			writer.write(counter + ". " + s.instruction);
			counter++;
			writer.newLine();
			writer.flush();
		}
		writer.close();
	}
}
