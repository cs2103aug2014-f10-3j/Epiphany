package Logic.Interpreter.DateInterpreter.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import Logic.Exceptions.InvalidCommandException;
import Logic.Interpreter.DateInterpreter.strtotime;

public class CalculateCorrectDateTests {
	private static final String COMMAND_PREFIX_BY = "Complete something by ";

	@Test
	public void nextSaturdayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.SATURDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday) % 7;
			now.add(Calendar.DAY_OF_YEAR, days);  
		}   
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next saturday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextSundayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.SUNDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 1) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}   
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next sunday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextMondayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.MONDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 2) % 7;
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next monday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextTuesdayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.TUESDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 3) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next tuesday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextWednesdayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.WEDNESDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 4) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next wednesday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextThursdayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.THURSDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 5) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next thursday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void nextFridayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next friday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void followingFridayTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 14); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"the following friday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void toFailTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		try{
		strtotime.convert(COMMAND_PREFIX_BY+"next frday", dates);
		assert(false);
		} catch(InvalidCommandException e){
			assert(true);
		}
	}
	
	@Test
	public void nextSaturdayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.SATURDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday) % 7;
			now.add(Calendar.DAY_OF_YEAR, days);  
		}   
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next saturday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextSundayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.SUNDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 1) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}   
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next sunday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextMondayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.MONDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 2) % 7;
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next monday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextTuesdayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.TUESDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 3) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next tuesday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextWednesdayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.WEDNESDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 4) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next wednesday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextThursdayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.THURSDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 5) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next thursday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void nextFridayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"next friday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	@Test
	public void followingFridayWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}  
		now.add(Calendar.DAY_OF_YEAR, 14); 
		// now is the date you want
		
		strtotime.convert(COMMAND_PREFIX_BY+"the following friday 14:20", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
		assertEquals(20, cal.get(Calendar.MINUTE));
	}
	
	
	
	@Test
	public void toFailWithTimeTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		try{
		strtotime.convert(COMMAND_PREFIX_BY+"next frday 14:20", dates);
		assert(false);
		} catch(InvalidCommandException e){
			assert(true);
		}
	}
	
	@Test
	public void noPrefixThisTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}
		// now is the date you want
		
		strtotime.convert("do something this friday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}
	
	@Test
	public void noPrefixNextTest() throws InvalidCommandException {
		Calendar cal = Calendar.getInstance();
		ArrayList<Date> dates = new ArrayList<Date>();
		
		Calendar now = Calendar.getInstance();  
		int weekday = now.get(Calendar.DAY_OF_WEEK);  
		if (weekday != Calendar.FRIDAY)  
		{  
			// calculate how much to add  
			// the 2 is the difference between Saturday and Monday  
			int days = (Calendar.SATURDAY - weekday + 6) % 7; 
			now.add(Calendar.DAY_OF_YEAR, days);  
		}
		now.add(Calendar.DAY_OF_YEAR, 7); 
		// now is the date you want
		
		strtotime.convert("do something next friday", dates);
		cal.setTime(dates.get(0));
		assertEquals(now.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_MONTH));
		assertEquals(now.get(Calendar.MONTH), cal.get(Calendar.MONTH));
	}

}
