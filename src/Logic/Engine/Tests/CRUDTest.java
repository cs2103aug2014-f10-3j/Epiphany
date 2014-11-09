package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import com.sun.javafx.tk.Toolkit.Task;

import Logic.Engine.Engine;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.Interpreter.CommandType.AddCommandType;
import Logic.Interpreter.CommandType.CompleteCommandType;
import Logic.Interpreter.CommandType.DeleteCommandType;
import Logic.Interpreter.CommandType.DisplayCommandType;
import Logic.Interpreter.CommandType.RedoCommandType;
import Logic.Interpreter.CommandType.ResetCommandType;
import Logic.Interpreter.CommandType.SearchCommandType;
import Logic.Interpreter.CommandType.UndoCommandType;
import Logic.Engine.*;

public class CRUDTest {
	public static final String TASK_DESCRIPTION_FLOATING = "finish tutorial 5";
	public static final String TASK_DESCRIPTION_DEADLINE = "finish V0.5 by tomorrow";
	public static final String TASK_DESCRIPTION_INTERVAL = "complete accounting work from monday to thursday";
	public static final String TASK_DESCRIPTION_PROJECT = "finish project";
	public static final String TASK_DESCRIPTION_PROJECT2 = "finish lecture 10";

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

	@SuppressWarnings("static-access")
	@Test
	public void testAddFloating() throws IOException, ParseException {
		Engine E = Engine.getInstance();

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

	@SuppressWarnings("static-access")
	@Test
	public void testAddDeadline() throws IOException, ParseException {

		Engine E = Engine.getInstance();
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

	@SuppressWarnings("static-access")
	@Test
	public void testAddInterval() throws IOException, ParseException {
		Engine E = Engine.getInstance();
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

	@SuppressWarnings("static-access")
	@Test
	public void testAddProject() throws IOException, ParseException {
		Engine E = Engine.getInstance();

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

	@Test
	public void testDelete() throws IOException, ParseException {

		Engine E = Engine.getInstance();
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

	@Test
	public void testDeleteProject() throws IOException, ParseException {

		Engine E = Engine.getInstance();
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

	@SuppressWarnings("static-access")
	@Test
	public void searchTest() throws IOException, ParseException {
		Engine E = Engine.getInstance();
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
	@Test
	public void testDisplay() throws IOException, ParseException {
		Engine E = Engine.getInstance();
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
	@SuppressWarnings("static-access")
	@Test
	public void testUndoForAdd() throws IOException, ParseException {
		Engine E = Engine.getInstance();

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
	@SuppressWarnings("static-access")
	@Test
	public void testRedo() throws IOException, ParseException {
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
