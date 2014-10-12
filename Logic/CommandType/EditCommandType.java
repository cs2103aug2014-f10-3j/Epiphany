package Logic.CommandType;

public class EditCommandType {
	String originalTask;
	String newTask;
	
	public EditCommandType(String _originalTask, String _newTask) {
		originalTask = _originalTask;
		newTask = _newTask;
	}
	
	public String getOriginalTask(){
		return originalTask; 
	}
	
	public String getNewTask(){
		return newTask; 
	}
}
