//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.CommandType.CompleteCommandType;

public class CompleteCommandTypeTest {

	@Test
	public void testCompleteCommandTypeWithoutProject() {
		CompleteCommandType deleteCommand = new CompleteCommandType("This is a complete command");
		assertEquals("complete", deleteCommand.getType());
		assertEquals("This is a complete command", deleteCommand.getTaskDescription());
		assertEquals("default", deleteCommand.getProjectName());
	}
	
	@Test
	public void testCompleteCommandTypeWithProject() {
		CompleteCommandType deleteCommand = new CompleteCommandType("This is a complete command", "Project Name");
		assertEquals("complete", deleteCommand.getType());
		assertEquals("This is a complete command", deleteCommand.getTaskDescription());
		assertEquals("Project Name", deleteCommand.getProjectName());
	}


}
