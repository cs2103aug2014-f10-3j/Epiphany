package Logic.Interpreter.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.Interpreter.CommandType.EditCommandType;

public class EditCommandTypeTestCases {

	@Test
	public void testEditCommandType() {
		EditCommandType editCommand = new EditCommandType("Old Task description", "New Task description");
		assertEquals("edit", editCommand.getType());
		assertEquals("Old Task description", editCommand.getOriginalTask());
		assertEquals("New Task description", editCommand.getNewTask());
	}

}
