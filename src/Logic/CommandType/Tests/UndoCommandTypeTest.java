//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.CommandType.UndoCommandType;

public class UndoCommandTypeTest {

	@Test
	public void test() {
		UndoCommandType deleteCommand = new UndoCommandType();
		assertEquals("undo", deleteCommand.getType());
	}

}
