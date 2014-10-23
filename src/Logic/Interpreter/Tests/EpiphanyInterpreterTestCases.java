package Logic.Interpreter.Tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Logic.Exceptions.ExitException;
import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.EpiphanyInterpreter;
import Logic.Interpreter.CommandType.AddCommandType;
import Logic.Interpreter.CommandType.CommandType;
import Logic.Interpreter.CommandType.DeleteCommandType;
import Logic.Interpreter.CommandType.DisplayCommandType;
import Logic.Interpreter.CommandType.SearchCommandType;
import Logic.Interpreter.DateInterpreter.strtotime;

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
	}
	
	@Test
	public void displayInvalidModifierTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(InvalidCommandException.class);
		CommandType actualCommand = interpreter.interpretCommand("display something");
	}
	
	@Test
	public void displayTwoHashtagsTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(InvalidCommandException.class);
		CommandType actualCommand = interpreter.interpretCommand("display #something#YOLO#Error");
	}
	
	@Test
	public void interpretExitTest() throws IOException, ParseException, InvalidCommandException, ExitException{
		EpiphanyInterpreter interpreter = new EpiphanyInterpreter();
		thrown.expect(ExitException.class);
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
	
}