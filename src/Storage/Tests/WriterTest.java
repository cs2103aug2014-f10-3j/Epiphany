package Storage.Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.junit.Test;

import Logic.Engine.Project;
import Logic.Engine.Task;
import Storage.Reader;
import Storage.Writer;

public class WriterTest {
	/***********Test Attributes********/
	private ArrayList<Task> dLineList;
	private ArrayList<Task> interList;
	private ArrayList<Task> floatList;
	
	Task deadLineTaskExpected;
	Task intervalTaskExpected;
	Task floatingTaskExpected;
	
	
	public WriterTest(){
		dLineList = new ArrayList<Task>();
		interList = new ArrayList<Task>();
		floatList = new ArrayList<Task>();
		
		Date d1 = Calendar.getInstance().getTime();
		Calendar.getInstance().set(2000, 12, 12);
		Date d2 = Calendar.getInstance().getTime();
		
		intervalTaskExpected = new Task("FLY DAMITH", d1, d2, "CS2103");
		deadLineTaskExpected = new Task("FLY RABBIT", null, d2, "CS2103");
		floatingTaskExpected = new Task("FLY RENEGADE", null, null, "CS2103");
		
		dLineList.add(deadLineTaskExpected);
		interList.add(intervalTaskExpected);
		floatList.add(floatingTaskExpected);
	}
	
	@Test
	public void testWrite() throws IOException, ParseException{
		Writer w = new Writer("krig", dLineList, interList, floatList);
		w.writeToFile();
		
		ArrayList<String> testNames = new ArrayList<String>();
		testNames.add("krig");
		
		ArrayList<Task> allTaskList = new ArrayList<Task>();
		allTaskList.addAll(dLineList);
		allTaskList.addAll(interList);
		allTaskList.addAll(floatList);
		
		
		Project p = new Project("krig", allTaskList);
		ArrayList<Project> pList = new ArrayList<Project>();
		pList.add(p);
		
		Reader r = new Reader(testNames, pList);
		r.readProjectData();
		
		assertEquals(testNames, r.getProjectNames());
	
	}
	
	@Test
	public void testAddToProjectMasterList() throws IOException{
		Writer w = new Writer("krig", dLineList, interList, floatList);
		w.writeToFile();
		
		ArrayList<String> projectNames = new ArrayList<String>();
		ArrayList<String> expected = new ArrayList<String>();
		
		
		Scanner sc = new Scanner(new File("../Epiphany/src/Storage/projectMasterList.txt"));
		while (sc.hasNextLine()) {
			projectNames.add(sc.nextLine());

		}
		sc.close();
		
		projectNames.add("flamith");
		
		Writer.addToProjectMasterList("flamith");
		
		Scanner sc2 = new Scanner(new File("../Epiphany/src/Storage/projectMasterList.txt"));
		while (sc2.hasNextLine()) {
			expected.add(sc2.nextLine());
		}
		sc2.close();
		
		assertEquals(expected, projectNames);
		
		
	}
	
}
