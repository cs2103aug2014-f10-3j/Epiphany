package CommandType;

public class SearchCommandType implements CommandType{

	private String taskDescription;
	private String projectName;
	
	public SearchCommandType(String _taskDescription) {
		taskDescription = _taskDescription; //search everything.
		projectName = "";
	}
	
	public SearchCommandType(String _taskDescription, String _projectName) {
		taskDescription = _taskDescription; 
		projectName = _projectName;
	}
	
	@Override
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
