//@author A0118905A
package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an undo command. 
 */

public class RedoCommandType implements CommandType{

	public RedoCommandType(){
		
	}
	
	public String getType() {
		return "redo";
	}
}
