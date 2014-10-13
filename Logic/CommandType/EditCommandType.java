
package Logic.CommandType;

/**
 * This is a class that can be instantiated to represent an edit command.
 * 
 * @author abdulla contractor and amit gamane
 */

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
