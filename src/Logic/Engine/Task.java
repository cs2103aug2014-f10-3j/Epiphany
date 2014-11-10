package Logic.Engine;

import java.util.Date;
import java.util.HashMap;

/**
 * This class helps in the creation and management of tasks. Each task contains
 * a task description, a deadline of class Date, a projectName and a boolean
 * variable to check if the task has been completed.
 * 
 * @author A0119264E
 */
public class Task {
	
	/***************Attributes**********************/
	private String taskDescription;
	private String duplicateTaskDescription; // backup of taskDescription, cannot be mutated							
	private Date from;
	private Date deadLine;
	private String projectName;
	private boolean isCompleted;
	private String completionStatus;
	private static String[] months;
	private static String[] days;
	private boolean parity; // to help undo/redo tracking
	private static HashMap<String, String> colors;
	private String color;
	
	/***************Constructors**********************/
	
	/**
	 * Overloaded constructors for the creation of tasks are shown below. They
	 * differ in the type of arguments that they receive.
	 * 
	 * @param taskDescription
	 * @param date
	 *            stores the deadline
	 * @param ProjectName
	 *            stores the name of the project that the task belongs to
	 */
	public Task(String instruction, Date from, Date deadLine, String ProjectName) {
		populateColors();
		this.taskDescription = instruction;
		this.from = from;
		this.deadLine = deadLine;
		this.projectName = ProjectName;
		this.duplicateTaskDescription = taskDescription;
		this.completionStatus = "";
		this.parity = false;
		this.color = colors.get("reset");

		months = new String[12];
		populateMonths();

		days = new String[7];
		populateDays();

	}

	public Task(String instruction, Date from, Date deadLine, String ProjectName, boolean isCompleted) {
		populateColors();

		this.taskDescription = instruction;
		this.from = from;
		this.deadLine = deadLine;
		this.projectName = ProjectName;
		this.duplicateTaskDescription = taskDescription;
		this.isCompleted = isCompleted;
		this.parity = false;
		this.color = colors.get("reset");


		if (this.isCompleted) {
			this.completionStatus = " [DONE]";
			this.color = colors.get("green");
			
		} else {
			this.completionStatus = "";
			this.color = colors.get("reset");
		}

		months = new String[12];
		populateMonths();

		days = new String[7];
		populateDays();
	}

	public Task() {

	}

	/********************** Getters ******************************/
	public String getTaskDescription() {
		return this.taskDescription;
	}

	public String getDuplicateTaskDescription() {
		return this.duplicateTaskDescription;
	}

