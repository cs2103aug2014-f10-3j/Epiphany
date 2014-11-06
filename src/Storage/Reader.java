package Storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import Logic.Engine.Project;
import Logic.Engine.Task;

/**
 * This class belongs to the Storage component of the overall architecture.
 * Responsible for writing tasks to txt file.
 * 
 * @author amit
 *
 */
public class Reader {
	/***************** Attributes ***********************/
	private ArrayList<String> projectNames;
	private ArrayList<Project> projectsList;

	/***************** Constructor ***********************/

	public Reader(ArrayList<String> projectNames, ArrayList<Project> projectsList) {
		this.projectNames = projectNames;
		this.projectsList = projectsList;
	}

	/***************** Methods ***********************/

	public void readProjectData() throws IOException, ParseException {
		for (String fileName : projectNames) {
			ArrayList<Task> temp = new ArrayList<Task>();

			File f = new File("../Epiphany/src/Storage/Projects/"
					+ fileName);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] taskComponents = line.split("~");

				String type = taskComponents[0];
				String description = taskComponents[1];
				Date from = parseDate(taskComponents[2]);
				Date to = parseDate(taskComponents[3]);
				String projName = taskComponents[4];
				// 5 reserved for completion status
//				int UID = Integer.parseInt(taskComponents[6]);

				Task t = null;

				if (type.equals("deadline")) {
					t = new Task(description, null, to, projName);
				} else if (type.equals("interval")) {
					t = new Task(description, from, to, projName);
				} else if (type.equals("floating")) {
					t = new Task(description, null, null, projName);
				}

				temp.add(t);
			}

			reader.close();

			Project p = new Project(fileName, temp);
			projectsList.add(p);
		}

	}

	public void readProjectTitles() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("../Epiphany/src/Storage/projectMasterList.txt"));
		while (sc.hasNextLine()) {
			projectNames.add(sc.nextLine());

		}
		sc.close();
	}
	private static Date parseDate(String input) throws ParseException {
		Date date = new Date();

		if (!input.equals("null")) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy");
			date = sdf.parse(input);
		} else {
			return null;
		}

		return date;
	}

}
