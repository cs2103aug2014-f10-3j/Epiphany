//@author A0119264E
package Logic.Engine.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.Engine.Task;

public class TaskTestCases {
	
	/*****Test Attributes********/
	/*
	private static String taskDescription;
	private static String duplicateTaskDescription; // backup of taskDescription, cannot be mutated							
	private static Date from;
	private static Date deadLine;
	private static String projectName;
	private static boolean isCompleted;
	private static String completionStatus;
	*/
//	private static String[] months;
//	private static String[] days;
//	private static boolean parity;
//	private static HashMap<String, String> colors;
//	private String color;
	
	/*******Test Object************/
	static Task testTask;
	
	/*public TaskTestCases(){
		taskDescription = "FLY DAMITH";
		duplicateTaskDescription = "FLY DAMITH";
		from = Calendar.getInstance().getTime();
		Calendar.getInstance().set(2000, 12, 12);
		deadLine = Calendar.getInstance().getTime();
		projectName = "RENEGADE";
		isCompleted = false;
		completionStatus = "";
		months = new String[12];
		days = new String[7];
		parity = false;
		color = colors.get("reset");
		
		testTask = new Task(taskDescription, from, deadLine, projectName, isCompleted);
		populateDays();
		populateMonths();
		populateColors();
		
	}*/
	
	@Test
	public void testGetTaskDescription(){
		testTask = new Task("DAMITH", null, null, "", false);

		assertEquals("DAMITH", testTask.getTaskDescription());
	}
	
	@Test
	public void testStringMethod(){
		String test = "floating~fly a kite~null~null~default~false";
		Task t = new Task("fly a kite", null, null, "default");
		
		assertEquals(test, t.toString());
	}
	
	
	
	/********Other Methods*********/
	/*
	private void populateDays() {
		days[0] = "Sunday";
		days[1] = "Monday";
		days[2] = "Tuesday";
		days[3] = "Wednesday";
		days[4] = "Thursday";
		days[5] = "Friday";
		days[6] = "Saturday";
	}
	
	private void populateMonths() {
		months[0] = "Jan";
		months[1] = "Feb";
		months[2] = "Mar";
		months[3] = "Apr";
		months[4] = "May";
		months[5] = "Jun";
		months[6] = "Jul";
		months[7] = "Aug";
		months[8] = "Sep";
		months[9] = "Oct";
		months[10] = "Nov";
		months[11] = "Dec";
	}
	
	private static void populateColors(){
		colors = new HashMap<String, String>();
		colors.put("green", "\033[32m");
		colors.put("red", "\033[31m");
		colors.put("black", "\033[30m");
		colors.put("white", "\033[37m");
		colors.put("reset", "\033[0m");
	}
	*/
}
