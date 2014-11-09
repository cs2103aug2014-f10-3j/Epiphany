//@author A0118905A
package Logic.Interpreter.DateInterpreter;

import java.util.Date;

import Logic.Exceptions.InvalidCommandException;

public interface Matcher {
	//Every matcher must have a method to try and convert a string input to a Date output.
    public Date tryConvert(String input) throws InvalidCommandException;
}
