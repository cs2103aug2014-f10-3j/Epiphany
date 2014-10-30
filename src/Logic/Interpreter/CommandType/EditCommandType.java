
package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an edit command.
 * 
 * @author abdulla contractor and amit gamane
 */

public class EditCommandType implements CommandType{
	private String projectName;
	private String taskDescription;
	
	public EditCommandType(String _taskDescription) {
		taskDescription = _taskDescription;
		projectName = "default";
	}
	
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
