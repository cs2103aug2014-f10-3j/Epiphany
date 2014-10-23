package Logic.Interpreter.DateInterpreter.Tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Logic.Interpreter.DateInterpreter.DeadlineDateConverter;
import Logic.Interpreter.DateInterpreter.LongIntervalDateConverter;
import Logic.Interpreter.DateInterpreter.ShortIntervalDateConverter;
import Logic.Interpreter.DateInterpreter.strtotime;

public class DateInterpreterExceptionTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void attemptToInstantiatestrtotime() throws UnsupportedOperationException{
		thrown.expect(UnsupportedOperationException.class);
	    // act
		strtotime conv = new strtotime();
	}
	
	@Test
	public void attemptToInstantiateDeadlineDateConverter() throws UnsupportedOperationException{
		thrown.expect(UnsupportedOperationException.class);
	    // act
	    DeadlineDateConverter conv = new DeadlineDateConverter();
	}
	
	@Test
	public void attemptToInstantiateShortIntervalDateConverter() throws UnsupportedOperationException{
		thrown.expect(UnsupportedOperationException.class);
	    // act
		ShortIntervalDateConverter conv = new ShortIntervalDateConverter();
	}
	
	@Test
	public void attemptToInstantiateLongIntervalDateConverter() throws UnsupportedOperationException{
		thrown.expect(UnsupportedOperationException.class);
	    // act
		LongIntervalDateConverter conv = new LongIntervalDateConverter();
	}

}
