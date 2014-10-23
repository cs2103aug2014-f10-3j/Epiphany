package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


public class OnlyDateMatcherWithTime implements Matcher{
	
	private final Pattern datewletters = Pattern.compile("(the |)(\\d\\d|\\d)(st|nd|rd|th)( | from )\\d+:\\d\\d");
	private final Pattern datewoletters = Pattern.compile("(the |)(\\d\\d|\\d)( | from )\\d+:\\d\\d");
	private final Pattern extractDatePattern = Pattern.compile("\\d+");
	
	@Override
	public Date tryConvert(String input) {

		if (datewoletters.matcher(input).matches() || datewletters.matcher(input).matches()) {

			java.util.regex.Matcher m = extractDatePattern.matcher(input);
			//m.matches();
			if(m.find()){
				Calendar cal = Calendar.getInstance();
				int date = Integer.parseInt(m.group(0));
				if(date >= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
					// in within same month.
					cal.set(Calendar.DAY_OF_MONTH, date);
				} else{
					cal.set(Calendar.DAY_OF_MONTH, date);
					cal.add(Calendar.MONTH, 1);
				}

	            int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
	            int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
	            cal.set(Calendar.HOUR_OF_DAY, hour);
	            cal.set(Calendar.MINUTE, minute);
				return cal.getTime();
			}
		}

		return null;
	}
}
