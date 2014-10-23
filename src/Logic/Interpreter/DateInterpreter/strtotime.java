package Logic.Interpreter.DateInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;

import Logic.Exceptions.*;

public final class strtotime {

    private static final String REGEX_ADD_DEADLINE_COMMAND = ".*\\s(by)\\s.*";
	private static final String REGEX_SPLIT_ADD_DEADLINE_COMMAND = "\\s(by)\\s(?!.*\\s(by)\\s)";
    private static final String REGEX_ADD_SHORT_INTERVAL_COMMAND = ".*\\s(on)\\s.*";
	private static final String REGEX_SPLIT_ADD_SHORT_INTERVAL_COMMAND = "\\s(on)\\s(?!.*\\s(on)\\s)";
    private static final String REGEX_ADD_LONG_INTERVAL_COMMAND = ".*\\s(from)\\s.*";
	private static final String REGEX_SPLIT_ADD_LONG_INTERVAL_COMMAND = "\\s(from)\\s(?!.*\\s(from)\\s)";
	//private static final String REGEX_SPLIT_ADD_COMMAND = "\\s(by|on|in)\\s(?!.*\\s(by|on|in)\\s)";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //dictionary

    public static String convert(String input, ArrayList<Date> d) throws InvalidCommandException {
    	try {
			populateDictionary();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // adds an english dictionary
    	d.clear();
    	String toInterpret = input;
    	if(input.matches(REGEX_ADD_DEADLINE_COMMAND)){
    		String[] tokens = input.split(REGEX_SPLIT_ADD_DEADLINE_COMMAND);
    		toInterpret = tokens[1];
    		DeadlineDateConverter.convert(toInterpret, d);
    		if(!d.isEmpty()){
    			return tokens[0];
    		}
    	} else if(input.matches(REGEX_ADD_SHORT_INTERVAL_COMMAND)){
    		String[] tokens = input.split(REGEX_SPLIT_ADD_SHORT_INTERVAL_COMMAND);
    		toInterpret = tokens[1];
    		ShortIntervalDateConverter.convert(toInterpret, d);
    		if(d.size()==2){
    			return tokens[0];
    		}
    	} else if(input.matches(REGEX_ADD_LONG_INTERVAL_COMMAND)){
    		String[] tokens = input.split(REGEX_SPLIT_ADD_LONG_INTERVAL_COMMAND);
    		toInterpret = tokens[1];
    		LongIntervalDateConverter.convert(toInterpret, d);
    		if(d.size()==2){
    			return tokens[0];
    		}
    	}
    	if(isValidEnglish(toInterpret) || toInterpret.equals(input)){
        	return input;
        } else {
        	throw new InvalidCommandException();
        }
    }
    

    public strtotime() throws UnsupportedOperationException{
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
		dictScan.close();
	}
}
