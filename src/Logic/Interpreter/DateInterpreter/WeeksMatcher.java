package Logic.Interpreter.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class WeeksMatcher implements Matcher {

    private final Pattern weeks = Pattern.compile("[\\-\\+]?\\d+ weeks");
    private final Pattern inAWeek = Pattern.compile("a week");

    @Override
    public Date tryConvert(String input) {

        if (weeks.matcher(input).matches()) {
            int w = Integer.parseInt(input.split(" ")[0]);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, w * 7);
            return calendar.getTime();
        }
        if (inAWeek.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            return calendar.getTime();
        }

        return null;
    }
}
