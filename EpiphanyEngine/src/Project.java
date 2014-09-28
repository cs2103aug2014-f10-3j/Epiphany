import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Project {

	private String projectName;
	private ArrayList items;

	// Constructor
	public Project(String name, ArrayList<Task> items) {
		this.setProjectName(name);
		this.items = items;
		try {
			createNewFile(projectName, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProjectName() {
		return this.projectName;
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
}

