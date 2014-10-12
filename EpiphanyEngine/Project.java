<<<<<<< HEAD
package EpiphanyEngine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
=======
//Project Class that handles tasks.
>>>>>>> FETCH_HEAD

class Project {
		private String projectName;
		private ArrayList<Task> items;

		// Constructor
		public Project(String name, ArrayList<Task> items) {
			this.setProjectName(name);
			// this.items = items;
			try {
				createNewFile(projectName, items);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		/**
		 * Creates a new text file to store the new project file
		 * 
		 * @param fileName
		 *            is the name of the file/project
		 * @param items
		 *            is the ArrayList of items that is inside this project
		 * @throws IOException
		 */
		public void createNewFile(String fileName, ArrayList<Task> items)
				throws IOException {

			FileWriter f = new FileWriter(fileName);
			BufferedWriter writer = new BufferedWriter(f);

			int counter = 1;

			for (Task s : items) {
				writer.write(counter + ". " + s.getInstruction());// or
				// s.instruction
				counter++;
				writer.newLine();
				writer.flush();
			}
			writer.close();
		}


		public String getProjectName() {
			return projectName;
		}

		public ArrayList<Task> getTaskList() {
			return items;
		}

		// This function would allow us to change the name of a project
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
	}