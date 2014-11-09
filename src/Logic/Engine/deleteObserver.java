package Logic.Engine;

import java.io.IOException;

import Logic.Exceptions.CancelDeleteException;

public interface deleteObserver {
	int[] askForAdditionalInformationForDelete() throws CancelDeleteException, IOException;
}
