package Logic.CommandType;

import java.util.Date;


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
