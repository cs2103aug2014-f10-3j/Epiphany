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
	Project project;
	
/*************Constructors*************/
	public PastCommands(String type, Task t){
		this.type = type;
		this.task = t;
	}
	
	public PastCommands(String type, Project project){
		// mainly for delete
		this.type = type;
		this.project = project;
	}
	
	
/*************Getters*************/
	public String getType(){
		return this.type;
	}
	
	public Task getTask(){
		return this.task;
	}
	
	public Project getProject(){
		return this.project;
	}
	
	public boolean isProjectOnly(){
		return (project == null) ? false : true;
	}

	
}
