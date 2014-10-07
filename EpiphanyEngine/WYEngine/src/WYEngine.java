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

public class WYEngine {
	// EpiphanyMain contains all the current projects which is stored in an
	// array List
	public static ArrayList<String> projectNames;
	public static ArrayList<Task> defaultProject;
	public static ArrayList<ArrayList<Task>> EpiphanyMain;
	public static ArrayList<Date> testDate1;

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
	private Scanner sc;
	private Scanner sc2;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		EpiphanyMain = new ArrayList<ArrayList<Task>>();
		projectNames = new ArrayList<String>();
		defaultProject = new ArrayList<Task>();
	    testDate1 = new ArrayList<Date>();
		
		// testing out date class	
		WYEngine L1 = new WYEngine();
		L1.testSort();
		for (Date s: testDate1) {
			System.out.println(s);
		}
		
		EpiphanyMain.add(defaultProject);
		L1.addTask(null, null, null);
		L1.addTask("testing time", null , null);
		L1.addTask("testing another time", new Date(31,10,2014), null);
		L1.addTask("testing third time", new Date(31,4,2014), null);
		L1.addTask("Why am I so pretty", null, null);
		
		
		L1.addTask("we are still working", null, "CS");
		L1.addTask("we are really still working", null, "CS");
		L1.addTask("Hello there", null, "We rock");
		L1.addTask("software engineering much wow", null, "2103");


		System.out.println();
		L1.displayAll(EpiphanyMain);

		System.out.println("List of projects: ");
		L1.displayProjects();
		// L1.test();
		// L1.displayAL(EpiphanyMain.get(0));

		System.out.println();
		L1.search("are");
		
		
		
		//L1.deleteProjectFromMain();
		//L1.displayProjects();

		/*
			System.out.println("List of projects: ");
			for (String s : projectNames) {
				System.out.println(s);
			}
		 */

		// System.out.println();
		// L1.displayProject("CS");

		// L1.addTask("we ", null, null);
		// L1.run();
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

