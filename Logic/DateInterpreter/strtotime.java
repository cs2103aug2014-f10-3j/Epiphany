package DateInterpreter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public final class strtotime {

    private static final List<Matcher> matchers;

    static {
        matchers = new LinkedList<Matcher>();
        matchers.add(new TomorrowMatcher());
        matchers.add(new DaysMatcher());
        matchers.add(new WeeksMatcher());
        matchers.add(new DayMatcher());
        matchers.add(new OnlyDateMatcher());
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd.MM.yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd/MM/yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd-MM-yyyy")));
        matchers.add(new DateFormatMatcherThree(new SimpleDateFormat("dd MM yyyy")));
        matchers.add(new DateFormatMatcherTwo(new SimpleDateFormat("dd MMM")));
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

    /*public static void main(String[] args){
    	System.out.println(strtotime("26th july"));
    }*/
    public static void registerMatcher(Matcher matcher) {
        matchers.add(0, matcher);
    }

    public static interface Matcher {

        public Date tryConvert(String input);
    }

    public static Date convert(String input) {
        for (Matcher matcher : matchers) {
            Date date = matcher.tryConvert(input);

            if (date != null) {
                return date;
            }
        }

        return null;
    }

    private strtotime() {
        throw new UnsupportedOperationException("cannot instantiate");
    }
}
