package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija <code>echo</code> uzima sadržaj registra i ispisuje ga na
 * standardni izlaz. Instrukcija podržava indirektno adresiranje, primjeri
 * poziva su u nastavku:
 * 
 * <pre>
 *  echo r0
 * echo [r0 + offset]
 * </pre>
 * 
 * , gdje je offset cjelobrojna vrijednost.
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrEcho implements Instruction {

	/**
	 * registar0 - sadržaj će biti spremljen u njega
	 */
	private int indexRegistra;

	/**
	 * vrijednost offseta ako postoji indirektno adresiranje
	 */
	private int offset;

	/**
	 * provjeravamo hoce li vrijednost biti vrijednost registra ili mem.
	 * lokacije
	 */
	private boolean isIndirect;

	/**
	 * Stvara novu naredbu za ispisivanje sadrzaja registra na ekran.
	 * 
	 * @param arguments
	 *            registar ciji ce sadrzaj biti ispisan na ekran
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		InstructionArgument deskriptor = arguments.get(0);

		if (!deskriptor.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for echo command!");
		}

		this.indexRegistra = RegisterUtil.getRegisterIndex((Integer) deskriptor.getValue());
		this.offset = RegisterUtil.getRegisterOffset((Integer) deskriptor.getValue());

		if (RegisterUtil.isIndirect((Integer) deskriptor.getValue())) {
			isIndirect = true;
		} else {
			isIndirect = false;
		}
	}

	public boolean execute(Computer computer) {
		Registers regs = computer.getRegisters();
		if (isIndirect) {
			Memory mem = computer.getMemory();
			System.out.print(mem.getLocation((Integer) regs.getRegisterValue(indexRegistra) + offset));
		} else {
			System.out.print(regs.getRegisterValue(indexRegistra));
		}

		return false;
	}
}