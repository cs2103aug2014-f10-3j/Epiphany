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
		private static String[] days;

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
			
			months = new String[12];
			populateMonths();
			
			days = new String[7];
			populateDays();
			
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
		
/**********************String Methods******************************/	

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
		
		public String printTaskForDisplay(){
			String s = null;
			
			if(this.getProjectName().equals("default")){
				if(this.hasInterval()){
					s =  this.getTaskDescription() + " from " + this.getStartDate().toString() + " to " + this.getDeadline().toString();
				}else if(this.isFloating()){
					s =  this.getTaskDescription();
				}else if(this.hasDeadLine()){
					s = this.getTaskDescription();
				}
			}else{
				if(this.hasInterval()){
					s =  this.getTaskDescription() + " from " + this.getStartDate().toString() + " to " + this.getDeadline().toString() + "\t\t #" + this.getProjectName();
				}else if(this.isFloating()){
					s =  this.getTaskDescription() + "\t\t #" + this.getProjectName();
				}else if(this.hasDeadLine()){
					s = this.getTaskDescription() + "\t\t #" + this.getProjectName();
				}
			}
			return s;
		}
		
		
		/* Unused for now, keep code.
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
			String output = "null";
			if(this.deadLine != null){
				Date dLine = this.deadLine;
				output = dLine.getDate() + " " + convertToMonth(dLine.getMonth()) + (dLine.getYear() + 1900);
			}
			return output;
		}
		
		// DD MMM YYYY, 
		public String startDateToString() {
			String output = "null";
			if(this.from != null){
				Date startDate = this.from;
				output = startDate.getDate() + " " + convertToMonth(startDate.getMonth()) + (startDate.getYear() + 1900);
			}
			return output;
		}
		
		public String getDeadLineDay(){
			return convertToDay(this.deadLine.getDay());
		}
		
		/**********************Helper Methods****************************/

		private void populateMonths() {
			months[0] = "Jan";
			months[1] = "Feb";
			months[2] = "Mar";
			months[3] = "Apr";
			months[4] = "May";
			months[5] = "Jun";
			months[6] = "Jul";
			months[7] = "Aug";
			months[8] = "Sep";
			months[9] = "Oct";
			months[10] = "Nov";
			months[11] = "Dec";
		}
		
		private static String convertToMonth(int input) {
			return months[input];
		}
		
		private void populateDays(){
			days[0] = "Sunday";
			days[1] = "Monday";
			days[2] = "Tuesday";
			days[3] = "Wednesday";
			days[4] = "Thursday";
			days[5] = "Friday";
			days[6] = "Saturday";
		}
		
		private static String convertToDay(int input){
			return days[input];
		}
		
	}