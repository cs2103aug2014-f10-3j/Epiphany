/* Epiphany Engine v0.1 alpha release
 * Project & Class under a separate class file
 * 
 * This class contains all the relevant methods needed by the Task class
 * 
 * @author Moazzam & Wei Yang
 */

public class Task {

	private String instruction;
	private String deadLine;
	private String ProjectName;
	private boolean isCompleted;

	//Constructor for Task
	public Task(String i, String date, String ProjectName) {
		this.instruction = i;
		this.deadLine = date;
		this.ProjectName = ProjectName;
		this.isCompleted = false; //Set as false as a default
	}

	// Accessors
	String getInstruction() {
		return this.instruction;
	}

	String getDeadline() {
		return this.deadLine;
	}
	
	public String getProjectName() {
		return this.ProjectName;
	}
	
	public boolean status() {
		return isCompleted;
	}

	// Mutators
	void setInstruction(String newInstruction) {
		this.instruction = newInstruction;
	}

	void setDeadLine(String newDeadline) {
		this.deadLine = newDeadline;
	}
	
	void setProjectName(String newProjectName) {
		this.ProjectName = newProjectName;
	}
	
	void setIsCompleted(boolean value) {
		this.isCompleted = value;
	}
}
