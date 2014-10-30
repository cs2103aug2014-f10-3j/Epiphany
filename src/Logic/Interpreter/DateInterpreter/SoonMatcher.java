package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import Logic.Exceptions.InvalidCommandException;

class SoonMatcher implements Matcher {

    private final Pattern tomorrow = Pattern.compile(".*tomorrow.*");
    private final Pattern today = Pattern.compile(".*(today|tonight).*");
    private final Pattern yesterday = Pattern.compile(".*(yesterday).*");

    public Date tryConvert(String input) throws InvalidCommandException {
        if (tomorrow.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            return calendar.getTime();
        }
        if (today.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        }
        if (yesterday.matcher(input).matches()) {
            throw new InvalidCommandException();
        }
        else {
            return null;
        }
    }
}
