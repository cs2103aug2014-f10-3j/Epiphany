/**
 * This is a class that can be instantiated to represent a display command. It can
 * support all types of add commands (all tasks, specific projects) Each type of add command
 * has a dedicated constructor.
 * 
 * @author abdulla contractor and amit gamane
*/
package Logic.CommandType;

public class DisplayCommandType implements CommandType{

	private String modifiers;
	
	public DisplayCommandType() {
		modifiers = "all";
	}
	
	public DisplayCommandType(String _modifiers) {
		modifiers = _modifiers;
	}
	
	@Override
	public String getType() {
		return "display";
	}
	
	public String getModifiers() {
		return modifiers;
	}
	
}
