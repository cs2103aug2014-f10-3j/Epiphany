//@author A0118905A
package Logic.Interpreter.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Logic.Exceptions.ExitException;
import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.CommandType.*;

public class EpiphanyInterpreterTestCases {
	/*private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	@Test
	public void initializationTest() throws IOException, ParseException {
		EpiphanyInterpreter.main(null);
		String inputData = "exit\r\n";
		System.setIn(new ByteArrayInputStream(inputData.getBytes()));
		assertEquals("Exiting Program.", outContent.toString());

	}*/

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void interpretDisplayTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("display");
		if(!actualCommand.getType().equals("display")){
			fail();
		}
		DisplayCommandType actualDisplayCommand = (DisplayCommandType) actualCommand;
		assertEquals("all", actualDisplayCommand.getModifiers());

		actualCommand = interpreter.interpretCommand("view all");
		if(!actualCommand.getType().equals("display")){
			fail();
		}
		actualDisplayCommand = (DisplayCommandType) actualCommand;
		assertEquals("all", actualDisplayCommand.getModifiers());

		actualCommand = interpreter.interpretCommand("display #Project Name");
		if(!actualCommand.getType().equals("display")){
			fail();
		}
		actualDisplayCommand = (DisplayCommandType) actualCommand;
		assertEquals("Project Name", actualDisplayCommand.getModifiers());

