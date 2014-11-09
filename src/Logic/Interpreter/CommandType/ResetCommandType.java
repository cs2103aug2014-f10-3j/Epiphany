//@author A0118905A
package Logic.Interpreter.CommandType;
/**
 * This is a class that can be instantiated to represent an reset command. 
 */
public class ResetCommandType implements CommandType{
	public ResetCommandType(){

	}

	public String getType() {
		return "reset";
	}
}
