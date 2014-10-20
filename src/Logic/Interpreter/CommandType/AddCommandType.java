package Logic.Interpreter.CommandType;

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
	private Date dateFrom;
	private Date dateTo;
	private String projectName;
	
	public AddCommandType(String _description) {
		description = _description;
		dateFrom = null;
		dateTo = null;
		projectName = "default";
	}
	
	public AddCommandType(String _description, Date _dateTo) {
		description = _description;
		dateFrom = null;
		dateTo = _dateTo;
		projectName = "default";
	}
	
	public AddCommandType(String _description, Date _dateTo, String _projectName) {
		description = _description;
		dateFrom = null;
		dateTo = _dateTo;
		projectName = _projectName;
	}
	
	public AddCommandType(String _description, Date _dateFrom, Date _dateTo, String _projectName) {
		description = _description;
		dateFrom = _dateFrom;
		dateTo = _dateTo;
		projectName = _projectName;
	}
	
	public String getType() {
		return "add";
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDateTo() {
		return dateTo;
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}
	
	public String getProjectName() {
		return projectName;
	}

}
