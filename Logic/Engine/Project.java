package Logic.Engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * This class is used to create and modify projects that are created by the Engine. 
 * A project contains a project name and an list of Tasks. 
 * @author Moazzam and Wei Yang
 * edited by @author amit
 */
public class Project {
		private String projectName;
		private ArrayList<Task> deadLineList;
		private ArrayList<Task> intervalList;
		private ArrayList<Task> floatingList;

/**********************Constructors ***********************************/
		/**
		 * @param name
		 * @param list
		 * @throws IOException
		 */
		public Project(String name, ArrayList<Task> list) throws IOException {
			this.setProjectName(name);
			deadLineList = new ArrayList<Task>();
			intervalList = new ArrayList<Task>();
			floatingList = new ArrayList<Task>();
			createNewFile(projectName, deadLineList, intervalList, floatingList); //creates a new text file with relevant info. 
		}

/**********************Getters***********************************/		
		
		public String getProjectName() {
			return projectName;
		}

		public ArrayList<Task> getDeadlineList() {
			return this.deadLineList;
		}
		
		public ArrayList<Task> getIntervalList() {
			return this.intervalList;
		}
		
		public ArrayList<Task> getFloatingList() {
			return this.floatingList;
		}
		

/**********************Mutators***********************************/		

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		
		/**
		 * Tasks are to be added according to the following priority
		 * 1) Tasks with deadline, with most recent on top
		 * 2) Tasks that have an interval, sorted by earliest start date first.
		 * 3) Tasks with no deadline, sorted alphabetically.
		 * @param t
		 * @throws IOException 
		 */
		public void addTask(Task t) throws IOException{
			assert(t.hasTask()); // ensures that we have a valid task.
			
			if(t.hasInterval()){
				// Add to the second section
				intervalList.add(t);
				Collections.sort(intervalList, new intervalComparator());

			}else if(t.hasDeadLine()){
				// Add to the first section
				deadLineList.add(t);
				Collections.sort(deadLineList, new deadlineComparator());
			}else{
				//Is a floating task. Add to last section.
				floatingList.add(t);
				Collections.sort(floatingList, new floatingComparator());
			}
			
			// Task added. Repopulate txt file.
			writeToFile(this.projectName, deadLineList, intervalList, floatingList);
			
		} 
		
/**********************Other methods***********************************/		

		/**
		 * Creates a new text file to store the new project file
		 * 
		 * @param fileName
		 *            is the name of the file/project
		 * @param items
		 *            is the ArrayList of items that is inside this project
		 * @throws IOException
		 */
		public void createNewFile(String fileName, ArrayList<Task> dLineList, ArrayList<Task> interList, ArrayList<Task> floatList ) throws IOException {
			writeToFile(fileName, dLineList, interList, floatList);
		}

		/**
		 * Does the actual writing to .txt file.
		 * @param dLineList
		 * @param interList
		 * @param floatList
		 * @throws IOException
		 */
		private void writeToFile(String fileName, ArrayList<Task> dLineList,
				ArrayList<Task> interList, ArrayList<Task> floatList)
				throws IOException {
			FileWriter f = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(f);
			
				// for deadline
				for(Task t : dLineList){
					writer.write(t.toString());
					writer.newLine();
					writer.flush();
				}
				
				//for interval
				for(Task t : interList){
					writer.write(t.toString());
					writer.newLine();
					writer.flush();
				}
				
				//for floating
				for(Task t : floatList){
					writer.write(t.toString());
					writer.newLine();
					writer.flush();
				}
			
	
			writer.close();
		}
		
		/**
		 * This method helps compare two dates.
		 * Task with earlier deadline is put first.
		 * @author Moazzam
		 *
		 */
		private class deadlineComparator implements Comparator<Task> {
			public int compare(Task one, Task two) {
				return one.getDeadline().compareTo(two.getDeadline());
			}
		}
		
		/**
		 * Puts the task with earlier start date first.
		 * @author amit
		 *
		 */
		private class intervalComparator implements Comparator<Task> {
			public int compare(Task one, Task two) {
				return one.getStartDate().compareTo(two.getStartDate());
			}
		}
		
		/**
		 * Sorts tasks alphabetically(by task description)
		 * @author amit
		 *
		 */
		private class floatingComparator implements Comparator<Task> {
			public int compare(Task one, Task two) {
				return one.getTaskDescription().compareTo(two.getTaskDescription());
			}
		}
}

