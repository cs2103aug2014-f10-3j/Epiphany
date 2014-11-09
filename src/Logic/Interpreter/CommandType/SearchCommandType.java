//@author A0119264E
package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an search command.
 */


public class SearchCommandType implements CommandType{

	private String taskDescription;
	private String projectName;
	
	/**
	 * Search across all projects.
	 * @param _taskDescription
	 */
	public SearchCommandType(String _taskDescription) {
		taskDescription = _taskDescription; //search everything.
		projectName = "";
	}
	
	/**
	 * Search in a specific project.
	 * @param _taskDescription
	 * @param _projectName
	 */
	public SearchCommandType(String _taskDescription, String _projectName) {
		taskDescription = _taskDescription; 
		projectName = _projectName;
	}
	
	public String getType() {
		return "search";
	}
	
	public String getTaskDescription(){
		return taskDescription;
	}
	
	public String getProjectName(){
		return projectName;
	}
}
