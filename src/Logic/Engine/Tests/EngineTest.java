package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Logic.Engine.Engine;
import Logic.Interpreter.CommandType.AddCommandType;
import Logic.Interpreter.CommandType.CommandType;
import Logic.Interpreter.CommandType.DeleteCommandType;
import Logic.Interpreter.CommandType.DisplayCommandType;
import Logic.Interpreter.CommandType.EditCommandType;
import Logic.Interpreter.CommandType.SearchCommandType;
import Logic.Engine.Task;
import Logic.Engine.Project;

public class EngineTest{

	@Test
	public void engineAddDefaultTest() throws IOException, ParseException {
		Engine e = Engine.getInstance();
		// add floating tasks into default project
		AddCommandType add1 = new AddCommandType("This is a test task");
		AddCommandType add2 = new AddCommandType("This is a test task part 2");
		e.executeCommand(add1);
		e.executeCommand(add2);

		String saved1 = e.projectsList.get(0).displayAllTasks().get(0).getTaskDescription();
		String testing1 = "This is a test task";

		String saved2 = e.projectsList.get(0).displayAllTasks().get(1).getTaskDescription();
		String testing2 = "This is a test task part 2";

		assertEquals("This is a test task", saved1);
		assertEquals("This is a test task part 2", saved2);
	}
}
