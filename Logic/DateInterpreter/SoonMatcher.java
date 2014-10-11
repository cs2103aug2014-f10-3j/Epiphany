package Logic.DateInterpreter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class SoonMatcher implements strtotime.Matcher {

    private final Pattern tomorrow = Pattern.compile("\\W*tomorrow\\W*");
    private final Pattern today = Pattern.compile("\\W*today\\W*");
    private final Pattern tonight = Pattern.compile("\\W*tonight\\W*");

    @Override
    public Date tryConvert(String input) {
        if (tomorrow.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            return calendar.getTime();
        }
        if (today.matcher(input).matches() || tonight.matcher(input).matches()) {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        } else {
            return null;
        }
    }
}
