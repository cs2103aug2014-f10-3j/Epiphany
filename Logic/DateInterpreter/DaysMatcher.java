import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

class DaysMatcher implements strtotime.Matcher {

    private final Pattern days = Pattern.compile("[\\-\\+]?\\d+ days");

    @Override
    public Date tryConvert(String input) {

        if (days.matcher(input).matches()) {
            int d = Integer.parseInt(input.split(" ")[0]);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, d);
            return calendar.getTime();
        }

        return null;
    }
}
