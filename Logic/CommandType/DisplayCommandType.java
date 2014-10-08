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
