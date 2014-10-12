// Task Class that stores tasks.

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