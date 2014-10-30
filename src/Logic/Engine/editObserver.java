package Logic.Engine;

import Logic.Exceptions.CancelEditException;
import Logic.Interpreter.CommandType.CommandType;

public interface editObserver {
	int askForAdditionalInformationForEdit() throws CancelEditException;
	CommandType askForNewTaskForEdit() throws CancelEditException;
}
