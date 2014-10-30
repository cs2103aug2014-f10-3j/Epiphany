package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.junit.Test;

import Logic.Engine.Project;
import Logic.Engine.Task;

public class ProjectTestCases {
	ArrayList<Task> deadlineListExpected;
	ArrayList<Task> intervalListExpected;
	ArrayList<Task> floatingListExpected;
	Task deadlineTask;
	Task intervalTask;
	Task floatingTask;
	
	@SuppressWarnings("deprecation")
	
	public ProjectTestCases(){
		deadlineListExpected = new ArrayList<Task>();
		intervalListExpected = new ArrayList<Task>();
		floatingListExpected = new ArrayList<Task>();
		
		Date d1 = Calendar.getInstance().getTime();
		Calendar.getInstance().set(2000, 12, 12);
		Date d2 = Calendar.getInstance().getTime();
		
		intervalTask = new Task("DAMITH", d1, d2, "CS2103");
		deadlineTask = new Task("DAMOTH", null, d2, "CS2103");
		floatingTask = new Task("DAMITH", null, null, "CS2103");
	}
	
	@Test
	public void testAddTask() throws IOException{
		Project test = initializeTest();
		
		assertEquals(deadlineListExpected, test.getDeadlineList());
		assertEquals(intervalListExpected, test.getIntervalList());
		assertEquals(floatingListExpected, test.getFloatingList());

	}
	
	@Test
	public void testDeleteTask() throws IOException{
		Project test = initializeTest();
		
		test.deleteTask(deadlineTask);
		deadlineListExpected.remove(deadlineTask);
		
		assertEquals(deadlineListExpected, test.getDeadlineList());

	}
	
	/*
	@Test
	public void testEditTask() throws IOException{
		Project test = initializeTest();
	

	}
	*/
	
	@Test
	public void testSearchTasks() throws IOException{
		Project test = initializeTest();

		ArrayList<Task> searchResults = test.searchForTask("DAMITH");
		ArrayList<Task> expectedList = new ArrayList<Task>();
		expectedList.add(intervalTask);
		expectedList.add(floatingTask);
		
		assertEquals(searchResults, expectedList);
	}
	
	@Test
	public void testDisplay() throws IOException{
		Project test = initializeTest();
		test.addTask(intervalTask);
		test.addTask(floatingTask);
		
		ArrayList<Task> displayResults = test.displayAllTasks();
		
		
		ArrayList<Task> expectedList = new ArrayList<Task>();
		expectedList.add(deadlineTask);
		expectedList.add(intervalTask);
		expectedList.add(floatingTask);
		Collections.sort(expectedList, new dateComparator());
		
		assertEquals(displayResults, expectedList);
		
	}
	
	private Project initializeTest() throws IOException {
		ArrayList<Task> testList = new ArrayList<Task>();
		testList.add(deadlineTask);
		Project test = new Project("CS2103", testList);
		
		deadlineListExpected.clear();
		intervalListExpected.clear();
		floatingListExpected.clear();
		
		deadlineListExpected.add(deadlineTask);
		intervalListExpected.add(intervalTask);
		floatingListExpected.add(floatingTask);
		
		test.addTask(intervalTask);
		test.addTask(floatingTask);
		return test;
	}
	
	private class dateComparator implements Comparator<Task>{
		public int compare(Task task1, Task task2) {
			if(task1.getDeadline() == null && task2.getDeadline() == null){
				return 0;
			}else if(task1.getDeadline() != null && task2.getDeadline() == null){
				return 1;
			}else if(task1.getDeadline() == null && task2.getDeadline() !=null){
				return -1;
			}else if(task1.getDeadline().before(task2.getStartDate())){
				return 1;
			}else if(task1.getDeadline().equals(task2.getStartDate())){
				return 0;
			}else{
				return -1;
			}
		}
		
	}

	
}

