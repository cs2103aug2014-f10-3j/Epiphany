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