package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za zbrajanje. Instrukcija prima 3 argumenta koji predstavljaju
 * registre. Prvi registar je odredište, a druga dva su vrijednosti koje se
 * zbrajaju. </br>
 * Instrukcija <b>ne podržava indirektno adresiranje </b> te radi isključivo
 * izravno s registrima. Instrukcija add r0, r1, r2 koja uzima sadržaje
 * registara r1 i r2, zbraja ih i rezultat pohranjuje u r0 može se
 * implementirati kako je prikazano u nastavku:
 * 
 * <pre>
 *  add r0, r1, r2
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrAdd implements Instruction {

	/**
	 * registar1 - vrijednost će biti spremljena u njega
	 */
	private int indexRegistra1;

	/**
	 * prvi faktor množenja
	 */
	private int indexRegistra2;

	/**
	 * drugi faktor množenja
	 */
	private int indexRegistra3;

	/**
	 * Stvara novu naredbu za zbrajanje dva broja koja zbraja vrijednosti
	 * zadanih registara i sprema ih u odredišni registar
	 * 
	 * @param arguments
	 *            odredišni registar, dva pribrojnika
	 */
	public InstrAdd(List<InstructionArgument> arguments) {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		for (int i = 0; i < 3; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}
		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.indexRegistra3 = RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra2);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra3);
		computer.getRegisters().setRegisterValue(indexRegistra1, Integer.valueOf((Integer) value1 + (Integer) value2));
		return false;
	}
}