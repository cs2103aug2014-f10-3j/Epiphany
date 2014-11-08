package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

import Logic.Engine.Engine;
import Logic.Interpreter.CommandType.AddCommandType;

public class CRUDTest {
	public static final String TASK_DESCRIPTION = "do something";
	@SuppressWarnings("static-access")
	@Test
	public void test() throws IOException, ParseException {
		Engine E = Engine.getInstance();
		AddCommandType addCommandType = new AddCommandType(TASK_DESCRIPTION);
		AddCommandType addCommandType2 = new AddCommandType("hello by tomorrow");

		E.executeCommand(addCommandType);
		E.executeCommand(addCommandType2);
		if(E.projectsList.get(E.projectsList.size() - 1).retrieveAllTasks().get(0).getTaskDescription().equals(TASK_DESCRIPTION)){
		}
		else{
			fail();
		}
		
	}

}