	/*
		public void test() {
			ArrayList<ArrayList<Task>> testing = new ArrayList<ArrayList<Task>>();

			ArrayList<Task> testProject = new ArrayList<Task>();
			ArrayList<Task> cs2103 = new ArrayList<Task>();

			Task hello = new Task("hello", null, null);
			Task hello1 = new Task("hello there", null, null);
			Task hello2 = new Task("complete PS4", null, "CS2010");
			Task hello3 = new Task("Celebrate mum's birthday, complete birthday card ", null , "CS2103");

			Task one = new Task("The little fox", null, "CS2103");
			Task two = new Task("Is capable of", null, "CS2103");
			Task three = new Task("flying his fox", null, "CS2103");


			addTask("hello", null, null);
			addTask("hello there", null, null);
			addTask("complete PS4", null, "CS2010");
			addTask("Celebrate mum's birthday, complete birthday card ", null , "CS2103");

			addTask("The little fox", null, "CS2103");
			addTask("Is capable of", null, "CS2103");
			addTask("flying his fox", null, "CS2103");

			testing.add(testProject);
			testing.add(cs2103);

			displayAll(testing);
			System.out.println();

			displayProjects();
			//System.out.print(hello3.ProjectName);

			System.out.println();
			EpiphanyMain = testing;
			//search("complete");
			System.out.println();
			displayTaskToDelete("complete", search("complete"));
			//deleteProject(null, "testProject");

			System.out.println();
			deleteProject(null, "CS2103");
		}
	 */

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
		// updated the String method to ignore cases:
		// String phrase = new String(targetPhrase).toLowerCase();
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
			return searchResult; // empty
		}
		System.out.println("Search result:");
		displayAL(searchResult); //returns an arraylist of tasks
		return searchResult;
	}

	// Displays all projects
	public void displayAll(ArrayList<ArrayList<Task>> Epiphany) {
		if (Epiphany.isEmpty()) {
			System.out.println("Nothing to display.");
		} else {

			for (int i = 0; i < Epiphany.size(); i++) {// CHNAGED I FORM 1 TO 0
				Task name = Epiphany.get(i).get(0);
				if (i == 0) {
					System.out.println("Project: Default");
				} else {
					System.out
					.println("Project: " + i + ". " + name.ProjectName);
				}
				int counter = 1;
				for (int j = 0; j < Epiphany.get(i).size(); j++) {
					Task s = Epiphany.get(i).get(j);
					System.out.println(String.format(MESSAGE_DISPLAY, counter,
							s.instruction));
					counter++;
				}
			}
		}
	}

	// Displays a specific project
	public ArrayList<Task> displayAL(ArrayList<Task> expected) {

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

	// doesnt work as intended
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
				displayAL(temporaryProject);
			} else {  
				System.out.println("No such project exists.");
			}
		}
	}

	/**
	 * Displays the names and indices of all the projects that exist
	 * without showing the default project
	 */
	public void displayProjects() {
		int count = 1;
		if (projectNames.isEmpty()) {
			System.out.println("Projects folder is empty");
		} else {
			for (String s : projectNames) {
				System.out.println(count + ". " + s + ".");
				count ++;
			}
		}
	}

	// unnecessary method
	/*
		public void addTask(String input) {
			ArrayList<Task> hello = EpiphanyMain.get(0);
			hello.add(new Task(input));
		}
	 */

	/*
		public void addTask(String input, Date date, String project) {
			if (!project.equals(null)) {
				// Finds the project name and appends the task in
				// THis would terminate right?
				for (String s : projectNames) {
					int count = 1;
					if (s.equals(project)) {
						EpiphanyMain.get(count).add(new Task(input, null, project));
					} else if (!s.equals(project)) {
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
				if (date == null) {
				}
				// I have not yet incorporated the add with date.
			} else {
				// add to default project
				for (int i = 0; i < projectNames.size(); i++) {
					if (projectNames.get(i).equals(project)) {
						EpiphanyMain.get(i).add(new Task(input, date, project));
					}
				}
			}
		}
	 */

	public void addTask(String instruction, Date date, String project) {
		if (instruction == null) {
			System.out.println("Please give a task name"); // convert this to static final
		} else {
			if (date == null && project == null) { // for tasks without deadline
				defaultProject.add(new Task(instruction, null, null));
			} else if (date == null && project != null) {

				// check if the project exists first, if not
				// create a new file for the new project.

				if (projectNames.contains(project)) { //does not deal with upper or lower cases yet.
					int count = 1; // start from 1, 0 is the default project
					EpiphanyMain.get(count).add(new Task(instruction, null, project));

				} else if (!projectNames.contains(project)){ // create a new file for the project
					projectNames.add(project); //update the projectNames arrayList
					ArrayList<Task> latest = new ArrayList<Task>();
					latest.add(new Task(instruction, null, project)); //add new task inside this new project called latest
					//Project newProject = new Project(project, latest);
					EpiphanyMain.add(latest);
					// Create a new file to store the new data
					try {
						createNewFile(project, latest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} else if (date != null && project == null) {

				// add tasks with deadline into the default
				// and sort according to priority
				defaultProject.add(new Task(instruction, date, null));
				// defaultProject.sort()
			}
		}	
	}

	//cant delete default project. make this a failsafe.
	/*
		public void deleteProject(Integer input, String projectName) {
			displayProjects();
			sc = new Scanner(System.in);
			if (sc.hasNext()) {
				int value = sc.nextInt();
				projectNames.remove(value - 1);
			} else {
				if (projectNames.contains(projectName)) {
					projectNames.remove(projectName);
				}
			}
		}
	 */
	
	public void deleteTaskWithinProject(String phrase, ArrayList<Task> project) {
		if (project.contains(phrase)) {
			int count = project.lastIndexOf(phrase);
			project.remove(count);
		}
	}
	
	public void deleteProjectFromMain(ArrayList<Task> projectName) {
		if (EpiphanyMain.contains(projectName) && projectNames.contains(projectName)) {
			int count = EpiphanyMain.lastIndexOf(projectName);
			int count1 = projectNames.lastIndexOf(projectName);
			EpiphanyMain.remove(count);
			projectNames.remove(count1);
		} 
	}

	public void displayTaskToDelete(String phrase, ArrayList<Task> temp) {
		for (int i = 0; i<temp.size(); i++) {
			System.out.println(i + 1 + ". " + temp.get(i).getInstruction() + " " + temp.get(i).getProjectName());
		}
	}


	//sorts by deadline. meaning the phrase with the earliest date will show first.
	//date format is quite crude for now, can be polymorphised later.
	// for now date is sorted according to DDMMYYYY 
	// will specify to include time as well. TTTT to settle the cases of tasks on the same day.
	
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
	

	public class Task {
		private String instruction;
		private Date deadLine;
		private String date;
		private String ProjectName;
		private boolean isCompleted;


		//Constructor for Task
		public Task(String i, Date date, String ProjectName) {
			this.instruction = i;
			this.deadLine = date;
			
			this.ProjectName = ProjectName;
			this.isCompleted = false; //Set as false as a default
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