package Logic.Engine;


/**
 * This class exits to denote information about a past command.
 * @author amit
 *
 */
public class PastCommands {
	
/*************Attributes*************/	
	String type;
	Task task;
	
/*************Constructors*************/
	public PastCommands(String type, Task t){
		this.type = type;
		this.task = t;
	}
	
/*************Getters*************/
	public String getType(){
		return this.type;
	}
	
	public Task getTask(){
		return this.task;
	}

	
}
