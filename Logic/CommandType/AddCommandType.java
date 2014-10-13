package Logic.CommandType;

import java.util.Date;

/**
 * This is a class that can be instantiated to represent an add command. It can
 * support all types of add commands (with date and project, without date with project,
 * with date without project and without date without project.) Each type of add command
 * has a dedicated constructor.
 * 
 * @author abdulla contractor and amit gamane
 */

public class AddCommandType implements CommandType {
	
	private String description;
	private Date date;
	private String projectName;
	
	public AddCommandType(String _description) {
		description = _description;
		date = null;
		projectName = "default";
	}
	
	public AddCommandType(String _description, Date _date) {
		description = _description;
		date = _date;
		projectName = "default";
	}
	
	public AddCommandType(String _description, Date _date, String _projectName) {
		description = _description;
		date = _date;
		projectName = _projectName;
	}
	
	@Override
	public String getType() {
		return "add";
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getProjectName() {
		return projectName;
	}

}
