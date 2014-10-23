package Logic.Interpreter.DateInterpreter.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.DateInterpreter.strtotime;

public class MiscDateInterpreterTests {

	@Test
	public void test() throws InvalidCommandException {
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert("visit gardens by the bay",dates);
		assertEquals(0, dates.size());
		strtotime.convert("Make sandcastle on the beach",dates);
		assertEquals(0, dates.size());
		strtotime.convert("Open gift from my mother",dates);
		assertEquals(0, dates.size());
	}

}
