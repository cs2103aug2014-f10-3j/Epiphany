package Logic.Interpreter.DateInterpreter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import Logic.Exceptions.*;

public class DateInperpreterTest {
	
	private static final String COMMAND_PREFIX_BY = "Complete something by ";
	//private static final String COMMAND_PREFIX_BY = "";
	//private static final String COMMAND_PREFIX_ON = "";
	//private static final String COMMAND_PREFIX_IN = "";
	
	@Test
	public void DateAndMonthMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_BY+"26th July", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"26 July", dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
	}
	
	@Test
	public void SoonMatcherTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_BY+"today",dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(now.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_BY+"tomorrow",dates);
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
		strtotime.convert(COMMAND_PREFIX_BY+"the 30th",dates);
		cal.setTime(dates.get(0));
		assertEquals(30, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void DateFormatThreeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_BY+"26/7/2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_BY+"26.7.2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_BY+"26-7-2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
		strtotime.convert(COMMAND_PREFIX_BY+"26 7 2020",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		assertEquals(2020, cal.get(Calendar.YEAR));
	}
	
	@Test
	public void DateFormatTwoTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		strtotime.convert(COMMAND_PREFIX_BY+"26/7",dates);
		cal.setTime(dates.get(0));
		assertEquals(26, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"1st july",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"2nd july",dates);
		cal.setTime(dates.get(0));
		assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"3rd july",dates);
		cal.setTime(dates.get(0));
		assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"4th july",dates);
		cal.setTime(dates.get(0));
		assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"july 1st",dates);
		cal.setTime(dates.get(0));
		assertEquals(1, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"july 2nd",dates);
		cal.setTime(dates.get(0));
		assertEquals(2, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"july 3rd",dates);
		cal.setTime(dates.get(0));
		assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
		strtotime.convert(COMMAND_PREFIX_BY+"july 4th",dates);
		cal.setTime(dates.get(0));
		assertEquals(4, cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, cal.get(Calendar.MONTH));
	}
}
