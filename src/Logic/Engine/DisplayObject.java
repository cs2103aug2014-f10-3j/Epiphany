package Logic.Engine;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to instantiate objects that assist in the display operation.
 * 
 */
//@author A0110924R
public class DisplayObject {
	
		
	/*************Attributes*************/	
		private Date date;
		private ArrayList<Task> list;
		private static String[] months = new String[12];
		private static String[] days = new String[7];

		
		
	/*************Constructors*************/
		public DisplayObject(Date date, ArrayList<Task> list){
			this.date = date;
			populateDays();
			populateMonths();
			this.list = list;
		}
		
		public DisplayObject(Date date, Task task){
			this.date = date;
			populateDays();
			populateMonths();
			list.add(task);
		}
		
		public DisplayObject(Date date){
			list = new ArrayList<Task>();
			this.date = date;
			populateDays();
			populateMonths();
		}
		
		public void addTaskToList(Task t){
			assert(list != null);
			this.list.add(t);
		}
		
		
	/*************Getters*************/
		public Date getDate(){
			return this.date;
		}
		
		public ArrayList<Task> getList(){
			return this.list;
		}
		
		
	/*************String Methods*************/
		@SuppressWarnings("deprecation")
		public String dateToString(){
			String output = "null";
			
			if(date != null){
				output = convertToDay(date.getDay()) + ", " + date.getDate() + " "+ convertToMonth(date.getMonth()) + " " + convertToYear(date.getYear());
			}
			
			return output;
		}
		
	
	/*************Other Methods*************/
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
		
		private void populateDays(){
			days[0] = "Sunday";
			days[1] = "Monday";
			days[2] = "Tuesday";
			days[3] = "Wednesday";
			days[4] = "Thursday";
			days[5] = "Friday";
			days[6] = "Saturday";
		}
		
		private static String convertToMonth(int input) {
			return months[input];
		}
		
		private static String convertToDay(int input){
			return days[input];
		}
		
		private static int convertToYear(int input){
			return input + 1900;
		}
	
		
	}
