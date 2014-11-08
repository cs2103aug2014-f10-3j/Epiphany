package Logic.Interpreter.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.Interpreter.CommandType.UndoCommandType;

public class UndoCommandTypeTest {

	@Test
	public void test() {
		UndoCommandType deleteCommand = new UndoCommandType();
		assertEquals("undo", deleteCommand.getType());
	}

}
