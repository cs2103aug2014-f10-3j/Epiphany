package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class SoonMatcherWithTime implements Matcher {

    private final Pattern tomorrow = Pattern.compile("tomorrow( | from )\\d+:\\d\\d");
    private final Pattern today = Pattern.compile("(today|tonight)( | from )\\d+:\\d\\d");

    @Override
    public Date tryConvert(String input) {
        if (tomorrow.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
            int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            return calendar.getTime();
        }
        if (today.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            int hour = Integer.parseInt(input.substring(input.lastIndexOf(' ')+1, input.indexOf(':')));
            int minute = Integer.parseInt(input.substring(input.indexOf(':')+1));
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            return calendar.getTime();
        } else {
            return null;
        }
    }
}
