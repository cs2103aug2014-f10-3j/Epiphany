//@author A0118905A
package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


public class OnlyDateMatcher implements Matcher{
	
	private final Pattern datewletters = Pattern.compile("(the |)(\\d\\d|\\d)(st|nd|rd|th)");
	private final Pattern datewoletters = Pattern.compile("(the |)(\\d\\d|\\d)");
	private final Pattern extractDatePattern = Pattern.compile("\\d+");
	
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
				return cal.getTime();
			}
		}

		return null;
	}
}
