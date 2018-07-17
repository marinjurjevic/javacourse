package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za uvjetni skok. Instrukcija prima 1 argument, adresu memorijske
 * lokacije koja će biti pridijeljena programskog brojilu ako je zadovoljen
 * uvjet. Uvjet koji se provjerava je vrijednost zastavice u implementaciji
 * registara </br>
 * Instrukcija jumpIfTrue lokacija koja postavlja programskog brojilo na adresu
 * lokacije ako je uvjet zadovoljen, može se implementirati kako je prikazano u
 * nastavku:
 * 
 * <pre>
 *  jumpIfTrue lokacija
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrJumpIfTrue implements Instruction {

	private int memLokacija;

	/**
	 * Stvara novu naredbu za uvjetni skok.
	 * 
	 * @param arguments
	 *            nova memorijska lokaciju koja će biti pridružena programskog
	 *            brojilu u slučaju ako je uvjet zadovoljen
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		this.memLokacija = (Integer) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(memLokacija);
		}
		return false;
	}
}