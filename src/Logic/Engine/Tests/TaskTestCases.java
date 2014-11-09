package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.util.Date;


import org.junit.Test;

import Logic.Engine.Task;

public class TaskTestCases {
	String taskDescription;
	Date from;
	Date deadLine;
	String projectName;
	boolean isCompleted;
	
	public TaskTestCases(){
		taskDescription = "Finish 2103";
		from = null;
		deadLine = null;
		projectName = "CS2103";
		isCompleted = false;
	}
	
	@Test
	public void testTaskDescription() {
		Task t = new Task("finish CS2103", null, null, "CS2103");
		
		assertEquals(taskDescription, t.getTaskDescription());
	}

	public void testStartDate() {
		Task t = new Task("finish CS2103", null, null, "CS2103");
		
		assertEquals(from, t.getStartDate());
	}
	
	public void testDeadLine() {
		Task t = new Task("finish CS2103", null, null, "CS2103");
		
		assertEquals(from, t.getStartDate());
	}

	public String getProjectName() {
		return this.projectName;
	}

	public boolean isCompleted() {
		return isCompleted;
	}
	
	public boolean hasTask() {
		return (this.taskDescription == null) ? false : true;
	}
	
	/**
	 * Checks if this task has an interval, i.e a start and end date.
	 * @return
	 */
	/*
	public boolean hasInterval(){
		if(this.startDate() != null && this.hasDeadLine() != null){
			return true;
		}else{
			return false;
		}
	}
	*/
	public boolean hasDeadLine() {
		return (this.deadLine == null) ? false : true;
	}
}
