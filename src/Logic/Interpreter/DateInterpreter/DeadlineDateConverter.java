//@author A0118905A
package Logic.Interpreter.DateInterpreter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Logic.Exceptions.InvalidCommandException;

public final class DeadlineDateConverter {

    private static final List<Matcher> matchers;
	
    //List of date matcher classes that we will exhaustively use to determine weather
    //the input contains a deadline style date.
    static {
    	matchers = new LinkedList<Matcher>();

        //WITH TIME SPECIFICATIONS
        matchers.add(new SoonMatcherWithTime());
        matchers.add(new DayMatcherWithTime());
        matchers.add(new ExtendedDayMatcherWithTime());
        matchers.add(new OnlyDateMatcherWithTime());
        //Matchers which follow default java date formats
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd.MM.yyyy HH:mm")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd/MM/yyyy HH:mm")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd-MM-yyyy HH:mm")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd MM yyyy HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd MMM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd/MM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'st' MMM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'nd' MMM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'rd' MMM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'th' MMM HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'st' HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'nd' HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'rd' HH:mm")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'th' HH:mm")));
        
    	//WITHOUT TIME SPECIFICATIONS
        matchers.add(new SoonMatcher());
        matchers.add(new DayMatcher());
        matchers.add(new ExtendedDayMatcher());
        matchers.add(new OnlyDateMatcher());
        //Matchers which follow default java date formats
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd.MM.yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd/MM/yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd-MM-yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd MM yyyy")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd MMM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd/MM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'st' MMM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'nd' MMM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'rd' MMM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd'th' MMM")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'st'")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'nd'")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'rd'")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("MMM dd'th'")));
    }


    public static void convert(String input, ArrayList<Date> d) throws InvalidCommandException {
    	//the format for deadline dates is "<date>"
    	d.clear();
    	String toInterpret = input;
    	//cycle through all of the matchers and if any one of them succeeds, then we have a winner.
        for (Matcher matcher : matchers) {
            Date date = matcher.tryConvert(toInterpret);
            if (date != null) {
    			d.add(date);
    			return;
            }
        }
    }
    

    private DeadlineDateConverter() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("cannot instantiate");
    }
}
