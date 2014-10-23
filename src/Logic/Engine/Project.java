package Logic.Engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * This class is used to create and modify projects that are created by the Engine. 
 * A project contains a project name and an list of Tasks. 
 * @author amit
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
			
			if(!list.isEmpty()){
				populateLists(list);
			}
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

		/**
		 * Tasks are to be added according to the following priority
		 * 1) Tasks with deadline, with most recent on top
		 * 2) Tasks that have an interval, sorted by earliest start date first.
		 * 3) Tasks with no deadline, sorted alphabetically.
		 * @param t
		 * @throws IOException 
		 * @author amit
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
		
		/**
		 * This method removes the specified task from this project, regardless of the type of project it is.
		 * @param t
		 * @throws IOException
		 * @author amit
		 */
		public void deleteTask(Task t) throws IOException{
			if(deadLineList.contains(t)){
				deadLineList.remove(t);
			}else if(intervalList.contains(t)){
				intervalList.remove(t);
			}else if(floatingList.contains(t)){
				floatingList.remove(t);
			}
			
			// Task deleted. Repopulate txt file.
			writeToFile(this.projectName, deadLineList, intervalList, floatingList);
		}
		
		/**
		 * This method takes in a string and results a list of any task descriptions that contain(even in part) the search string.
		 * @param input
		 * @return ArrayList<Task>
		 * @author amit
		 */
		public ArrayList<Task> searchForTask(String input){
			ArrayList<Task> results = new ArrayList<Task>();
			
			for(Task t : deadLineList){
				if(t.getTaskDescription().contains(input)){
					results.add(t);
				}
			}
			
			for(Task t : intervalList){
				if(t.getTaskDescription().contains(input)){
					results.add(t);
				}
			}
			
			for(Task t : floatingList){
				if(t.getTaskDescription().contains(input)){
					results.add(t);
				}
			}
			
			return results;
		}
		
		/**
		 * This method edits an existing task by swapping it with a new one.
		 * @param before
		 * @param after
		 * @throws IOException
		 * @author amit
		 */
		public void editTask(Task before, Task after) throws IOException{
			this.deleteTask(before);
			this.addTask(after);
		}
		
		/**
		 * This method returns a list of all the tasks in this project.
		 * The list is sorted by date and thus an impending task will be prioritized.
		 * @return ArrayList<Task>
		 * @author amit
		 */
		public ArrayList<Task> displayAllTasks(){
			ArrayList<Task> results = new ArrayList<Task>();
			
			results.addAll(deadLineList);
			results.addAll(intervalList);
			results.addAll(floatingList);
			
			Collections.sort(results, new dateComparator());
			return results;
		}
		
		/**
		 * This method changes the name of the Project.
		 * @param projectName
		 * @author amit
		 */
		public void setProjectName(String projectName) {
			this.projectName = projectName;
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
		 * @author amit
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
		 * @author amit
		 */
		private void writeToFile(String fileName, ArrayList<Task> dLineList,
				ArrayList<Task> interList, ArrayList<Task> floatList)
				throws IOException {

			File file = new File("../Epiphany/src/Logic/Engine/Projects/" + fileName);
			FileWriter f = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(f);
			
			writer.write("");
			
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
					System.out.print(t.toString());
					writer.write(t.toString());
					writer.newLine();
					writer.flush();
				}
			
	
			writer.close();
		}
		
		
		private void populateLists(ArrayList<Task> list) {
			for(Task t : list){
				String type = t.getType();
				if(type.equals("interval")){
					intervalList.add(t);
				}else if(type.equals("deadline")){
					deadLineList.add(t);
				}else if(type.equals("floating")){
					floatingList.add(t);
				}
			}
		}
		
/**********************Sorting Compartors***********************************/		

		
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
		
		/**
		 * Sorts by impending task(date)
		 * @author amit
		 *
		 */
		private class dateComparator implements Comparator<Task>{
			@Override
			public int compare(Task task1, Task task2) {
				if(task1.getDeadline().before(task2.getStartDate())){
					return 1;
				}else if(task1.getDeadline().equals(task2.getStartDate())){
					return 0;
				}else{
					return -1;
				}
			}
			
		}
}

