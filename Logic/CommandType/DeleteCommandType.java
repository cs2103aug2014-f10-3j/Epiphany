package CommandType;

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
