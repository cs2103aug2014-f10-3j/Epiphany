package Logic.Engine;


/**
 * This class exits to denote information about a past command.
 * @author amit
 *
 */
public class PastCommands {
	
/*************Attributes*************/	
	private String type;
	private Task task;
	private Project project;
	private String projectName;
	
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
	
	public PastCommands(String type, String projectName){
		this.type = type;
		this.projectName = projectName;
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
	
	public String getProjectName(){
		return this.projectName;
	}
	
	public boolean isProjectOnly(){
		return (project == null) ? false : true;
	}

	
}
