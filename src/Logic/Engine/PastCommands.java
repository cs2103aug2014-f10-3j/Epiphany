//@author A0119264E
package Logic.Engine;

/**
 * This class exists to denote information about a past command.
 * PastCommands is generally used by the undo and redo functions in Engine to
 * keep track of commands executed by the user.
 *
 */
public class PastCommands {
	
/*************Attributes*************/	
	private String type;
	private Task task;
	private Project project;
	private String projectName;
	
	
/*************Constructors*************/
	public PastCommands(String type, Task t, String projectName){
		this.type = type;
		this.task = t;
		this.projectName = projectName;
	}
	
	public PastCommands(String type, Project project){
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
		
}
