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
	
	public boolean isStatus() {
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
	
	void isCompletedUpdate(boolean value) {
		this.isCompleted = value;
	}
}