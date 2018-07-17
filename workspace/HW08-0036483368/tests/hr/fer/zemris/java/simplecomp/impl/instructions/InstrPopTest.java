package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.mockito.Mockito.atLeast;
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
public class InstrPopTest {

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

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments1() {
		when(list.size()).thenReturn(0);
		new InstrPop(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongNoOfArguments2() {
		when(list.size()).thenReturn(2);
		new InstrPop(list);
	}

	@Test
	public void testAllValidInputs() {
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(1);
		new InstrPop(list);
	}

	@Test
	public void testExecution() {
		// use r1
		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(argument);
		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(1);

		// Create instruction.
		Instruction pop = new InstrPop(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		// Mocking stack pointer register
		when(registers.getRegisterValue(15)).thenReturn(300, 301);

		// Mocking memory behavior.
		when(memory.getLocation(300)).thenReturn(0);

		pop.execute(computer);

		// Checking minimal performance.
		verify(registers, atLeast(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, atLeast(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 301);

	}
}