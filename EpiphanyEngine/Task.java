package EpiphanyEngine;


import java.util.*;

/**
 * This class aims to help in the creation and management of tasks. Each task contains an instruction,
 * a deadline of class Date, a projectName which is the name of the project that the task is stored
 * under and a boolean variable to check if the task has been completed.
 * @author Moazzam
 *
 */
public class Task {
		private String instruction;
		private Date deadLine;
		private String projectName;
		private boolean isCompleted;

		/**
		 * Overloaded constructors for the creation of tasks are shown below. They differ in the 
		 * type of arguments that they receive.
		 * @param instruction	stores the actual task	
		 * @param date			stores the deadline
		 * @param ProjectName	stores the name of the project that the task belongs to
		 */
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