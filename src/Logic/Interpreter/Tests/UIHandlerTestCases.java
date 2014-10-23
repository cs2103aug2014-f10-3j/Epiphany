package Logic.Interpreter.Tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.Interpreter.UIHandler;

public class UIHandlerTestCases {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
	public void printToTerminalLnTest() {
	    UIHandler uiHandler = UIHandler.getInstance();
	    uiHandler.printToTerminal("hello");
	    assertEquals("hello\r\n", outContent.toString());
	}
	
	@Test
	public void printToTerminalTest() {
	    UIHandler uiHandler = UIHandler.getInstance();
	    uiHandler.printToTerminal("hello","inline");
	    assertEquals("hello", outContent.toString());
	}
	
	@Test
	public void printToDisplayTest() {
	    UIHandler uiHandler = UIHandler.getInstance();
	    uiHandler.printToDisplay("hello");
	    assertEquals("hello\r\n", outContent.toString());
	}
}
