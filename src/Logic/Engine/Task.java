package Logic.Engine;

import java.util.Date;


/**
 * This class helps in the creation and management of tasks. 
 * Each task contains a task description, a deadline of class Date, a projectName and
 * a boolean variable to check if the task has been completed.
 * 
 * @author amit
 */
public class Task {
		private String taskDescription;
		private Date from;
		private Date deadLine;
		private String projectName;
		private boolean isCompleted;
		
		private static String[] months;

		/**
		 * Overloaded constructors for the creation of tasks are shown below. They differ in the 
		 * type of arguments that they receive.
		 * @param taskDescription 
		 * @param date			stores the deadline
		 * @param ProjectName	stores the name of the project that the task belongs to
		 */
		public Task(String instruction, Date from, Date deadLine, String ProjectName) {
			this.taskDescription = instruction;
			this.from = from;
			this.deadLine = deadLine;
			this.projectName = ProjectName;
			months = new String[13];
			populateMonths();
		}
		
		public Task(){
			
		}
		
		@Override
		public boolean equals(Object o){
			if(o instanceof Task){
				Task t = (Task) o;
				if(!t.getTaskDescription().equals(this.getTaskDescription())){
					return false;
				}
				
				if(this.getDeadline()!= null && t.getDeadline() != null){
					return this.getDeadline().equals(t.getDeadline());
				}
				if((this.getDeadline() == null && t.getDeadline() != null)){ 
					return false;
				}
				if((this.getDeadline() != null && t.getDeadline() == null)){
					return false;
				}


				if(this.getStartDate()!= null && t.getStartDate() != null){
					return this.getStartDate().equals(t.getStartDate());
				}
				if((this.getStartDate() == null && t.getStartDate() != null)){ 
					return false;
				}
				if((this.getStartDate() != null && t.getStartDate() == null)){
					return false;
				}
				
				if(!t.getType().equals(this.getType())){
					return false;
				}
				if(!t.getProjectName().equals(this.getProjectName())){
					return false;
				}
				return true;
				
			}
			return false;
			
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
		
		public boolean isFloating(){
			return (this.from == null && this.deadLine == null);
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
				s = this.getType() + "~" + this.getTaskDescription() + "~" + "null" + "~" + this.getDeadline().toString() + "~" + this.getProjectName() + "~" + this.isCompleted();
			}else if(this.from == null && this.deadLine == null){
				s = this.getType() + "~" + this.getTaskDescription() + "~" + "null" + "~" + "null" + "~" + this.getProjectName() + "~" + this.isCompleted();
			}else if(this.from != null && this.deadLine != null){
				s = this.getType() + "~" + this.getTaskDescription() + "~" + this.getStartDate().toString() + "~" + this.getDeadline().toString() + "~" + this.getProjectName() + "~" + this.isCompleted();
			}
			return s;
		}
		
		/* Not used for now
		public String printTaskForDisplay(){
			String s = null;
			
			if(this.getProjectName().equals("default")){
				if(this.hasInterval()){
					s =  this.getTaskDescription() + " from " + this.getStartDate().toString() + " to " + this.getDeadline().toString();
				}else if(this.isFloating()){
					s =  this.getTaskDescription();
				}else if(this.hasDeadLine()){
					s = this.getTaskDescription() + " by " + this.getDeadline();
				}
			}else{
				if(this.hasInterval()){
					s =  this.getTaskDescription() + " from " + this.getStartDate().toString() + " to " + this.getDeadline().toString() + "\t\t #" + this.getProjectName();
				}else if(this.isFloating()){
					s =  this.getTaskDescription() + "\t\t #" + this.getProjectName();
				}else if(this.hasDeadLine()){
					s = this.getTaskDescription() + " by " + this.getDeadline() +  "\t\t #" + this.getProjectName();
				}
			}
			return s;
		}
		*/
		// DD MMM YYYY, 
		public String deadLineToString() {
			Date dLine = this.deadLine;
			return dLine.getDate() + " " + convertToMonth(dLine.getMonth()) + dLine.getYear();	
		}
		/*
		public String startDateToString() {
			return
		}
		*/

		private void populateMonths() {
			months[1] = "Jan";
			months[2] = "Feb";
			months[3] = "Mar";
			months[4] = "Apr";
			months[5] = "May";
			months[6] = "Jun";
			months[7] = "Jul";
			months[8] = "Aug";
			months[9] = "Sep";
			months[10] = "Oct";
			months[11] = "Nov";
			months[12] = "Dec";
		}
		
		private static String convertToMonth(int input) {
			return months[input];
		}
		
		private static String 
	}