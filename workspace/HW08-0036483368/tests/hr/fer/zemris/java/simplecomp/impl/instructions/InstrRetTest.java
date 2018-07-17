package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeast;
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
public class InstrRetTest {

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

	@Test
	public void testExecution() {
		// Create instruction.
		Instruction ret = new InstrRet(list);

		// Mocking memory and register components.
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		// Mocking PC behavior.
		doNothing().when(registers).setProgramCounter(anyInt());

		// Mocking stack pointer register
		when(registers.getRegisterValue(15)).thenReturn(300, 301);

		doNothing().when(registers).setRegisterValue(15, 301);
		// Mocking memory behavior.
		when(memory.getLocation(301)).thenReturn(100);

		ret.execute(computer);

		// Checking minimal performance.
		verify(registers).setProgramCounter(100);
		verify(registers, atLeast(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, atLeast(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 301);

	}
}