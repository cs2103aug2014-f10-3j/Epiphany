package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import Logic.Interpreter.DateInterpreter.Matcher;

class ExtendedDayMatcherWithTime implements Matcher {

	private final Pattern next = Pattern.compile("(next|the coming|coming).*");
	private final Pattern following = Pattern.compile("(following|the following).*");


	public Date tryConvert(String input) {

		if (next.matcher(input).matches()) {
			String extractedDay = input.split("(next|the coming|coming) ")[1];
			Matcher nextMatcher = new DayMatcherWithTime();
			Date date = nextMatcher.tryConvert(extractedDay);
            if (date != null) {
    			Calendar cal = Calendar.getInstance();
    			cal.setTime(date);
    			cal.add(Calendar.DATE, 7);
                return cal.getTime();
            }
		}
		
		if (following.matcher(input).matches()) {
			String extractedDay = input.split("(following|the following) ")[1];
			Matcher nextMatcher = new DayMatcherWithTime();
			Date date = nextMatcher.tryConvert(extractedDay);
            if (date != null) {
    			Calendar cal = Calendar.getInstance();
    			cal.setTime(date);
    			cal.add(Calendar.DATE, 14);
                return cal.getTime();
            }
		}
		return null;
	}
}
