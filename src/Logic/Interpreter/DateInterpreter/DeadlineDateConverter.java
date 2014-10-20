package Logic.Interpreter.DateInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public final class DeadlineDateConverter {

    private static final List<Matcher> matchers;
    private static final String REGEX_ADD_DEADLINE_COMMAND = ".*\\s(by|in)\\s.*";
	private static final String REGEX_SPLIT_ADD_DEADLINE_COMMAND = "\\s(by|in)\\s(?!.*\\s(by|in)\\s)";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //dictionary
	
    static {
        matchers = new LinkedList<Matcher>();
        matchers.add(new SoonMatcher());
        matchers.add(new DaysMatcher());
        matchers.add(new WeeksMatcher());
        matchers.add(new ExtendedDayMatcher());
        matchers.add(new DayMatcher());
        matchers.add(new OnlyDateMatcher());
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

    public static void registerMatcher(Matcher matcher) {
        matchers.add(0, matcher);
    }


    public static String convert(String input, ArrayList<Date> d) {
    	try {
			populateDictionary();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // adds an english dictionary
    	d.clear();
    	String toInterpret = input;
		String[] tokens = input.split(REGEX_SPLIT_ADD_DEADLINE_COMMAND);
		toInterpret = tokens[1];
        for (Matcher matcher : matchers) {
            Date date = matcher.tryConvert(toInterpret);
            if (date != null) {
    			d.add(date);
            	if(input.matches(REGEX_ADD_DEADLINE_COMMAND)){
        			return tokens[0];
            	} else {
            		return input.substring(0, input.lastIndexOf(' '));
            	}
            }
        }
        if(isValidEnglish(toInterpret)){
        	return input;
        } else {
        	return null;
        }
    }
    

    private DeadlineDateConverter() {
        throw new UnsupportedOperationException("cannot instantiate");
    }
    
    private static boolean isValidEnglish(String toCheck) {
    	String[] tokensToCheck = toCheck.split(" ");
    	for (String wordToCheck : tokensToCheck) {
			if(!actionWords.contains(wordToCheck)){
				return false;
			}
		}
		return true;
	}
    
    /**
	 * This function draws on an English Dictionary to check if the action words of the user are legitimate.
	 */
	private static void populateDictionary() throws FileNotFoundException{
		File dict = new File("dictionary.txt");
		Scanner dictScan = new Scanner(dict);
		while(dictScan.hasNextLine()){
			actionWords.add(dictScan.nextLine());
		}
	}
}
