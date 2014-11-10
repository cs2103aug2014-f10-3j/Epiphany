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
	//To test, make an actual project and test it against an expected set of results.
	
	
	/*********Test Attributes*******************/
	private ArrayList<Task> deadLineListExpected;
	private ArrayList<Task> intervalListExpected;
	private ArrayList<Task> floatingListExpected;
	Task deadLineTaskExpected;
	Task intervalTaskExpected;
	Task floatingTaskExpected;
	
	/***************To be tested on***********/
	static Project testProj;
	
	
	public ProjectTestCases() throws IOException{
		// Need to initialize DS.
		deadLineListExpected = new ArrayList<Task>();
		intervalListExpected = new ArrayList<Task>();
		floatingListExpected = new ArrayList<Task>();
		
		Date d1 = Calendar.getInstance().getTime();
		Calendar.getInstance().set(2000, 12, 12);
		Date d2 = Calendar.getInstance().getTime();
		
		intervalTaskExpected = new Task("FLY DAMITH", d1, d2, "CS2103");
		deadLineTaskExpected = new Task("FLY RABBIT", null, d2, "CS2103");
		floatingTaskExpected = new Task("FLY RENEGADE", null, null, "CS2103");
		
		deadLineListExpected.add(deadLineTaskExpected);
		intervalListExpected.add(intervalTaskExpected);
		floatingListExpected.add(floatingTaskExpected);
		
		testProj = new Project("CS2103", new ArrayList<Task>());
		
	}
	
	@Test
	public void testAddTask() throws IOException{
		populateTestProj();
		
		assertEquals(deadLineListExpected, testProj.getDeadlineList());
		assertEquals(intervalListExpected, testProj.getIntervalList());
		assertEquals(floatingListExpected, testProj.getFloatingList());
	}

	private void populateTestProj() throws IOException {
		testProj.addTask(deadLineTaskExpected);
		testProj.addTask(floatingTaskExpected);
		testProj.addTask(intervalTaskExpected);
	}
	
	@Test
	public void testDeleteTask() throws IOException{
		testProj.deleteTask(deadLineTaskExpected);
		testProj.deleteTask(floatingTaskExpected);
		testProj.deleteTask(intervalTaskExpected);
		
		deadLineListExpected.clear();
		intervalListExpected.clear();
		floatingListExpected.clear();
		
		assertEquals(deadLineListExpected, testProj.getDeadlineList());
		assertEquals(intervalListExpected, testProj.getIntervalList());
		assertEquals(floatingListExpected, testProj.getFloatingList());
	}
	
	@Test
	public void testSearchForTask() throws IOException{
		populateTestProj();
		ArrayList<Task> expectedList = new ArrayList<Task>();
		
		expectedList.add(deadLineTaskExpected);
		expectedList.add(intervalTaskExpected);
		expectedList.add(floatingTaskExpected);
		
		assertEquals(expectedList, testProj.searchForTask("FLY"));
	}
	
	@Test
	public void testRetrieve() throws IOException{
		populateTestProj();

		ArrayList<Task> expectedList = new ArrayList<Task>();
		
		expectedList.add(deadLineTaskExpected);
		expectedList.add(intervalTaskExpected);
		expectedList.add(floatingTaskExpected);
		
		Collections.sort(expectedList, new dateComparator());
		assertEquals(expectedList, testProj.retrieveAllTasks());
	}
	
	@Test
	public void testGetProjectName(){
		assertEquals("CS2103", testProj.getProjectName());
	}
	
	@Test
	public void testContainsTask() throws IOException{
		populateTestProj();
		Task t = new Task("FLY RENEGADE", null, null, "CS2103");

		assertEquals(true, testProj.containsTask(t));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**********************Sorting Compartors***********************************/		

	private class dateComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			if (task1.getDeadline() == null && task2.getDeadline() == null) {
				return 0;
			} else if (task1.getDeadline() != null
					&& task2.getDeadline() == null) {
				return 1;
			} else if (task1.getDeadline() == null
					&& task2.getDeadline() != null) {
				return -1;
			} else if (task1.getDeadline() != null && task2.getDeadline() != null) {
				return task1.getDeadline().compareTo(task2.getDeadline());
			}
			
			return 0;
		}
	
	} 

	
}

