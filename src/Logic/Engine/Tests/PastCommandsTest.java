package Logic.Engine.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Logic.Engine.PastCommands;
import Logic.Engine.Project;
import Logic.Engine.Task;

public class PastCommandsTest {
	private static String type;
	private static Task task;
	private static Project project;
	private static String projectName;
	
	public PastCommandsTest() throws IOException{
		type = "add";
		task = new Task("FLY BASS", null, null, "VIM");
		project = new Project("VIM", new ArrayList<Task>());
		projectName = "VIM";
	}
	@Test
	public void testGetType(){
		PastCommands p = new PastCommands(type, task, projectName);
		
		assertEquals("add", p.getType());
	}
	
	@Test
	public void testGetTask(){
		PastCommands p = new PastCommands(type, task, projectName);

		assertEquals(task, p.getTask());
	}
	
	@Test
	public void testGetProject(){
		PastCommands p = new PastCommands(type, project);

		assertEquals(project, p.getProject());
	}
	
	@Test
	public void testGetProjectName(){
		PastCommands p = new PastCommands(type, projectName);
		
		assertEquals(projectName, p.getProjectName());
		
	}

}
