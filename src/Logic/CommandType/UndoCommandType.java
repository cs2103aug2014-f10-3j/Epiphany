//@author A0119264E
package Logic.CommandType;

/**
 * This is a class that can be instantiated to represent an undo command. 
 * 
 */

public class UndoCommandType implements CommandType{

	public UndoCommandType(){
		
	}
	
	public String getType() {
		return "undo";
	}
}
