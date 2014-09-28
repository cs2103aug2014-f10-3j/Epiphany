import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Project {
	private String projectName;
	private ArrayList<Task> items;

	// Constructor
	public Project(String name) {
		this.setProjectName(name);
		this.items = new ArrayList<Task>();
		try {
			createNewFile(projectName, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProjectName() {
		return this.projectName;
	}
	
	public ArrayList<Task> getTaskList() {
		return this.items;
	}

	// This function would allow us to change the name of a project
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

		for (Task task : items) {
			writer.write(counter + ". " + task.getInstruction());
			counter++;
			writer.newLine();
			writer.flush();
		}
		writer.close();
	}

	// leave to the interpreter to decided if the input has a deadline/ specific project/
	public void addTask(String i, String d, String p) throws Exception {
		if (i != null) {
			System.out.println("Please give a task name"); // convert this to static final
		} else {
			if (d == null && p == null) {
				this.items.add(new Task(i, null, null));
			} else if (d == null && p != null) {
				// decide if keyed in project name already exists. if it does save the task under that file instead
				// if no create a new project and save the new task under the new project file
				this.items.add(new Task(i, null, p));
			} else if (d != null && p == null) {
				// add to default project with deadline
				this.items.add(new Task(i, d, null));
			}
		}
	}
	/* Delete function has 2 approaches
	 * 
	 * 1.
	 * User specifies a word/phrase: 
	 * System searches for the phrases and displays out all the tasks that contains the phrase
	 * System asks the user which index he/she wants to delete
	 * System deletes that index from the arraylist
	 * 
	 * 2.
	 * User specifies the index of the task that he/she wants to delete
	 * System does arraylist.remove(i) to remove that particular task.
	 */

	public void deleteTask(Integer indexToDelete, String phrase) {
		if ((indexToDelete == null) && this.getTaskList().contains(phrase)) {
			// display all tasks in project
			// ask what user wants to delete
			
		} else if (indexToDelete != null) {
			this.getTaskList().remove(indexToDelete);
		}
	}
	
	public void clearAllTasks() {
		this.getTaskList().clear();
	}
}

