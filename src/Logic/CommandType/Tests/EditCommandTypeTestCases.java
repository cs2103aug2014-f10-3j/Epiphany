//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.CommandType.EditCommandType;

public class EditCommandTypeTestCases {

	@Test
	public void testEditCommandType() {
		EditCommandType editCommand = new EditCommandType("task description");
		assertEquals("edit", editCommand.getType());
		assertEquals("task description", editCommand.getTaskDescription());
		assertEquals("default", editCommand.getProjectName());
	}
	
	@Test
	public void testEditWithProjectCommandType() {
		EditCommandType editCommand = new EditCommandType("task description", "project name");
		assertEquals("edit", editCommand.getType());
		assertEquals("task description", editCommand.getTaskDescription());
		assertEquals("project name", editCommand.getProjectName());
	}

}
