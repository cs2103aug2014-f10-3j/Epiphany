package Logic.Interpreter.CommandType.Tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import Logic.Interpreter.CommandType.AddCommandType;

public class AddCommandTypeTestCases {

	@Test
	public void testAddFloatingCommandTypeWithoutProject() {
		AddCommandType addFloatingCommand = new AddCommandType("This is a floating command");
		assertEquals("add", addFloatingCommand.getType());
		assertEquals("This is a floating command", addFloatingCommand.getDescription());
		assertEquals(null, addFloatingCommand.getDateFrom());
		assertEquals(null, addFloatingCommand.getDateTo());
		assertEquals("default", addFloatingCommand.getProjectName());
	}
	
	@Test
	public void testAddDeadlineCommandTypeWithoutProject() {
		Calendar now = Calendar.getInstance();
		AddCommandType addDeadlineCommand = new AddCommandType("This is a deadline command", now.getTime());
		assertEquals("add", addDeadlineCommand.getType());
		assertEquals("This is a deadline command", addDeadlineCommand.getDescription());
		assertEquals(null, addDeadlineCommand.getDateFrom());
		assertEquals(now.getTime(), addDeadlineCommand.getDateTo());
		assertEquals("default", addDeadlineCommand.getProjectName());
	}

	@Test
	public void testAddDeadlineCommandTypeWithProject() {
		Calendar now = Calendar.getInstance();
		AddCommandType addDeadlineCommand = new AddCommandType("This is a deadline command", now.getTime(), "Project Name");
		assertEquals("add", addDeadlineCommand.getType());
		assertEquals("This is a deadline command", addDeadlineCommand.getDescription());
		assertEquals(null, addDeadlineCommand.getDateFrom());
		assertEquals(now.getTime(), addDeadlineCommand.getDateTo());
		assertEquals("Project Name", addDeadlineCommand.getProjectName());
	}
	
	@Test
	public void testAddIntervalCommandTypeWithoutProject() {
		Calendar now = Calendar.getInstance();
		Calendar later = Calendar.getInstance();
		later.add(Calendar.DAY_OF_MONTH, 3);
		AddCommandType addIntervalCommand = new AddCommandType("This is an interval command", now.getTime(), later.getTime());
		assertEquals("add", addIntervalCommand.getType());
		assertEquals("This is an interval command", addIntervalCommand.getDescription());
		assertEquals(now.getTime(), addIntervalCommand.getDateFrom());
		assertEquals(later.getTime(), addIntervalCommand.getDateTo());
		assertEquals("default", addIntervalCommand.getProjectName());
	}

	@Test
	public void testAddIntervalCommandTypeWithProject() {
		Calendar now = Calendar.getInstance();
		Calendar later = Calendar.getInstance();
		AddCommandType addIntervalCommand = new AddCommandType("This is a deadline command", now.getTime(), later.getTime(), "Project Name");
		assertEquals("add", addIntervalCommand.getType());
		assertEquals("This is a deadline command", addIntervalCommand.getDescription());
		assertEquals(now.getTime(), addIntervalCommand.getDateFrom());
		assertEquals(later.getTime(), addIntervalCommand.getDateTo());
		assertEquals("Project Name", addIntervalCommand.getProjectName());
	}
	
}
