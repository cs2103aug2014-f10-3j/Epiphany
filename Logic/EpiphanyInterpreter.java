import java.util.TreeSet;
import java.util.Scanner;
import java.io.*;
import java.lang.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class EpiphanyInterpreter {
	///all the string constants that are involved in displaying things to the user.
	private static final String MESSAGE_COMMAND_PROMPT = "command: ";
	private static final String REGEX_ADD_COMMAND = ".*\\s(by|on)\\s.*";
	private static final String REGEX_SPLIT_ADD_COMMAND = "\\s(by|on)\\s(?!.*\\s(by|on)\\s)";
	private static final TreeSet<String> actionWords = new TreeSet<String>();
	private static final HashMap<Integer, String> months = new HashMap<Integer, String>();

	/**
	 * This is the main function which dictates the flow of the program. All the functionality is
	 * abstracted out to other methods.
	 * @param args which contains the file name (at index 0) entered by the user.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		EpiphanyInterpreter obj = new EpiphanyInterpreter();	
		obj.populateActionWords();
		obj.populateMonths();
		obj.acceptUserInputUntilExit();
	}

	private void populateMonths(){
		months.put(1, "January");
		months.put(2, "February");
		months.put(3, "March");
		months.put(4, "April");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "September");
		months.put(10, "October");
		months.put(11, "November");
		months.put(12, "December");
	}

	private void populateActionWords() throws FileNotFoundException{
		File dict = new File("dictionary.txt");
		Scanner dictScan = new Scanner(dict);
		while(dictScan.hasNextLine()){
			actionWords.add(dictScan.nextLine());
		}
		/*
			actionWords.add("remember");
			actionWords.add("call");
			actionWords.add("finish");
			actionWords.add("do");
			actionWords.add("complete");
			actionWords.add("buy");
			actionWords.add("visit");
			actionWords.add("watch");
			actionWords.add("meet");
			actionWords.add("read");
			actionWords.add("revise");
			actionWords.add("go");
			actionWords.add("study");
		 */

	}
	/**
	 * This method accepts the user inputs until the 'exit' command is entered. None of the actual
	 * operations are carried out in this function - all the operations are left to the 'route' function.
	 */
	void acceptUserInputUntilExit() {
		Scanner input = new Scanner( System.in );
		String userInput;
		do{
			System.out.print(MESSAGE_COMMAND_PROMPT);
			userInput = input.nextLine();
			String interpretedCommand = interpretCommand(userInput);
			showToUser(interpretedCommand);
		} while(!userInput.equalsIgnoreCase("exit"));
		input.close();
	}

	private String interpretCommand(String userInput) {
		if(userInput.matches("(display|view).*")){
			return interpretDisplayCommand(userInput);
		} else if(userInput.equals("exit")) {
			return exitProgram();
		} else if(userInput.matches("(search|find).*")) {
			return interpretSearchCommand();
		} else { 
			return interpretAddCommand(userInput);
		} 
	}

	private String interpretSearchCommand() {
		// TODO Auto-generated method stub
		return "search command";
	}

	private static String extractDate(String input){
		//input could either be <x>st
		//or <x>th.
		StringBuilder st = new StringBuilder();
		int i=0;

		while(i<input.length() && Character.isDigit(input.charAt(i))){
			st.append(input.charAt(i));
			i++;
		}

		return st.toString();
	}

	public static boolean isNumericDate(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;
		}  
		return true;  
	}

	private String interpretAddCommand(String userInput) {
		if(userInput.matches(REGEX_ADD_COMMAND)){
			String[] tokens = userInput.split(REGEX_SPLIT_ADD_COMMAND);
			interpretTask(tokens[0]);
			interpretDate(tokens[1]);

			// we something to work with after regex split.
			//either by or from.
			String str = tokens[1];
			if(str.matches(".*\\d+.*")){
				// contains a number, probably a date.
				//16th.
				//16-Jan
				//16 Jan
				//16 Jan 2015
				//16-Jan-2015
				//Jan 16
				//16.6.2014
				String[] ans = str.split(" |-|\\.|\\/");
				int l = ans.length;
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();

				if(l == 1){
					String stringDate = extractDate(str);
					if(isNumericDate(stringDate)){
						int date = Integer.parseInt(stringDate);
						if(date >= today.getDate()){
							// in within same month.
							return "Date: " + date + " " + months.get(today.getMonth() + 1) + " " + (today.getYear() + 1900); 
						} else{

							if((today.getMonth() + 2) > 12){
								return "Date: " + date + " " + months.get(1) + " " + (today.getYear() + 1901);// corner case from jan to dec.
							}else{
								return "Date: " + date + " " + months.get(today.getMonth() + 2) + " " + (today.getYear() + 1900);// corner case from jan to dec.
							}
							
						}
					} else {
						return "Date: " + ans[0];
					}


				} else if(l == 2){
				// number then month or
				// month then number
				// need to check if its a month or date.
					String date;
					String month;

					if(isNumericDate(ans[0])){
						date = ans[0];
						month = ans[1];
						return "Date: " + date + " " + month + " " + (today.getYear() + 1900);
					}else{
						//not a number its a string, i.e month.
						// or its still a number, like 6th
						

						if((ans[0]).matches(".*\\d+.*")){
							//contains a number.
							//6th
							date = extractDate(ans[0]);
							month = ans[1];
							
							return "Date: " + date + " " + month + " " + (today.getYear() + 1900);
						} else{
							date = extractDate(ans[1]);
							month = ans[0];
						
							return "Date: " + date + " " + month + " " + (today.getYear() + 1900);
						}


						
					}
			} else if(l == 3){
				// usually 3 part date will be typed as
				// 16-Jan-2014
				// 16 Jan 2014
				// 16.6.14
				String date = ans[0];
				String month = ans[1];
				String year = ans[2];
				return "Date: " + date + " " + months.get(Integer.parseInt(month)) + " " + year;

			}

			return ans[0];
		}else{
			// doesnt contain a number.
			//tonight
			//tomorrow
			//next week
			//next month
			return "LOL";
		}
	} else {
		//Gibberish or single line command
		if(userInput.contains(" ") && actionWords.contains(userInput.split(" ")[0])){
			// possibly a single line command
			return userInput;

		}
		// else gibberish
		else{
			return "do you even english bro?";
		}
	}
	// TODO Auto-generated method stub
	//	return "regex found";
}

private void interpretTask(String string) {
	// TODO Auto-generated method stub
	showToUser(string);

}

private void interpretDate(String string) {
	// TODO Auto-generated method stub
	showToUser(string);

}

private String exitProgram() {
	// TODO Auto-generated method stub
	return "exiting program";
}

private String interpretDisplayCommand(String userInput) {
	// TODO Auto-generated method stub
	return "display command";
}

/**
 * displays the given text to the user.
 * @param text
 */
void showToUser(String text) {
	System.out.println(text);
}


}
