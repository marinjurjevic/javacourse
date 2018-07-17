package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija push prima 1 argument, registar čiji se sadržaj staviti na stog.
 * Instrukcija potom smanjuje pokazivač na vrh stoga za 1. Pretpostavljeni
 * registar koji čuva informaciju gdje se vrh stoga nalazi trenutno jest
 * r15.</br>
 * 
 * Primjer poziva ove instrukcije:
 * 
 * <pre>
 *  push r0
 * </pre>
 * 
 * Funkcija s pretpostavljenim registrom za vrh stoga, ima isto značenje kao i
 * kod u nastavku:
 * 
 * <pre>
 *  move [r15], r0
 * decrement r15
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrPush implements Instruction {

	/**
	 * pamtimo indeks registra
	 */
	private int indexRegistra;

	/**
	 * Stvara novu naredbu za stavljanje sadržaja danog registra na stog.
	 * 
	 * @param arguments
	 *            registar čiji sadržaj prebacujemo na stog
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		InstructionArgument arg = arguments.get(0);

		if (!arg.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for push command! Argument must be register");
		}

		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) arg.getValue());
	}

	public boolean execute(Computer computer) {
		int SPValue = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		// stavi sadrzaj registra na stog
		Object naStog = computer.getRegisters().getRegisterValue(indexRegistra);
		computer.getMemory().setLocation(SPValue, naStog);

		// smanji stack pointer
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, SPValue - 1);

		return false;
	}

}