package Logic.Interpreter.DateInterpreter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

class DateFormatMatcherTwo implements Matcher {

    private final DateFormat dateFormat;

    public DateFormatMatcherTwo(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date tryConvert(String input) {
        try {
            Date d = dateFormat.parse(input);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            if(cal.before(Calendar.getInstance())){
            	cal.add(Calendar.YEAR, 1);
            }
            return cal.getTime();
            
        } catch (ParseException ex) {
            return null;
        }
    }
}