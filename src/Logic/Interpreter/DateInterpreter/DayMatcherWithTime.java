package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class DayMatcherWithTime implements Matcher {

	private final Pattern saturday = Pattern.compile("(this |)(saturday|sat)( | from )\\d+:\\d\\d");
	private final Pattern sunday = Pattern.compile("(this |)(sunday|sun)( | from )\\d+:\\d\\d");
	private final Pattern monday = Pattern.compile("(this |)(monday|mon)( | from )\\d+:\\d\\d");
	private final Pattern tuesday = Pattern.compile("(this |)(tuesday|tue|tues)( | from )\\d+:\\d\\d");
	private final Pattern wednesday = Pattern.compile("(this |)(wednesday|wed)( | from )\\d+:\\d\\d");
	private final Pattern thursday = Pattern.compile("(this |)(thursday|thur)( | from )\\d+:\\d\\d");
	private final Pattern friday = Pattern.compile("(this |)(friday|fri)( | from )\\d+:\\d\\d");

	public Date tryConvert(String input) {

		if (saturday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.SATURDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (sunday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.SUNDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 1) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (monday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.MONDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 2) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (tuesday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.TUESDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 3) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (wednesday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.WEDNESDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 4) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (thursday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.THURSDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 5) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		if (friday.matcher(input).matches()) {
			Calendar now = Calendar.getInstance();  
			int weekday = now.get(Calendar.DAY_OF_WEEK);  
			if (weekday != Calendar.FRIDAY)  
			{  
				// calculate how much to add  
				// the 2 is the difference between Saturday and Monday  
				int days = (Calendar.SATURDAY - weekday + 6) % 7;  
				now.add(Calendar.DAY_OF_YEAR, days);  
			}  
			int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
			int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
			now.set(Calendar.HOUR_OF_DAY, hour);
			now.set(Calendar.MINUTE, minute);
			// now is the date you want  
			return now.getTime();
		}

		return null;
	}
}
