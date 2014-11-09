//@author A0119264E
package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an edit command.
 */

public class EditCommandType implements CommandType{
	
	private String projectName;
	private String taskDescription;

	/**
	 * edit a task without a project
	 * @param _taskDescription
	 */
	public EditCommandType(String _taskDescription) {
		taskDescription = _taskDescription;
		projectName = "default";
	}
	
	/**
	 * edit a task with a project
	 * @param _taskDescription
	 */
	public EditCommandType(String _taskDescription, String _projectName) {
		taskDescription = _taskDescription;
		projectName = _projectName;
	}
	
	public String getType() {
		return "edit";
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getTaskDescription(){
		return taskDescription;
	}
}