	public boolean getParity() {
		return this.parity;
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
	 * 
	 * @return
	 */
	public boolean hasInterval() {
		if (this.getStartDate() != null && this.getDeadline() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasDeadLine() {
		return (this.deadLine == null) ? false : true;
	}

	public boolean isFloating() {
		return (this.from == null && this.deadLine == null);
	}

	/********************** Mutators ******************************/

	public void setInstruction(String newInstruction) {
		this.taskDescription = newInstruction;
	}

	public void setDeadLine(Date newDate) {
		this.deadLine = newDate;
	}

	public void setProjectName(String newProjectName) {
		this.projectName = newProjectName;
	}

	public void flipParity() {
		this.parity = !this.parity;
	}

	public boolean isFinished() {
		this.isCompleted = true;
		return this.isCompleted;
	}
	
	/**
	 * Marks/Unmarks a task as completed.
	 * @author A0118794R
	 */
	public void setStatus() { 
		this.isCompleted = !this.isCompleted;
		if (this.isCompleted) {
			this.completionStatus = " [DONE]";
			this.color = colors.get("green");
		} else {
			this.completionStatus = "";
			this.color = colors.get("reset");
		}
	}
	
	/**
	 * Mark this task as overdue
	 */
	public void setOverdue(){
		this.color = colors.get("red");
	}

	public String getType() {
		if (!this.hasDeadLine()) {
			return "floating";
		} else if (this.hasInterval()) {
			return "interval";
		} else {
			return "deadline";
		}
	}

	/********************** String Methods ******************************/

	@Override
	public String toString() {
		String s = null;

		if (this.from == null && this.deadLine != null) {
			s = this.getType() + "~" + this.getTaskDescription() + "~" + "null" + "~" + this.getDeadline().toString() + "~" + this.getProjectName() + "~" + this.isCompleted();
		} else if (this.from == null && this.deadLine == null) {
			s = this.getType() + "~" + this.getTaskDescription() + "~" + "null" + "~" + "null" + "~" + this.getProjectName() + "~" + this.isCompleted();
		} else if (this.from != null && this.deadLine != null) {
			s = this.getType() + "~" + this.getTaskDescription() + "~" + this.getStartDate().toString() + "~" + this.getDeadline().toString() + "~" + this.getProjectName() + "~" + this.isCompleted();
		}
		return s;
	}

	@SuppressWarnings("deprecation")
	public String printTaskForDisplay(String command) {
		String s = null;

		if (this.getProjectName().equals("default")) {
			if (this.hasInterval()) {
				if (isSingleDayTask(this.getStartDate(), this.deadLine)) {
					// print time diff
					if (command.equals("search")) {
						s = this.getTaskDescription() + " from "
								+ formatToTime(this.getStartDate()) + " to "
								+ formatToTime(this.getDeadline()) + "\t"
								+ this.deadLineToString()
								+ this.completionStatus;

					} else if (command.equals("display")) {
						s = this.getTaskDescription() + " from "
								+ formatToTime(this.getStartDate()) + " to "
								+ formatToTime(this.getDeadline())
								+ this.completionStatus;
					}
				} else {
					s = this.getTaskDescription() + " from "
							+ this.getStartDate().getDate() + " "
							+ formatToMonth(this.getStartDate().getMonth())
							+ " to " + this.getDeadline().getDate() + " "
							+ formatToMonth(this.getDeadline().getMonth())
							+ this.completionStatus;
				}
			} else if (this.isFloating()) {
				s = this.getTaskDescription() + this.completionStatus;
			} else if (this.hasDeadLine()) {
				if (command.equals("search")) {
					s = this.getTaskDescription() + " by "
							+ formatToTime(this.getDeadline()) + "\t"
							+ this.deadLineToString()
							+ this.completionStatus;

				} else if (command.equals("display")) {
					s = this.getTaskDescription() + " by "
							+ formatToTime(this.getDeadline()) + "\t"
							+ this.completionStatus;
				}
			}
		} else {
			if (this.hasInterval()) {
				if (isSingleDayTask(this.getStartDate(), this.deadLine)) {
					// print time diff
					if (command.equals("search")) {
						s = this.getTaskDescription() + " from "
								+ formatToTime(this.getStartDate()) + " to "
								+ formatToTime(this.getDeadline()) + "\t"
								+ this.deadLineToString()
								+ this.completionStatus;
						s = addSpace(s);
						s += "\t\t #" + this.getProjectName();
					}// lAST CHANGED
					else if (command.equals("display")) {

						s = this.getTaskDescription() + " from "
								+ formatToTime(this.getStartDate()) + " to "
								+ formatToTime(this.getDeadline())
								+ this.completionStatus;
						s = addSpace(s);
						s += "\t\t #" + this.getProjectName();
					}
				} else {
					s = this.getTaskDescription() + " from "
							+ this.getStartDate().getDate() + " "
							+ formatToMonth(this.getStartDate().getMonth())
							+ " to " + this.getDeadline().getDate() + " "
							+ formatToMonth(this.getDeadline().getMonth())
							+ this.completionStatus;
					s = addSpace(s);
					s += "\t\t #" + this.getProjectName();
				}
			} else if (this.isFloating()) {
				s = this.getTaskDescription() + this.completionStatus;
				s = addSpace(s);
				s += "\t\t #" + this.getProjectName();
			} else if (this.hasDeadLine()) {
				if (command.equals("search")) {
					s = this.getTaskDescription() + " by "
							+ formatToTime(this.getDeadline()) + "\t"
							+ this.deadLineToString()
							+ this.completionStatus;
					s = addSpace(s);
					s += "\t\t #" + this.getProjectName();
				} else if (command.equals("display")) {

					s = this.getTaskDescription() + " by "
							+ formatToTime(this.getDeadline()) + "\t"
							+ this.completionStatus;
					
					s = addSpace(s);
					s += "\t\t #" + this.getProjectName();
				}
			}
		}
		return this.color + s;
	}


	// DD MMM YYYY,
	@SuppressWarnings("deprecation")
	public String deadLineToString() {
		String output = "null";
		if (this.deadLine != null) {
			Date dLine = this.deadLine;
			output = dLine.getDate() + " " + formatToMonth(dLine.getMonth())
					+ " " + (dLine.getYear() + 1900);
		} else {
			output = "";
		}
		return output;
	}

	// DD MMM YYYY,
	@SuppressWarnings("deprecation")
	public String startDateToString() {
		String output = "null";
		if (this.from != null) {
			Date startDate = this.from;
			output = startDate.getDate() + " "
					+ formatToMonth(startDate.getMonth()) + " "
					+ (startDate.getYear() + 1900);
		} else {
			output = "";
		}
		return output;
	}

	@SuppressWarnings("deprecation")
	public String getDeadLineDay() {
		return formatToDay(this.deadLine.getDay());
	}

	/********************** Helper Methods ****************************/

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

	private static String formatToMonth(int input) {
		return months[input];
	}

	private void populateDays() {
		days[0] = "Sunday";
		days[1] = "Monday";
		days[2] = "Tuesday";
		days[3] = "Wednesday";
		days[4] = "Thursday";
		days[5] = "Friday";
		days[6] = "Saturday";
	}

	private static String formatToDay(int input) {
		return days[input];
	}

	@SuppressWarnings("deprecation")
	private static String formatToTime(Date d) {
		String output = "";
		int hour = d.getHours();
		int minutes = d.getMinutes();

		if (hour < 12) {
			// am
			if (hour == 0) {
				output = "midnight";
			} else if(minutes == 0) {
				output = hour + "am";
			}else {
				output = hour + ":" + minutes + "am";
			}

		} else if (hour > 12) {
			// pm
			if (minutes == 0) {
				output = (hour % 12) + "pm";
			} else {
				output = (hour % 12) + ":" + minutes + "pm";
			}
		} else {
			if (minutes == 0) {
				output = "noon";
			} else {
				output = 12 + ":" + minutes + "pm";
			}
		}

		return output;

	}

	@SuppressWarnings("deprecation")
	private static boolean isSingleDayTask(Date d1, Date d2) {
		return (d1.getDate() == d2.getDate() && d1.getMonth() == d2.getMonth() && d1
				.getYear() == d2.getYear());
	}

	/**
	 * Method to maitain consistent spacing for project names during display
	 * @param s
	 * @return
	 */
	private static String addSpace(String s) {
		int numOfSpaces = 40 - s.length();

		for (int i = 0; i < numOfSpaces; i++) {
			s += " ";
		}

		return s;
	}
	
	/**
	 * A Dictionary of colors.
	 * Keys being the color names and the values their respective ANSI color codes
	 */
	private static void populateColors(){
		colors = new HashMap<String, String>();
		colors.put("green", "\033[32m");
		colors.put("red", "\033[31m");
		colors.put("black", "\033[30m");
		colors.put("white", "\033[37m");
		colors.put("reset", "\033[0m");
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Task) {
			Task t = (Task) o;
			if (!t.getTaskDescription().equals(this.getTaskDescription())) {
				return false;
			}

			if (this.getDeadline() != null && t.getDeadline() != null) {
				return this.getDeadline().equals(t.getDeadline());
			}
			if ((this.getDeadline() == null && t.getDeadline() != null)) {
				return false;
			}
			if ((this.getDeadline() != null && t.getDeadline() == null)) {
				return false;
			}

			if (this.getStartDate() != null && t.getStartDate() != null) {
				return this.getStartDate().equals(t.getStartDate());
			}
			if ((this.getStartDate() == null && t.getStartDate() != null)) {
				return false;
			}
			if ((this.getStartDate() != null && t.getStartDate() == null)) {
				return false;
			}

			if (!t.getType().equals(this.getType())) {
				return false;
			}
			if (!t.getProjectName().equals(this.getProjectName())) {
				return false;
			}
			return true;
		}
		return false;
	}
}
