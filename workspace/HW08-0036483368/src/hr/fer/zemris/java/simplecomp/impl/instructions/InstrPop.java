package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija pop prima 1 argument, registar u koji će se staviti element sa
 * vrha stoga. Instrukcija potom povećava pokazivač na vrh stoga za 1.
 * Pretpostavljeni registar koji čuva informaciju gdje se vrh stoga nalazi
 * trenutno jest r15.</br>
 * 
 * Primjer poziva ove instrukcije:
 * 
 * <pre>
 *  pop r0
 * </pre>
 * 
 * Funkcija s pretpostavljenim registrom za vrh stoga, ima isto značenje kao i
 * kod u nastavku:
 * 
 * <pre>
 *  
 * increment r15
 * move r0, [r15]
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrPop implements Instruction {

	/**
	 * pamtimo indeks registra
	 */
	private int indexRegistra;

	/**
	 * Stvara novu naredbu za skidanje sadržaja sa stoga u dani registar.
	 * 
	 * @param arguments
	 *            registar koji će primiti sadržaj vrha stoga
	 */
	public InstrPop(List<InstructionArgument> arguments) {
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
		// povećaj stack pointer
		int SPValue = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, SPValue + 1);

		// stavi sadrzaj s vrha stoga u registar
		Object saStoga = computer.getMemory().getLocation(SPValue + 1);
		computer.getRegisters().setRegisterValue(indexRegistra, saStoga);

		return false;
	}

}