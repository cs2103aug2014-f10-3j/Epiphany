//@author A0118905A
package Logic.CommandType;

import java.util.Date;

/**
 * This is a class that can be instantiated to represent an add command. It can
 * support all types of add commands (with date and project, without date with project,
 * with date without project and without date without project.) Each type of add command
 * has a dedicated constructor.
 */

public class AddCommandType implements CommandType {
	
	private String description;
	private Date dateFrom;
	private Date dateTo;
	private String projectName;
	
	/**
	 * Command type to create a floating task without a project.
	 * @param _description
	 */
	public AddCommandType(String _description) {
		description = _description;
		dateFrom = null;
		dateTo = null;
		projectName = "default";
	}
	
	/**
	 * Command type to create a deadline task without a project.
	 * @param _description
	 * @param _dateTo
	 */
	public AddCommandType(String _description, Date _dateTo) {
		description = _description;
		dateFrom = null;
		dateTo = _dateTo;
		projectName = "default";
	}
	
	/**
	 * Command type to create a deadline task is a particular project.
	 * @param _description
	 * @param _dateTo
	 * @param _projectName
	 */
	public AddCommandType(String _description, Date _dateTo, String _projectName) {
		description = _description;
		dateFrom = null;
		dateTo = _dateTo;
		projectName = _projectName;
	}

	/**
	 * Command type to create a interval task without a project.
	 * @param _description
	 * @param _dateFrom
	 * @param _dateTo
	 */
	public AddCommandType(String _description, Date _dateFrom, Date _dateTo) {
		description = _description;
		dateFrom = _dateFrom;
		dateTo = _dateTo;
		projectName = "default";
	}
	
	/**
	 * Command type to create a interval task in a particular project.
	 * @param _description
	 * @param _dateFrom
	 * @param _dateTo
	 * @param _projectName
	 */
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
