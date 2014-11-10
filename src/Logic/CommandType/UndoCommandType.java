package Logic.CommandType;

/**
 * This is a class that can be instantiated to represent an undo command. 
 * 
 * @author amit
 * @author moazzam
 */

public class UndoCommandType implements CommandType{

	public UndoCommandType(){
		
	}
	
	public String getType() {
		return "undo";
	}
}
