package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija call prima 1 argument, adresu koja će se staviti u PC(programskog
 * brojilo) i efektivno pozvati potprogram. Programsko brojilo se prvo stavlja
 * na stog (tj. adresa na koju on trenutno pokazuje). Potom se u brojilo upisuje
 * nova adresa na koju program efektivno "skače" u potprogram. </br>
 * 
 * Primjer poziva ove instrukcije:
 * 
 * <pre>
 *  call adresa
 * </pre>
 * 
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrCall implements Instruction {

	/**
	 * adresa potprograma
	 */
	private int adresa;

	/**
	 * Stvara novu naredbu za stavljanje sadržaja danog registra na stog.
	 * 
	 * @param arguments
	 *            registar čiji sadržaj prebacujemo na stog
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		this.adresa = (Integer) arguments.get(0).getValue();
	}

	public boolean execute(Computer computer) {

		// adresa vrha stoga;
		int SPValue = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);

		// stavi sadrzaj programskog brojila na stog
		computer.getMemory().setLocation(SPValue, computer.getRegisters().getProgramCounter());
		// smanji stack pointer
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, SPValue - 1);

		// pohrani adresu potprograma u PC
		computer.getRegisters().setProgramCounter(adresa);

		return false;
	}

}