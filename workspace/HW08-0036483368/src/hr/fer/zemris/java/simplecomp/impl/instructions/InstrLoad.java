package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija <code>load</code> uzima sadržaj memorijske lokacije i pohranjuje
 * ga u dani registar. Instrukcija prima 2 argumenta, prvi koji predstavlja
 * registar u koji se sprema sadržaj te adresu memorijske lokacije s koje
 * dohvaćamo sadržaj.</br>
 * Instrukcija <b>ne podržava indirektno adresiranje </b> te radi isključivo
 * izravno s registrima i memorijskim lokacijama. Instrukcija load r0,
 * {@literal @}poruka, koja uzima sadržaj s memorijske lokacije na koju pokazuje
 * labela <tt>poruka</tt> i sprema sadržaj u registar r0 može se implementirati
 * kako je prikazano u nastavku:
 * 
 * <pre>
 *  load r0, {@literal @}poruka
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrLoad implements Instruction {

	/**
	 * registar0 - sadržaj će biti spremljen u njega
	 */
	private int indexRegistra;

	/**
	 * adresa memorijske lokacije
	 */
	private int memLokacija;

	/**
	 * Stvara novu naredbu za množenje dva broja koja množi vrijednosti zadanih
	 * registara i sprema ih u odredišni registar
	 * 
	 * @param arguments
	 *            odredišni registar, dva faktora množenja
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		InstructionArgument registar = arguments.get(0);
		this.memLokacija = (Integer) arguments.get(1).getValue();

		if (!registar.isRegister() || RegisterUtil.isIndirect((Integer) registar.getValue())) {
			throw new IllegalArgumentException("Type mismatch for register argument!");
		}

		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) registar.getValue());
	}

	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(indexRegistra, computer.getMemory().getLocation(memLokacija));
		return false;
	}
}