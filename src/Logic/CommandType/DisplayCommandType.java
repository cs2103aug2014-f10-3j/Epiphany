//@author A0119264E
package Logic.CommandType;

/**
 * This is a class that can be instantiated to represent a display command. It can
 * support all types of display commands (all tasks, specific projects) Each type of display command
 * has a dedicated constructor.
*/

public class DisplayCommandType implements CommandType{

	private String modifiers;
	
	public DisplayCommandType() {
		modifiers = "all";
	}
	
	public DisplayCommandType(String _modifiers) {
		modifiers = _modifiers;
	}
	
	public String getType() {
		return "display";
	}
	
	public String getModifiers() {
		return modifiers;
	}
	
}
