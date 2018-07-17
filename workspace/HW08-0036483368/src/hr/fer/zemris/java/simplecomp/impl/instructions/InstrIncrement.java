package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za povecavanje vrijednosti registra za 1. Instrukcija prima 1
 * argument, registar kojem će se vrijednost koju sadrži povećati za jedan </br>
 * Instrukcija <b>ne podržava indirektno adresiranje </b> te radi isključivo
 * izravno s registrima. Instrukcija increment r0 koja uzima sadržaj registara
 * r0, te ga povećava za 1 može se implementirati kako je prikazano u nastavku:
 * 
 * <pre>
 *  increment r0
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrIncrement implements Instruction {

	/**
	 * registar1 - registar čiju vrijednost ćemo povećati za 1
	 */
	private int indexRegistra;

	/**
	 * Stvara novu naredbu za povećavanje vrijednosti danog registra
	 * 
	 * @param arguments
	 *            registar kojem će se vrijednost povećati za 1
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}

	public boolean execute(Computer computer) {
		int staraVrijednost = (Integer) computer.getRegisters().getRegisterValue(indexRegistra);
		computer.getRegisters().setRegisterValue(indexRegistra, staraVrijednost + 1);
		return false;
	}
}