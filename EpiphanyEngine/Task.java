// Task Class that stores tasks.
import java.util.*;
public class Task {
		private String instruction;
		private Date deadLine;
		private String projectName;
		private boolean isCompleted;

		// Constructor for Task
		public Task(String instruction, Date date, String ProjectName) {
			this.instruction = instruction;
			this.deadLine = date;

			this.projectName = ProjectName;
			this.isCompleted = false; // Set as false as a default
		}
		
		public Task(String instruction){
			this.instruction = instruction;
		}
		public Task(String instruction, Date date){
			this.instruction = instruction;
			this.deadLine = date;
		}
		public Task(String instruction, String projectName){
			this.instruction = instruction;
			this.projectName = projectName;
		}

		// Accessors
		public String getInstruction() {
			return this.instruction;
		}

		Date getDeadline() {
			return this.deadLine;
		}

		public String getProjectName() {
			return this.projectName;
		}

		public boolean isCompleted() {
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
			this.projectName = newProjectName;
		}

		void isCompletedUpdate(boolean value) {
			this.isCompleted = value;
		}
		
		@Override
		public String toString() {
			return this.getInstruction() + "has the deadline: " + this.getDeadline();
		}

		
	}