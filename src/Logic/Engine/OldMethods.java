
  /******************************* OLDER METHODS *****************************************/


  
  private boolean checkContains(int i, String projectName) {
    if (projectsList.get(i).getProjectName().equals(projectName)) {
      return true;
    } else {
      return false;
    }
  }

  
  
  /**
   * This function helps to search through a project to return all the Task's
   * that positively match the given phrase.
   * 
   * @param phrase
   *            Is a phrase that the user intends to search for
   * @param projectName
   *            is the name of the project that the user specifies
   * @return an ArrayList of the Tasks that matched the search phrase.
   */
  private ArrayList<Task> search(String phrase, String projectName) {
    ArrayList<Task> searchResult = new ArrayList<Task>();
    ArrayList<Task> temp;

    for (int i = 0; i < projectsList.size(); i++) {
      if (checkContains(i, projectName)) {
        temp = projectsList.get(i).getTaskList();
        for (int j = 0; j < temp.size(); j++) {
          if (temp.get(j).getInstruction().toLowerCase()
              .contains(phrase.toLowerCase())) {
            searchResult.add(temp.get(j));
          } else {
            continue;
          }
        }
        if (searchResult.isEmpty()) {
          System.out.println(MESSAGE_INVALID_SEARCH);

        }
        System.out.println("Search result:"); // UI handler
        displayArrayList(searchResult);
      }
    }
    return searchResult;
  }

  /**
   * A function that helps to display all the projects including all the tasks
   * stored within them.
   */
  public void displayAll() {
    if (projectsList.size() == 1
        && projectsList.get(0).getTaskList().isEmpty()) {
      System.out.println("Nothing to display.");// UI handler
    } else {
      for (int i = 0; i < projectsList.size(); i++) {
        Project curr = projectsList.get(i);
        System.out.println("Project: " + "\n" + (i + 1) + ". "
            + curr.getProjectName() + ":");
        ArrayList<Task> currentArrayList = curr.getTaskList();

        if (currentArrayList.isEmpty()) {
          System.out.println("IT IS EMPTY"); // UI handler
        } else {
          for (int k = 0; k < currentArrayList.size(); k++) {
            Task currTask = currentArrayList.get(k);
            // should i try a try-catch block

            Date currDate = currTask.getDeadline();
            // Stem.out.println(currDate);
            System.out.println("\t" + (k + 1) + ". "
                + currTask.getInstruction() + "\t" + currDate);

          }
        }
      }
    }
  }

  
  /**
   * This function helps to output an ArrayList of tasks. It is currently
   * being used by the search function to print out the tasks that contain the
   * search phrase.
   * 
   * @param expected
   *            an ArrayList to be displayed to the user.
   */
  private void displayArrayList(ArrayList<Task> expected) {

    if (expected.isEmpty()) {
      System.out.println(String.format(MESSAGE_DISPLAY_ERROR));
    } else {

      int counter = 1;
      for (Task s : expected) {
        System.out.println(String.format(MESSAGE_DISPLAY, counter,
            s.getInstruction()));
        counter++;
      }
    }
  }

  /**
   * Displays all the tasks within any one of the projects.
   * 
   * @param name
   *            The name of the project that we wish to display.
   */
  public void displayProject(Project tempName) {
    for (int i = 0; i < projectsList.size(); i++) {
      String nameTempProject = tempName.getProjectName().toLowerCase();
      String abc = projectsList.get(i).getProjectName().toLowerCase();
      if (abc.equals(nameTempProject)) {
        ArrayList<Task> temporaryProjectList = projectsList.get(i)
            .getTaskList();
        displayArrayList(temporaryProjectList);
      } else {
        System.out.println("No such project exists."); // UI handler
      }
    }
  }

  /**
   * Displays the names and indices of all the projects that exist.
   */
  public void displayProjects() {
    int count = 1;
    if (projectNames.isEmpty()) {
      System.out.println("There are currently no projects.");
    } else {
      for (String s : projectNames) {
        System.out.println(count + ". " + s + ".");
        count++;
      }
    }
  }

  private void createNewProject(String projectName, String instruction,
      Date date) {
    projectNames.add(projectName);
    ArrayList<Task> latest = new ArrayList<Task>();
    if (date == null) {
      latest.add(new Task(instruction, projectName));
    } else if (date != null) {
      latest.add(new Task(instruction, date, projectName));
    }
    projectsList.add(new Project(projectName, latest));
    System.out.println("New project created");
    System.out.println("Task has been added!");
  }

  

  private void deleteTask(String instruction, String projectName) {
    if (!projectName.contains(projectName)) {
      System.out.println("Such a project does not exist");
    } else {
      for (int i = 0; i < projectsList.size(); i++) {
        String current = projectsList.get(i).getProjectName()
            .toLowerCase();
        if (current.contains(projectName.toLowerCase())) {
          ArrayList<Task> currTaskList = projectsList.get(i)
              .getTaskList();

          for (int j = 0; j < currTaskList.size(); j++) {
            if (currTaskList.contains(instruction)) {
              currTaskList.remove(j);
              System.out.print("Removed successfully");
            }
          }
        }

      }

    }
  }

  /**
   * Finds the project are removes it entirely.
   * 
   * @param projectName
   *            is the name of the project.
   */
  public void deleteProject(ArrayList<Task> projectName) {
    if (projectsList.contains(projectName)
        && projectNames.contains(projectName)) {
      int count = projectsList.lastIndexOf(projectName);
      int count1 = projectNames.lastIndexOf(projectName);
      projectsList.remove(count);
      projectNames.remove(count1);
    }
  }
}


*/
