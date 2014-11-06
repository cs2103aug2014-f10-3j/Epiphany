package Logic.Interpreter.CommandType;

public class CompleteCommandType implements CommandType{
	private String projectName;
	private String taskDescription;
	
	public CompleteCommandType(String _taskDescription) {
		taskDescription = _taskDescription;
		projectName = "default";
	}
	
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
