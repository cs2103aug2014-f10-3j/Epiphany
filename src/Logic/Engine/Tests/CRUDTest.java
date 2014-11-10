package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import Logic.Engine.Engine;
import Logic.Engine.Task;
import Logic.CommandType.*;



/**
 * This class tests the various operations that are used in Engine.
 * 
 * 
 */
//@author A0110924R

public class CRUDTest {
	Engine E;
	private static final String TASK_DESCRIPTION_FLOATING = "finish tutorial 5";
	private static final String TASK_DESCRIPTION_DEADLINE = "finish V0.5 by tomorrow";
	private static final String TASK_DESCRIPTION_INTERVAL = "complete accounting work from monday to thursday";
	private static final String TASK_DESCRIPTION_PROJECT = "finish project";
	private static final String TASK_DESCRIPTION_PROJECT2 = "finish lecture 10";
	private static final String TASK_DESCRIPTION_1 = "do something";
	
	/************ Constructor **************/
	
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

	/***************************Reset test**************************/
	/*@Test
	public void resetTest() throws IOException, ParseException {
		
		Engine E = Engine.getInstance();
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		if(Engine.projectsList.size()==1){
			assert(true);
			}
			else{
				fail();
			}
		
		E.executeCommand(resetCommandType);*/
	
	/******************************** Testing for add ********************************/
	/*
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testAddFloating() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);

		if (E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks()
				.get(0).getTaskDescription().equals(TASK_DESCRIPTION_FLOATING)) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}
	*/
	
	//A0118794R
	@SuppressWarnings("static-access")
	@Test
	public void testAddFloating2() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION_1);
		E.executeCommand(addCommandType);
		
		Task Actual = E.projectsList.get(0).getFloatingList().get(0);
		taskIsEquals(Actual, TASK_DESCRIPTION_1, null, null, "default", false);
	}
	
	
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testAddDeadline() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_DEADLINE);
		E.executeCommand(addCommandType);

		if (E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks()
				.get(0).getTaskDescription().equals(TASK_DESCRIPTION_DEADLINE)) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testAddInterval() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_INTERVAL);

		E.executeCommand(addCommandType);
		if (E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks()
				.get(0).getTaskDescription().equals(TASK_DESCRIPTION_INTERVAL)) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testAddProject() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_PROJECT, null, "CS2103");
		E.executeCommand(addCommandType);
		Logic.Engine.Task currTask = E.projectsList.get(1).getFloatingList()
				.get(0);
		if (currTask.getTaskDescription().equals(TASK_DESCRIPTION_PROJECT)
				&& (currTask.getProjectName().equals("CS2103"))) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}

	/******************************** Testing of Delete ********************************/
	//@author A0110924R
	@Test
	public void testDelete() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);

		DeleteCommandType deleteCommandType = new DeleteCommandType("finish");
		E.executeCommand(deleteCommandType);

		if (Engine.projectsList.get(0).getFloatingList().isEmpty()) {
			assert (true);
		} else {
			fail();
		}
		E.executeCommand(resetCommandType);
	}
	//@author A0110924R
	@Test
	public void testDeleteProject() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_PROJECT, null, "CS2103");
		AddCommandType addCommandType2 = new AddCommandType(
				TASK_DESCRIPTION_PROJECT2, null, "CS2103");
		E.executeCommand(addCommandType);
		E.executeCommand(addCommandType2);

		DeleteCommandType deleteCommandType = new DeleteCommandType("#cs2103");
		E.executeCommand(deleteCommandType);

		if (Engine.projectsList.get(0).getFloatingList().isEmpty()) {
			assert (true);
		} else {
			fail();
		}
		E.executeCommand(resetCommandType);
	}

	/******************************** Testing of Search ********************************/
	//@author weiyang
	@SuppressWarnings("static-access")
	@Test
	public void searchTest() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		SearchCommandType searchCommandType = new SearchCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(searchCommandType);

		Logic.Engine.Task Actual = E.projectsList.get(0).getFloatingList()
				.get(0);

		assertEquals(Actual.getTaskDescription(), TASK_DESCRIPTION_FLOATING);
		assertEquals(Actual.getDeadline(), null);
		assertEquals(Actual.getStartDate(), null);
		assertEquals(Actual.getProjectName(), "default");
		assertEquals(Actual.isCompleted(), false);

		E.executeCommand(resetCommandType);
	}
	/******************************** Testing of Display ********************************/
	//@author A0110924R
	@Test
	public void testDisplay() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		DisplayCommandType displayCommandType = new DisplayCommandType("all");
		E.executeCommand(displayCommandType);
		
		if(Engine.ListByDate.get(Engine.ListByDate.size() - 1).getList().get(0).getTaskDescription().equals(TASK_DESCRIPTION_FLOATING)){
			assert(true);}
		
			else{
				fail();
			}
		
		E.executeCommand(resetCommandType);
	}

