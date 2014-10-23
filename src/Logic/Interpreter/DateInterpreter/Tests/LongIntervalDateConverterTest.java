package Logic.Interpreter.DateInterpreter.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.DateInterpreter.strtotime;

public class LongIntervalDateConverterTest {
	private static final String COMMAND_PREFIX_FROM = "Complete something from ";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void DateAndMonthMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"26th July 9:30 to 28th July 11:30", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(28, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(11, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		try {
			strtotime.convert(COMMAND_PREFIX_FROM+"26 July from 9:30 to 1130", dates);
		} catch (InvalidCommandException e){
			assertEquals(0, dates.size());
		}
	}

	@Test
	public void SoonMatcherTest() throws InvalidCommandException {
		ArrayList<Date> dates = new ArrayList<Date>();
		thrown.expect(InvalidCommandException.class);
		strtotime.convert(COMMAND_PREFIX_FROM+"today 9:30 to tomorrow 11:30",dates);
	}

	@Test
	public void SingleDateTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"the 1st 9:30 to 28th 10:30",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH)+1, cal.get(Calendar.MONTH));
		assertEquals(9, cal.get(Calendar.HOUR));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(28, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH)+1, cal.get(Calendar.MONTH));
		assertEquals(10, cal.get(Calendar.HOUR));
		assertEquals(30, cal.get(Calendar.MINUTE));
	}

	@Test
	public void DateFormatThreeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"26/9/2014 9:30 to 3/10/2014 13:30",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(8, cal.get(Calendar.MONTH));
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, cal.get(Calendar.MONTH));
		assertEquals(2014, cal.get(Calendar.YEAR));
		assertEquals(13, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
	}

	@Test
	public void LongIntervalDateConverterFail() throws InvalidCommandException {
		ArrayList<Date> dates = new ArrayList<Date>();
		try {
			strtotime.convert(COMMAND_PREFIX_FROM+"26 July from 9:30 to 1130", dates);
		} catch (InvalidCommandException e){
			assertEquals(0, dates.size());
		}
	}


}
