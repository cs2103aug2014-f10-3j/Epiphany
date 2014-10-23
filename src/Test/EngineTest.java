package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Logic.Engine.Engine;
import Logic.Engine.Engine.CommandTypesEnum;
import Logic.Interpreter.CommandType.AddCommandType;
import Logic.Interpreter.CommandType.CommandType;
import Logic.Interpreter.CommandType.DeleteCommandType;
import Logic.Interpreter.CommandType.DisplayCommandType;
import Logic.Interpreter.CommandType.EditCommandType;
import Logic.Interpreter.CommandType.SearchCommandType;
import Logic.Engine.Task;
import Logic.Engine.Project;

public class EngineTest {
	
	enum CommandTypesEnum {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, EDIT
	}
	private static final String ERROR_COMMAND_TYPE_NULL = null;
	private static final String ERROR_WRONG_INPUT = null;;
	private CommandTypesEnum determineCommandType(CommandType commandType) {
		if (commandType == null)
			throw new Error(ERROR_COMMAND_TYPE_NULL);

		if (commandType.getType().equalsIgnoreCase("add")) {
			return CommandTypesEnum.ADD;
		} else if (commandType.getType().equalsIgnoreCase("display")) {
			return CommandTypesEnum.DISPLAY;
		} else if (commandType.getType().equalsIgnoreCase("delete")) {
			return CommandTypesEnum.DELETE;
		} else if (commandType.getType().equalsIgnoreCase("search")) {
			return CommandTypesEnum.SEARCH;
		} else {
			return null;
		}
	}

	public void executeCommand(CommandType userCommand) throws IOException {
		CommandTypesEnum commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD:// METHOD DONE
			AddCommandType addUserCommand = (AddCommandType) userCommand;
			addTask(addUserCommand.getDescription(),
					addUserCommand.getDateFrom(), addUserCommand.getDateTo(),
					addUserCommand.getProjectName());
			break;
		case DISPLAY:
			DisplayCommandType displayUserCommand = (DisplayCommandType) userCommand;
			display(displayUserCommand.getModifiers());
			break;
		case DELETE:
			DeleteCommandType deleteUserCommand = (DeleteCommandType) userCommand;
			deleteTask(deleteUserCommand.getTaskDescription(),
					deleteUserCommand.getProjectName());
			break;
		case SEARCH:
			SearchCommandType searchUserCommand = (SearchCommandType) userCommand;
			search(searchUserCommand.getTaskDescription(),
					searchUserCommand.getProjectName());
			break;

		case EDIT: // WY WORKING ON THIS
			EditCommandType editUserCommand = (EditCommandType) userCommand;
			edit(null, null);
			break;

		default:
			// throw an error if the command is not recognized
			throw new Error(ERROR_WRONG_INPUT);
		}
	}

	
	private void edit(Object object, Object object2) {
		// TODO Auto-generated method stub
		
	}

	private void search(String taskDescription, String projectName) {
		// TODO Auto-generated method stub
		
	}

	private void deleteTask(String taskDescription, String projectName) {
		// TODO Auto-generated method stub
		
	}

	private void display(String modifiers) {
		// TODO Auto-generated method stub
		
	}

	private void addTask(String description, Date dateFrom, Date dateTo,
			String projectName) {
		// TODO Auto-generated method stub
		
	}


	Engine e =  new Engine();
	
	
	
	
	@Test
	public void testAdd() throws IOException{
		
		AddCommandType abcd = new AddCommandType("Hello hello");
		e.executeCommand(abcd);
		addTask(abcd.getDescription(), null, null, null);
		
		AddCommandType cde = new AddCommandType("finish work by tomorrow");
		addTask(cde.getDescription(), null, null, null);
		
		ArrayList<Task> expected = new ArrayList<Task>();
		expected.add(new Task("hello hello", null,null,null, false));
		expected.add(new Task("finish work by tomorrow", null,null,null, false));
		e.projectsList.get(0).addTask();
		assertEquals(expected.get(0).getTaskDescription(), "hello hello" );
		
	}
}
