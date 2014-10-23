package Logic.Interpreter.DateInterpreter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShortIntervalDateConverter {
private static final List<Matcher> fromMatchers;
	
    static {
    	fromMatchers = new LinkedList<Matcher>();

        //WITH TIME SPECIFICATIONS
    	fromMatchers.add(new SoonMatcherWithTime());
        fromMatchers.add(new DayMatcherWithTime());
        //matchers.add(new ExtendedDayMatcherWithTime());
        fromMatchers.add(new OnlyDateMatcherWithTime());
        //Matchers which follow default java date formats
        fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd.MM.yyyy' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd/MM/yyyy' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd-MM-yyyy' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd MM yyyy' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd MMM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd/MM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'st' MMM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'nd' MMM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'rd' MMM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'th' MMM' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'st'' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'nd'' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'rd'' from 'HH:mm")));
        fromMatchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'th'' from 'HH:mm")));
        
    	
    }

    /*public static void registerMatcher(Matcher matcher) {
        fromMatchers.add(0, matcher);
    }*/


    public static void convert(String input, ArrayList<Date> d) {
    	d.clear();
    	String toInterpret = input;
        for (Matcher matcher : fromMatchers) {
            Date date = matcher.tryConvert(toInterpret);
            if (date != null) {
    			d.add(date);
    			String toTimeString = input.split("to ")[1];
    			try {
    				Calendar cal = Calendar.getInstance();
    	            Date toDate = (new SimpleDateFormat("HH:mm")).parse(toTimeString);
    	            cal.set(Calendar.HOUR_OF_DAY, toDate.getHours());
    	            cal.set(Calendar.MINUTE, toDate.getMinutes());
    	            d.add(cal.getTime());
    	        } catch (ParseException ex) {
    	        	d.clear();
    	            return;
    	        }
    			return;
            }
        }
        DeadlineDateConverter.convert(toInterpret, d);
		if(!d.isEmpty()){
			Calendar cal = Calendar.getInstance();
			cal.setTime(d.get(0));
			d.clear();
    	    cal.set(Calendar.HOUR_OF_DAY, 0);
    	    cal.set(Calendar.MINUTE, 0);
    	    d.add(cal.getTime());
    	    cal.set(Calendar.HOUR_OF_DAY, 23);
    	    cal.set(Calendar.MINUTE, 59);
    	    d.add(cal.getTime());
		}
    }
    

    public ShortIntervalDateConverter() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("cannot instantiate");
    }
}
