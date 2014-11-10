//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;


import org.junit.Test;

import Logic.CommandType.DeleteCommandType;

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
		DeleteCommandType deleteCommand = new DeleteCommandType("This is a delete command", "Project Name");
		assertEquals("delete", deleteCommand.getType());
		assertEquals("This is a delete command", deleteCommand.getTaskDescription());
		assertEquals("Project Name", deleteCommand.getProjectName());
	}
	
	@Test
	public void testDeleteProjectCommandType() {
		DeleteCommandType deleteCommand = new DeleteCommandType(null, "Project Name");
		assertEquals("delete", deleteCommand.getType());
		assertEquals(null, deleteCommand.getTaskDescription());
		assertEquals("Project Name", deleteCommand.getProjectName());
	}

}
