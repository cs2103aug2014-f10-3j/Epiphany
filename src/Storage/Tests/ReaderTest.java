package Storage.Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Logic.Engine.Project;
import Logic.Engine.Task;
import Storage.Reader;
import Storage.Writer;

public class ReaderTest {
	private String fileName;
	private ArrayList<Task> dLineList;
	private ArrayList<Task> interList;
	private ArrayList<Task> floatList;
	
	Task deadLineTaskExpected;
	Task intervalTaskExpected;
	Task floatingTaskExpected;
	
	@Test
	public void testReadProjectData() throws IOException, ParseException{
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
		
		
		Writer w = new Writer("CS2103", dLineList, interList, floatList);
		
		ArrayList<String> projectNames = new ArrayList<String>();
		projectNames.add("CS2103");
		
		ArrayList<Project> projectsList = new ArrayList<Project>();
		ArrayList<Task> testList = new ArrayList<Task>();
		
		testList.addAll(dLineList);
		testList.addAll(floatList);
		testList.addAll(interList);
		
		projectsList.add(new Project("CS2103", testList));
		
		Reader r = new Reader(projectNames, projectsList);
		r.readProjectData();
		
		assertEquals(r.getProjectNames(), projectNames);
		assertEquals(r.getProjectList(), projectsList);
	}
}
