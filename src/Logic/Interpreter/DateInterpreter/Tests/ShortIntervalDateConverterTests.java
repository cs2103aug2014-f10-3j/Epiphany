//@author A0118905A
package Logic.Interpreter.DateInterpreter.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.DateInterpreter.strtotime;

public class ShortIntervalDateConverterTests {

	private static final String COMMAND_PREFIX_ON = "Complete something on ";
	private static final String COMMAND_PREFIX_NONE = "Complete something ";
	
	@Test
	public void DateAndMonthMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_ON+"26th July from 9:30 to 11:30", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(11, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		strtotime.convert(COMMAND_PREFIX_ON+"26 July from 9:30 to 11:30", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(11, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		strtotime.convert(COMMAND_PREFIX_ON+"26 July", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(0, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(23, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(59, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void SoonMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_NONE+"today from 9:30 to 15:30",dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		strtotime.convert(COMMAND_PREFIX_NONE+"tomorrow from 10:30 to 13:00",dates);
		now.add(Calendar.DAY_OF_MONTH, 1);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(10, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals(13, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void SingleDateTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_ON+"the 30th from 10:30 to 13:00",dates);
		cal.setTime(dates.get(0));
		assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(10, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(13, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(00, cal.get(Calendar.MINUTE));
		strtotime.convert(COMMAND_PREFIX_ON+"the 1st from 10:30 to 13:00",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH)+1, cal.get(Calendar.MONTH));
		assertEquals(10, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH)+1, cal.get(Calendar.MONTH));
		assertEquals(13, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(00, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void DateFormatThreeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_ON+"26/7/2020 from 9:30 to 11:30",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
		cal.setTime(dates.get(1));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		assertEquals(11, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void DeadlineDateInterpreterFail() throws InvalidCommandException {
		ArrayList<Date> dates = new ArrayList<Date>();
		try {
		strtotime.convert(COMMAND_PREFIX_ON+"26 July from 9:30 to 1130", dates);
		} catch (InvalidCommandException e){
			assertEquals(0, dates.size());
		}
	}
}
