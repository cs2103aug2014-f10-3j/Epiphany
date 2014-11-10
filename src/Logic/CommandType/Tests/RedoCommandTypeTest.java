//@author A0118905A
package Logic.CommandType.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Logic.CommandType.RedoCommandType;

public class RedoCommandTypeTest {

	@Test
	public void test() {
		RedoCommandType deleteCommand = new RedoCommandType();
		assertEquals("redo", deleteCommand.getType());
	}

}
