package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za provjerava jesu li sadržaji dva registra isti. Instrukcija
 * prima dva proizvoljna registra te ako su im sadržaji jednaki postavlja
 * zastavicu flag na true, odnosno false ako nisu. Primjer poziva:
 * 
 * <pre>
 *  testEquals rX, rY
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrTestEquals implements Instruction {

	/**
	 * indeks prvog registar za usporedbu
	 */
	private int indexRegistra1;

	/**
	 * indeks drugog registra za usporedbu
	 */
	private int indexRegistra2;

	/**
	 * Stvara novu naredbu za množenje dva broja koja množi vrijednosti zadanih
	 * registara i sprema ih u odredišni registar
	 * 
	 * @param arguments
	 *            odredišni registar, dva faktora množenja
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		for (int i = 0; i < 2; i++) {
			if (!arguments.get(i).isRegister() || RegisterUtil.isIndirect((Integer) arguments.get(i).getValue())) {
				throw new IllegalArgumentException("Type mismatch for argument " + i + "!");
			}
		}

		this.indexRegistra1 = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.indexRegistra2 = RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(indexRegistra1);
		Object value2 = computer.getRegisters().getRegisterValue(indexRegistra2);
		if (value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		} else {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}
}