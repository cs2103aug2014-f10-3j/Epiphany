package Logic.Engine;

import Logic.Exceptions.CancelDeleteException;

public interface deleteObserver {
	int[] askForAdditionalInformationForDelete() throws CancelDeleteException;
}
