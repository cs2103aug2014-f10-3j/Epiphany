public class Task {

	private String instruction;
	private String deadLine;
	private String ProjectName;
	private boolean isCompleted;

	//Constructor for Task
	public Task() {
		
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

	public String getProjectName() {
		// TODO Auto-generated method stub
		return this.ProjectName;
	}
}
