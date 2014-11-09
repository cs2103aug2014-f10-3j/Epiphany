package Logic.Engine.Tests;
import static org.junit.Assert.*;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Logic.Engine.Engine;
import Logic.Engine.Task;
import Logic.Interpreter.CommandType.AddCommandType;
import Logic.Interpreter.CommandType.SearchCommandType;

/**
 * 
 * @author tinweiyang
 *
 */

public class CRUDTest {
	Engine E;
	
	/* Addition of Tasks:
	 * Add floating
	 * Add tasks with dead line
	 * Add timed tasks from date to date
	 * Add floating with project
	 * Add deadline with project
	 * add timed tasks with project
	 * 
	 * Search
	 * Search by Task
	 * Search by projects via #
	 * 
	 * Delete
	 * Delete by Task
	 * Delete by Project
	 * 
	 * Undo
	 * Create a task, undo 
	 * delete a task, undo
	 * 
	 * Redo. Same as redo
	 * 
	 * isComplete
	 * check if the method has the proper
	 * 
	 * Edit
	 */

	private static final String TASK_DESCRIPTION_1 = "do something";
	private static final String TASK_DESCRIPTION_2 = "hello world";
	private static final String TASK_DESCRIPTION_3 = "quick brown fox jumps over the lazy dog";
	private static final String TASK_DESCRIPTION_4 = "complete v0.5";
	private static final String TASK_DESCRIPTION_5 = "finish ps7";
	private static final String TASK_DESCRIPTION_6 = "some undefined task";
	
	public CRUDTest() throws IOException, ParseException {
		E = Engine.getInstance();
	}
	
	public void taskIsEquals(Task Actual, String name, Date deadLine, Date startDate, String projectName, Boolean isCompleted) {
		assertEquals(Actual.getTaskDescription(), name);
		assertEquals(Actual.getDeadline(), deadLine);
		assertEquals(Actual.getStartDate(), startDate);
		assertEquals(Actual.getProjectName(), projectName);
		assertEquals(Actual.isCompleted(), isCompleted);
	}
	
	/*
	 * Test must be conducted with an empty storage!!!!! Otherwise it will fail
	 * 
	 */
	
	/************************ ADDITION OF TASKS ***********************************/
	@Test 
	public void testAddSample1() throws IOException, ParseException {
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_1);
		E.executeCommand(addCommandType);
	
		//Task Actual = E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks().get(0);
		Task Actual = E.projectsList.get(0).getFloatingList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_1, null, null, "default", false);
	}
	
	@Test
	public void testAddSample2() throws IOException, ParseException {
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_2);
		E.executeCommand(addCommandType);
	
		//Task Actual = E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks().get(1);
		Task Actual = E.projectsList.get(0).getFloatingList().get(1);
	
		taskIsEquals(Actual, TASK_DESCRIPTION_2, null, null, "default", false);
	}
	
	@Test
	public void testAddWithDeadLine() throws IOException, ParseException {
		Date testDate = new Date(114, 10, 31);
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_3, testDate);
		E.executeCommand(addCommandType);
		
		Task Actual = E.projectsList.get(0).getDeadlineList().get(0); // gets default, gets the first task in Deadline List
		taskIsEquals(Actual, TASK_DESCRIPTION_3, testDate, null, "default", false);
	}
	
	@Test
	public void testAddTimedTask() throws IOException, ParseException {
		Date dateFrom = new Date(114, 10, 31);
		Date dateTo = new Date(115, 11, 10);
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_4, dateFrom, dateTo);
		E.executeCommand(addCommandType);
		
		Task Actual = E.projectsList.get(0).getIntervalList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_4, dateTo, dateFrom, "default", false);
	}
	
	
	@Test
	public void testProjectFloating() throws IOException, ParseException {
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_5, null, null, "cs2010");
		E.executeCommand(addCommandType);
		
		//Task Actual = E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks().get(4);
		Task Actual = E.projectsList.get(1).getFloatingList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_5, null, null, "cs2010", false);
	}
	
	/*
	@Test
	public void testFloatingWithProject2() throws IOException, ParseException {
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_6, null, null, "cs3233");
		E.executeCommand(addCommandType);
		
		//Task Actual = E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks().get(0);
		Task Actual = E.projectsList.get(2).getFloatingList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_6, null, null, "cs3233", false);
	}
	*/
	
	@Test
	public void testAddWithDeadLineProject() throws IOException, ParseException {
		Date testDate = new Date(114, 10, 31);
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_5, testDate, null, "cs2010");
		E.executeCommand(addCommandType);
		
		Task Actual = E.projectsList.get(1).getDeadlineList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_5, testDate, null, "cs2010", false);
	}
	
	/************************ SEARCHING TASKS ***********************************/
	// search by task
	// search by task and project
	// if there are no tasks 
	
	@Test 
	public void searchTest() throws IOException, ParseException {
		SearchCommandType searchCommandType = new SearchCommandType(TASK_DESCRIPTION_1);
		E.executeCommand(searchCommandType);
	    
		// do the check from storage, somewhere you know it will confirm appear
		Task Actual = E.projectsList.get(0).getFloatingList().get(0);

		assertEquals(Actual.getTaskDescription(), TASK_DESCRIPTION_1);
		assertEquals(Actual.getDeadline(), null);
		assertEquals(Actual.getStartDate(), null);
		assertEquals(Actual.getProjectName(), "default");
		assertEquals(Actual.isCompleted(), false);
	}
	
	
	
	/************************ DELETION OF TASKS ***********************************/
}
