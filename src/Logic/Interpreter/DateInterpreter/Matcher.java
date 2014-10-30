package Logic.Interpreter.DateInterpreter;

import java.util.Date;

import Logic.Exceptions.InvalidCommandException;

public interface Matcher {
    public Date tryConvert(String input) throws InvalidCommandException;
}
