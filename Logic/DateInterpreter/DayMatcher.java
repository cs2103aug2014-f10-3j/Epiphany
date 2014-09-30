import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class DayMatcher implements strtotime.Matcher {

	private final Pattern saturday = Pattern.compile(".*saturday");
	private final Pattern sunday = Pattern.compile(".*sunday");
	private final Pattern monday = Pattern.compile(".*monday");
	private final Pattern tuesday = Pattern.compile(".*tuesday");
	private final Pattern wednesday = Pattern.compile(".*wednesday");
	private final Pattern thursday = Pattern.compile(".*thursday");
	private final Pattern friday = Pattern.compile(".*friday");

	@Override
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
			// now is the date you want  
			return now.getTime();
		}

		return null;
	}
}