/*	public void displayProjectTest() throws IOException, ParseException {
		Engine E = Engine.getInstance();
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);

		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_PROJECT, null, "CS2103");
		E.executeCommand(addCommandType);
		AddCommandType addCommandType2 = new AddCommandType(
				TASK_DESCRIPTION_PROJECT2, null, "CS2103");
		E.executeCommand(addCommandType2);
		
		DisplayCommandType displayCommandType = new DisplayCommandType("#CS2103");
		E.executeCommand(displayCommandType);
		
		if(Engine.ListByDate.get(1).getList().get(0).getTaskDescription().equals(TASK_DESCRIPTION_PROJECT)){
			assert(true);}
		
			else{
				fail();
			}
		
		E.executeCommand(resetCommandType);
	}*/
	/******************************** Testing of Undo ********************************/
	/*@SuppressWarnings("static-access")
	@Test
	public void testUndoForDelete() throws IOException, ParseException {
		Engine E = Engine.getInstance();

		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		DeleteCommandType deleteCommandType = new DeleteCommandType("finish");
		E.executeCommand(deleteCommandType);
		
		UndoCommandType undoCommandType = new UndoCommandType();
		E.executeCommand(undoCommandType);
		
		if (E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks()
				.get(0).getTaskDescription().equals(TASK_DESCRIPTION_FLOATING)) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}*/
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testUndoForAdd() throws IOException, ParseException {

		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		UndoCommandType undoCommandType = new UndoCommandType();
		E.executeCommand(undoCommandType);
		
		if (E.projectsList.get(E.projectsList.size() - 1).isEmpty()) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}

	/******************************** Testing of Redo ********************************/
	//@author A0110924R
	@SuppressWarnings("static-access")
	@Test
	public void testRedo() throws IOException, ParseException {
		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		DeleteCommandType deleteCommandType = new DeleteCommandType("finish");
		E.executeCommand(deleteCommandType);
		
		UndoCommandType undoCommandType = new UndoCommandType();
		E.executeCommand(undoCommandType);
		
		RedoCommandType redoCommandType = new RedoCommandType();
		E.executeCommand(redoCommandType);
		
		
		if (E.projectsList.get(E.projectsList.size() - 1).isEmpty()) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}
	/*********************************Test Complete*****************************/
	/*@SuppressWarnings("static-access")
	public void testComplete() throws IOException, ParseException {
		Engine E = Engine.getInstance();

		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		CompleteCommandType completeCommandType = new CompleteCommandType("finish");
		E.executeCommand(completeCommandType);
		
		if (!(E.projectsList.get(E.projectsList.size() - 1).isEmpty())) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}*/
	/********************************Edit Test************************/
	/******************************** Testing of Edit ********************************/
/*	@SuppressWarnings("static-access")
	@Test
	public void testEdit() throws IOException, ParseException {
		Engine E = Engine.getInstance();

		ResetCommandType resetCommandType = new ResetCommandType();
		E.executeCommand(resetCommandType);
		
		AddCommandType addCommandType = new AddCommandType(
				TASK_DESCRIPTION_FLOATING);
		E.executeCommand(addCommandType);
		
		EditCommandType editCommandType = new EditCommandType("finish");
		E.executeCommand(deleteCommandType);
		
		UndoCommandType undoCommandType = new UndoCommandType();
		E.executeCommand(undoCommandType);
		
		RedoCommandType redoCommandType = new RedoCommandType();
		E.executeCommand(redoCommandType);
		
		
		if (E.projectsList.get(E.projectsList.size() - 1).isEmpty()) {
			assert (true);
		} else {
			fail();
		}

		E.executeCommand(resetCommandType);
	}*/
}
