//@author A0118905A
package Logic.Interpreter.DateInterpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;

import Logic.Exceptions.*;

public final class strtotime {

	//Regex's which are used to check the type of date we are trying to match.
    private static final String REGEX_ADD_DEADLINE_COMMAND = ".*\\s(by)\\s.*";
    private static final String REGEX_ADD_SHORT_INTERVAL_COMMAND = ".*\\s(on)\\s.*";
    private static final String REGEX_ADD_LONG_INTERVAL_COMMAND = ".*\\s(from)\\s.*";
    private static final String REGEX_ADD_EDGE_CASE_COMMAND = ".*(this|next|tomorrow|today).*";
    private static final String REGEX_NOT_ADD_EDGE_CASE_COMMAND = "(this|next|tomorrow|today).*";
	private static final TreeSet<String> actionWords = new TreeSet<String>(); //dictionary
	
	//Regex's which are used to split the input we want to interpret according to the type of date given.
	private static final String REGEX_SPLIT_ADD_DEADLINE_COMMAND = "\\s(by)\\s(?!.*\\s(by)\\s)";
	private static final String REGEX_SPLIT_ADD_SHORT_INTERVAL_COMMAND = "\\s(on)\\s(?!.*\\s(on)\\s)";
	private static final String REGEX_SPLIT_ADD_LONG_INTERVAL_COMMAND = "\\s(from)\\s(?!.*\\s(from)\\s)";

    public static String convert(String input, ArrayList<Date> d) throws InvalidCommandException {
    	try {
			populateDictionary();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} // adds an english dictionary
    	d.clear();
    	String toInterpret = input;
    	if(input.matches(REGEX_ADD_DEADLINE_COMMAND)){
    		//If the command contains the word 'by' it may be a deadline task.
    		String[] tokens = input.split(REGEX_SPLIT_ADD_DEADLINE_COMMAND);
    		toInterpret = tokens[1];
    		DeadlineDateConverter.convert(toInterpret.toLowerCase(), d);
    		if(!d.isEmpty()){
    			return tokens[0];
    		}
    	} else if(input.matches(REGEX_ADD_SHORT_INTERVAL_COMMAND)){
    		//If the command contains the word 'by' it may be a interval task within a single day.
    		String[] tokens = input.split(REGEX_SPLIT_ADD_SHORT_INTERVAL_COMMAND);
    		toInterpret = tokens[1];
    		ShortIntervalDateConverter.convert(toInterpret.toLowerCase(), d);
    		if(d.size()==2){
    			return tokens[0];
    		}
    	} else if(input.matches(REGEX_ADD_EDGE_CASE_COMMAND) && !input.matches(REGEX_NOT_ADD_EDGE_CASE_COMMAND)){
    		//If the command enters here there may be a date without any of our key words in it.
    		String taskDescription;
    		if(input.contains("this")){
    			toInterpret = input.substring(input.lastIndexOf("this"));
    			taskDescription = input.substring(0, input.lastIndexOf("this")-1);
    		} else if(input.contains("next")){
    			toInterpret = input.substring(input.lastIndexOf("next"));
    			taskDescription = input.substring(0, input.lastIndexOf("next")-1);
    		} else if(input.contains("tomorrow")){
    			toInterpret = input.substring(input.lastIndexOf("tomorrow"));
    			taskDescription = input.substring(0, input.lastIndexOf("tomorrow")-1);
    		} else{
    			toInterpret = input.substring(input.lastIndexOf("today"));
    			taskDescription = input.substring(0, input.lastIndexOf("today")-1);
    		}
    		ShortIntervalDateConverter.convert(toInterpret.toLowerCase(), d);
    		if(d.size()==2||d.size()==1){
    			return taskDescription;
    		}
    	} else if(input.matches(REGEX_ADD_LONG_INTERVAL_COMMAND)){
    		//If the command contains the word 'from' it may be a interval task lasting more than a day.
    		String[] tokens = input.split(REGEX_SPLIT_ADD_LONG_INTERVAL_COMMAND);
    		toInterpret = tokens[1];
    		LongIntervalDateConverter.convert(toInterpret.toLowerCase(), d);
    		if(d.size()==2){
    			return tokens[0];
    		}
    	}
    	//At this point we know that there is no valid date in the command. That could be because if a spelling error in
    	//typing the date or because the user tried to enter a floating task.
    	if(isValidEnglish(toInterpret) || toInterpret.equals(input)){
        	return input;
        } else {
        	throw new InvalidCommandException();
        }
    }
    

    private strtotime() throws UnsupportedOperationException{
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
