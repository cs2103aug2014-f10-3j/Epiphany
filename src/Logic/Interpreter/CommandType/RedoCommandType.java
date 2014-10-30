package Logic.Interpreter.CommandType;

/**
 * This is a class that can be instantiated to represent an undo command. 
 * 
 * @author abdulla
 */

public class RedoCommandType implements CommandType{

	public RedoCommandType(){
		
	}
	
	public String getType() {
		return "redo";
	}
}
