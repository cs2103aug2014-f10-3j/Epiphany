import java.util.Scanner;


public class EpiphanyInterpreter {
	///all the string constants that are involved in displaying things to the user.
		private static final String MESSAGE_COMMAND_PROMPT = "command: ";
		private static final String REGEX_ADD_COMMAND = ".*(by|on|from).*$";
		
		/**
		 * This is the main function which dictates the flow of the program. All the functionality is
		 * abstracted out to other methods.
		 * @param args which contains the file name (at index 0) entered by the user.
		 */
		public static void main(String[] args) {
			EpiphanyInterpreter obj = new EpiphanyInterpreter();	
			obj.acceptUserInputUntilExit();
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
			if(userInput.matches("(display|Display|view|View).*")){
				return interpretDisplayCommand(userInput);
			} else if(userInput.equals("exit")) {
				return exitProgram();
			} else if(userInput.matches(REGEX_ADD_COMMAND)) { 
				return interpretAddCommand(userInput);
			} else {
				return "invalid command";
			}
		}

		private String interpretAddCommand(String userInput) {
			// TODO Auto-generated method stub
			String[] tokens = userInput.split(REGEX_ADD_COMMAND);
			showToUser(tokens[0]);
			showToUser(tokens[1]);
			return "regex found";
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
