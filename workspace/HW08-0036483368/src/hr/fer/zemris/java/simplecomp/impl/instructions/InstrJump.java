package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za skok. Instrukcija prima 1 argument, adresu memorijske lokacije
 * koja će biti pridijeljena programskog brojilu. </br>
 * Instrukcija jump lokacija koja postavlja programskog brojilo na adresu
 * lokacije, može se implementirati kako je prikazano u nastavku:
 * 
 * <pre>
 *  jump lokacija
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrJump implements Instruction {

	private int memLokacija;

	/**
	 * Stvara novu naredbu skok.
	 * 
	 * @param arguments
	 *            nova memorijska lokaciju koja će biti pridružena programskog
	 *            brojilu
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		this.memLokacija = (Integer) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(memLokacija);
		return false;
	}
}