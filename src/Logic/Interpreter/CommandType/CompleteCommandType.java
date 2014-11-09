//@author A0118905A
package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an complete command. 
 */
public class CompleteCommandType implements CommandType{
	private String projectName;
	private String taskDescription;
	
	/**
	 * Complete a task without a project
	 * @param _taskDescription
	 */
	public CompleteCommandType(String _taskDescription) {
		taskDescription = _taskDescription;
		projectName = "default";
	}
	/**
	 * Complete a task with a project
	 * @param _taskDescription
	 */
	public CompleteCommandType(String _taskDescription, String _projectName) {
		taskDescription = _taskDescription;
		projectName = _projectName;
	}
	
	public String getType() {
		return "complete";
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getTaskDescription(){
		return taskDescription;
	}
}
