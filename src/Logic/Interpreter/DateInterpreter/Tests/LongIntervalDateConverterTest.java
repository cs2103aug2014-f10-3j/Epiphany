package Logic.Interpreter.DateInterpreter.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.DateInterpreter.strtotime;

public class LongIntervalDateConverterTest {
private static final String COMMAND_PREFIX_FROM = "Complete something from ";
	
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
	
	/*@Test
	public void SoonMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"today",dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_FROM+"tomorrow",dates);
		cal.setTime(dates.get(0));
		now.add(Calendar.DAY_OF_MONTH, 1);
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
	}
	
	@Test
	public void SingleDateTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"the 30th",dates);
		cal.setTime(dates.get(0));
		assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"the 1st",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH)+1, cal.get(Calendar.MONTH));
	}
	
	@Test
	public void DateFormatThreeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"26/7/2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_FROM+"26.7.2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_FROM+"26-7-2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_FROM+"26 7 2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
	}
	
	@Test
	public void DateFormatTwoTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_FROM+"26/7",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"1st july",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"2nd july",dates);
		cal.setTime(dates.get(0));
		assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"3rd july",dates);
		cal.setTime(dates.get(0));
		assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"4th july",dates);
		cal.setTime(dates.get(0));
		assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"july 1st",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"july 2nd",dates);
		cal.setTime(dates.get(0));
		assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"july 3rd",dates);
		cal.setTime(dates.get(0));
		assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_FROM+"july 4th",dates);
		cal.setTime(dates.get(0));
		assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
	}*/
	
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
