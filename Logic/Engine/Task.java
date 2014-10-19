package Logic.Engine;


import java.util.*;

/**
 * This class helps in the creation and management of tasks. 
 * Each task contains a task description, a deadline of class Date, a projectName and
 * a boolean variable to check if the task has been completed.
 * 
 * @author Moazzam  and Wei Yang
 * edited by @author amit
 */
public class Task {
		private String taskDescription;
		private Date deadLine;
		private String projectName;
		private boolean isCompleted;

		/**
		 * Overloaded constructors for the creation of tasks are shown below. They differ in the 
		 * type of arguments that they receive.
		 * @param taskDescription 
		 * @param date			stores the deadline
		 * @param ProjectName	stores the name of the project that the task belongs to
		 */
		public Task(String instruction, Date date, String ProjectName) {
			this.taskDescription = instruction;
			this.deadLine = date;
			this.projectName = ProjectName;
			this.isCompleted = false; // Set as false as a default
		}

/**********************Getters******************************/		
		public String getTaskDescription() {
			return this.taskDescription;
		}

		public Date getDeadline() {
			return this.deadLine;
		}

		public String getProjectName() {
			return this.projectName;
		}

		public boolean isCompleted() {
			return isCompleted;
		}

/**********************Mutators******************************/	
		
		public void setInstruction(String newInstruction) {
			this.taskDescription = newInstruction;
		}

		public void setDeadLine(Date newDate) {
			this.deadLine = newDate;
		}

		public void setProjectName(String newProjectName) {
			this.projectName = newProjectName;
		}

		public boolean isFinished() {
			this.isCompleted = true;
			return this.isCompleted;
		}
		
/**********************Other Methods******************************/	

		@Override
		public String toString() {
			return this.getTaskDescription() + "has the deadline: " + this.getDeadline();
		}

		
	}