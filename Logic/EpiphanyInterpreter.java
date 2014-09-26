import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class EpiphanyInterpreter {
	///all the string constants that are involved in displaying things to the user.
		private static final String MESSAGE_COMMAND_PROMPT = "command: ";
		private static final String REGEX_ADD_COMMAND = ".*\\s(by|on)\\s.*";
		private static final String REGEX_SPLIT_ADD_COMMAND = "\\s(by|on)\\s(?!.*\\s(by|on)\\s)";
		private static final ArrayList<String> actionWords = new ArrayList<String>();
		
		/**
		 * This is the main function which dictates the flow of the program. All the functionality is
		 * abstracted out to other methods.
		 * @param args which contains the file name (at index 0) entered by the user.
		 */
		public static void main(String[] args) throws FileNotFoundException {
			EpiphanyInterpreter obj = new EpiphanyInterpreter();	
			obj.populateActionWords();
			obj.acceptUserInputUntilExit();
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

		private String interpretAddCommand(String userInput) {
			if(userInput.matches(REGEX_ADD_COMMAND)){
			String[] tokens = userInput.split(REGEX_SPLIT_ADD_COMMAND);
			interpretTask(tokens[0]);
			interpretDate(tokens[1]);
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
			return "regex found";
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
