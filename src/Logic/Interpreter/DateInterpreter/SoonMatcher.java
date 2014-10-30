package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class SoonMatcher implements Matcher {

    private final Pattern tomorrow = Pattern.compile(".*tomorrow.*");
    private final Pattern today = Pattern.compile(".*(today|tonight).*");

    public Date tryConvert(String input) {
        if (tomorrow.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            return calendar.getTime();
        }
        if (today.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        } else {
            return null;
        }
    }
}
