package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class InstrLoadTest {

	@Mock
	private Computer computer;
	@Mock
	private Registers registers;
	@Mock
	private Memory memory;
	@Mock
	private List<InstructionArgument> list;
	@Mock
	private InstructionArgument argument;

	@Mock
	private InstructionArgument first;
	@Mock
	private InstructionArgument second;

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments1() {
		when(list.size()).thenReturn(1);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(3);
		new InstrLoad(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFirstArgumentNotRegister() {
		when(list.size()).thenReturn(2);
		when(first.getValue()).thenReturn(0x00000000);
		when(first.isRegister()).thenReturn(false);

		when(second.getValue()).thenReturn(anyInt());

		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		new InstrLoad(list);
	}

	@Test
	public void testExecution() {

		// Prepare list of arguments.
		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(first);
		when(list.get(1)).thenReturn(second);
		when(first.isRegister()).thenReturn(true);

		// Address is in the second argument
		when(first.getValue()).thenReturn(0x00000001);
		when(second.getValue()).thenReturn(100);

		// Create instruction.
		Instruction load = new InstrLoad(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		doNothing().when(registers).setRegisterValue(anyInt(), anyObject());
		doNothing().when(memory).setLocation(anyInt(), anyObject());

		load.execute(computer);

		// Evaluation.
		verify(registers).setRegisterValue(anyInt(), anyObject());
		verify(memory).getLocation(100);
	}

}
