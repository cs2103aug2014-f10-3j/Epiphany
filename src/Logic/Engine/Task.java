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
		private Date from;
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
		public Task(String instruction, Date from, Date deadLine, String ProjectName, boolean status) {
			this.taskDescription = instruction;
			this.from = from;
			this.deadLine = deadLine;
			this.projectName = ProjectName;
			this.isCompleted = status; // Set as false as a default
		}

/**********************Getters******************************/		
		public String getTaskDescription() {
			return this.taskDescription;
		}

		public Date getStartDate() {
			return this.from;
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
		
		public boolean hasTask() {
			return (this.taskDescription == null) ? false : true;
		}
		
		/**
		 * Checks if this task has an interval, i.e a start and end date.
		 * @return
		 */
		public boolean hasInterval(){
			if(this.getStartDate() != null && this.getDeadline() != null){
				return true;
			}else{
				return false;
			}
		}
		
		public boolean hasDeadLine() {
			return (this.deadLine == null) ? false : true;
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
		
		public String getType(){
			if(!this.hasDeadLine()){
				return "floating";
			}else if(this.hasInterval()){
				return "interval";
			}else{
				return "deadline";
			}
		}
		
/**********************Other Methods******************************/	

		@Override
		public String toString() {
			String s = null;
			
			if(this.from == null && this.deadLine != null){
				s = this.getType() + "||" + this.getTaskDescription() + "||" + "null" + "||" + this.getDeadline().toString() + "||" + this.getProjectName() + "||" + this.isCompleted();
			}else if(this.from == null && this.deadLine == null){
				s = this.getType() + "||" + this.getTaskDescription() + "||" + "null" + "||" + "null" + "||" + this.getProjectName() + "||" + this.isCompleted();
			}else if(this.from != null && this.deadLine != null){
				s = this.getType() + "||" + this.getTaskDescription() + "||" + this.getStartDate().toString() + "||" + this.getDeadline().toString() + "||" + this.getProjectName() + "||" + this.isCompleted();
			}
			
			return s;
		}

		
	}