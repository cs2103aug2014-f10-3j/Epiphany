package Logic.Engine;

import java.io.IOException;

import Logic.Exceptions.CancelEditException;
import Logic.CommandType.CommandType;

public interface editObserver {
	int askForAdditionalInformationForEdit() throws CancelEditException, IOException;
	CommandType askForNewTaskForEdit() throws CancelEditException, IOException;
}