		actualCommand = interpreter.interpretCommand("display 26/07/2014");
		if(!actualCommand.getType().equals("display")){
			fail();
		}
		actualDisplayCommand = (DisplayCommandType) actualCommand;
		assertEquals("26-6-2014", actualDisplayCommand.getModifiers());
	}

	@Test
	public void displayInvalidModifierTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(InvalidCommandException.class);
		@SuppressWarnings("unused")
		CommandType actualCommand = interpreter.interpretCommand("display something");
	}

	@Test
	public void displayTwoHashtagsTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(InvalidCommandException.class);
		@SuppressWarnings("unused")
		CommandType actualCommand = interpreter.interpretCommand("display #something#YOLO#Error");
	}

	@Test
	public void interpretExitTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(ExitException.class);
		@SuppressWarnings("unused")
		CommandType actualCommand = interpreter.interpretCommand("exit");
	}

	@Test
	public void interpretSearchTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("search something");
		if(!actualCommand.getType().equals("search")){
			fail();
		}
		SearchCommandType actualSearchCommand = (SearchCommandType) actualCommand;
		assertEquals("something", actualSearchCommand.getTaskDescription());
		assertEquals("", actualSearchCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("search something #Project Name");
		if(!actualCommand.getType().equals("search")){
			fail();
		}
		actualSearchCommand = (SearchCommandType) actualCommand;
		assertEquals("something", actualSearchCommand.getTaskDescription());
		assertEquals("Project Name", actualSearchCommand.getProjectName());

		thrown.expect(InvalidCommandException.class);
		actualCommand = interpreter.interpretCommand("search");
	}

	@Test
	public void interpretDeleteTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("delete something");
		if(!actualCommand.getType().equals("delete")){
			fail();
		}
		DeleteCommandType actualDeleteCommand = (DeleteCommandType) actualCommand;
		assertEquals("something", actualDeleteCommand.getTaskDescription());
		assertEquals("default", actualDeleteCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("delete something #Project Name");
		if(!actualCommand.getType().equals("delete")){
			fail();
		}
		actualDeleteCommand = (DeleteCommandType) actualCommand;
		assertEquals("something", actualDeleteCommand.getTaskDescription());
		assertEquals("Project Name", actualDeleteCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("delete #Project Name");
		if(!actualCommand.getType().equals("delete")){
			fail();
		}
		actualDeleteCommand = (DeleteCommandType) actualCommand;
		assertEquals(null, actualDeleteCommand.getTaskDescription());
		assertEquals("Project Name", actualDeleteCommand.getProjectName());

		thrown.expect(InvalidCommandException.class);
		actualCommand = interpreter.interpretCommand("delete");
	}

	@Test
	public void addFloatingTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("Complete something");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		AddCommandType actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals(null, actualAddCommand.getDateFrom());
		assertEquals(null, actualAddCommand.getDateTo());
		assertEquals("default", actualAddCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("Complete something #Project Name");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals(null, actualAddCommand.getDateFrom());
		assertEquals(null, actualAddCommand.getDateTo());
		assertEquals("Project Name", actualAddCommand.getProjectName());

		thrown.expect(InvalidCommandException.class);
		actualCommand = interpreter.interpretCommand("ansodn valid words");
	}

	@Test
	public void addDeadlineTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("Complete something by 26th July");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		AddCommandType actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals("default", actualAddCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("Complete something by 26th July #Project Name");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals("Project Name", actualAddCommand.getProjectName());
	}

	@Test
	public void addIntervalTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("Complete something on 26th July from 9:30 to 11:30");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		AddCommandType actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals("default", actualAddCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("Complete something on 26th July from 9:30 to 11:30 #Project Name");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("Complete something", actualAddCommand.getDescription());
		assertEquals("Project Name", actualAddCommand.getProjectName());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void addMiscTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("\"do something\"");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		AddCommandType actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("do something", actualAddCommand.getDescription());
		assertEquals("default", actualAddCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("\"do something\" by 26/07");
		if(!actualCommand.getType().equals("add")){
			fail();
		}
		actualAddCommand = (AddCommandType) actualCommand;
		assertEquals("do something", actualAddCommand.getDescription());
		assertEquals("default", actualAddCommand.getProjectName());
		assertEquals(26, actualAddCommand.getDateTo().getDate());
		assertEquals(6, actualAddCommand.getDateTo().getMonth());

		try{
			actualCommand = interpreter.interpretCommand("do something #default");
			assert(false);
		} catch(InvalidCommandException e){
			assert(true);
		}
		try{
			actualCommand = interpreter.interpretCommand("d");
			assert(false);
		} catch(InvalidCommandException e){
			assert(true);
		}
	}

	@Test
	public void undoTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("undo");
		if(!actualCommand.getType().equals("undo")){
			fail();
		}
	}

	@Test
	public void redoTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("redo");
		if(!actualCommand.getType().equals("redo")){
			fail();
		}
	}

	@Test
	public void completeTaskTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("-m something");
		if(!actualCommand.getType().equals("complete")){
			fail();
		}
		CompleteCommandType actualCompleteCommand = (CompleteCommandType) actualCommand;
		assertEquals("something", actualCompleteCommand.getTaskDescription());
		assertEquals("default", actualCompleteCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("-m something #Project Name");
		if(!actualCommand.getType().equals("complete")){
			fail();
		}
		actualCompleteCommand = (CompleteCommandType) actualCommand;
		assertEquals("something", actualCompleteCommand.getTaskDescription());
		assertEquals("Project Name", actualCompleteCommand.getProjectName());

		thrown.expect(InvalidCommandException.class);
		actualCommand = interpreter.interpretCommand("-m");
	}

	@Test
	public void editTaskTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		CommandType actualCommand = interpreter.interpretCommand("edit something");
		if(!actualCommand.getType().equals("edit")){
			fail();
		}
		EditCommandType actualEditCommand = (EditCommandType) actualCommand;
		assertEquals("something", actualEditCommand.getTaskDescription());
		assertEquals("default", actualEditCommand.getProjectName());

		actualCommand = interpreter.interpretCommand("edit something #Project Name");
		if(!actualCommand.getType().equals("edit")){
			fail();
		}
		actualEditCommand = (EditCommandType) actualCommand;
		assertEquals("something", actualEditCommand.getTaskDescription());
		assertEquals("Project Name", actualEditCommand.getProjectName());

		thrown.expect(InvalidCommandException.class);
		actualCommand = interpreter.interpretCommand("edit");
	}

}
