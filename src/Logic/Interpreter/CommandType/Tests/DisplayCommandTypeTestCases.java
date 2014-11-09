//@author A0118905A
package Logic.Interpreter.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.Interpreter.CommandType.DisplayCommandType;

public class DisplayCommandTypeTestCases {

	@Test
	public void testDisplayCommandTypeWithoutProject() {
		DisplayCommandType displayAllCommand = new DisplayCommandType();
		assertEquals("display", displayAllCommand.getType());
		assertEquals("all", displayAllCommand.getModifiers());
	}
	
	@Test
	public void testDisplayCommandTypeWithProject() {
		DisplayCommandType displaySomeCommand = new DisplayCommandType("some modifiers");
		assertEquals("display", displaySomeCommand.getType());
		assertEquals("some modifiers", displaySomeCommand.getModifiers());
	}

}
