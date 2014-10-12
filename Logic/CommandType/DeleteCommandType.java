/**
 * This is a class that can be instantiated to represent a display command. It can
 * support all types of add commands (all tasks, specific projects) Each type of add command
 * has a dedicated constructor.
 * 
 * @author abdulla contractor and amit gamane
 */
package Logic.CommandType;

public class DeleteCommandType implements CommandType{
	
	private String projectName;
	private String taskDescription;
	
	public DeleteCommandType(String _taskDescription) {
		taskDescription = _taskDescription;
		projectName = "default";
	}
	
	public DeleteCommandType(String _taskDescription, String _projectName) {
		taskDescription = _taskDescription;
		projectName = _projectName;
	}
	
	@Override
	public String getType() {
		return "delete";
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public String getTaskDescription(){
		return taskDescription;
	}
}
