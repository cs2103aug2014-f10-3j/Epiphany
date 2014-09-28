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
		this.isCompleted = false;
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
