//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.CommandType.SearchCommandType;

public class SearchCommandTypeTestCases {

	@Test
	public void testSearchCommandTypeWithoutProject() {
		SearchCommandType searchCommand = new SearchCommandType("Task description");
		assertEquals("search", searchCommand.getType());
		assertEquals("Task description", searchCommand.getTaskDescription());
		assertEquals("", searchCommand.getProjectName());
	}
	
	@Test
	public void testSearchCommandTypeWithProject() {
		SearchCommandType searchCommand = new SearchCommandType("Task description", "Project Name");
		assertEquals("search", searchCommand.getType());
		assertEquals("Task description", searchCommand.getTaskDescription());
		assertEquals("Project Name", searchCommand.getProjectName());
	}

}
