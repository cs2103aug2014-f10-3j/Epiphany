package Logic.Engine;

import Logic.Exceptions.CancelDeleteException;

public interface deleteObserver {
	int askForAdditionalInformation() throws CancelDeleteException;
}
