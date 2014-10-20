package Logic.Interpreter.DateInterpreter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

class DateFormatMatcherThree implements Matcher {

    private final DateFormat dateFormat;

    public DateFormatMatcherThree(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date tryConvert(String input) {
        try {
            return dateFormat.parse(input);
        } catch (ParseException ex) {
            return null;
        }
    }
}