package Logic.Engine;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is used to instantiate objects that need would then be displayed
 * @author Moazzam
 *
 */
public class DisplayObject {
	
		
	/*************Attributes*************/	
		private Date date;
		private ArrayList<Task> list;
		
		
	/*************Constructors*************/
		public DisplayObject(Date date, ArrayList<Task> list){
			this.date = date;
			this.list = list;
		}
		
		public DisplayObject(Date date, Task task){
			this.date = date;
			list.add(task);
		}
		
		public DisplayObject(Date date){
			list = new ArrayList<Task>();
			this.date = date;
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
	}
