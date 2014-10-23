package Logic.Interpreter.CommandType.Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import Logic.Interpreter.CommandType.DeleteCommandType;

public class DeleteCommandTypeTestCases {

	@Test
	public void testDeleteCommandTypeWithoutProject() {
		DeleteCommandType deleteCommand = new DeleteCommandType("This is a delete command");
		assertEquals("delete", deleteCommand.getType());
		assertEquals("This is a delete command", deleteCommand.getTaskDescription());
		assertEquals("default", deleteCommand.getProjectName());
	}
	
	@Test
	public void testDeleteCommandTypeWithProject() {
		DeleteCommandType deelteCommand = new DeleteCommandType("This is a delete command", "Project Name");
		assertEquals("delete", deelteCommand.getType());
		assertEquals("This is a delete command", deelteCommand.getTaskDescription());
		assertEquals("Project Name", deelteCommand.getProjectName());
	}

}
