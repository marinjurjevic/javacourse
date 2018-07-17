package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija move prima 2 argumenta, odredište i vrijednost koja će se
 * "staviti" na odredište. Odredište može biti registar ili indirektna adresa,
 * vrijednost može biti zadana eksplicitno kao broj ili implicitno kao
 * vrijednost registra ili indirektna adresa.
 * 
 * Primjeri poziva ove instrukcije:
 * 
 * <pre>
 *  move rX, rY
 * move [rX+o1], [rY+o2]
 * move rX, broj
 * move [rX+o1], rY
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrMove implements Instruction {
	/**
	 * vrijednost prvog argumenta
	 */
	private InstructionArgument arg1;

	/**
	 * vrijednost prvog argumenta
	 */
	private InstructionArgument arg2;

	/**
	 * Stvara novu naredbu za množenje dva broja koja množi vrijednosti zadanih
	 * registara i sprema ih u odredišni registar
	 * 
	 * @param arguments
	 *            odredišni registar, dva faktora množenja
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		this.arg1 = arguments.get(0);
		this.arg2 = arguments.get(1);

		if (!arg1.isRegister()) {
			throw new IllegalArgumentException("Type mismatch for move command! First argument must be register");
		}

	}

	public boolean execute(Computer computer) {
		Object value = extractValue(computer);

		Integer des = (Integer) arg1.getValue();
		if (RegisterUtil.isIndirect((Integer) arg1.getValue())) {
			Memory mem = computer.getMemory();
			Registers regs = computer.getRegisters();
			mem.setLocation((Integer) regs.getRegisterValue(RegisterUtil.getRegisterIndex(des))
					+ RegisterUtil.getRegisterOffset(des), value);
		} else {
			computer.getRegisters().setRegisterValue(RegisterUtil.getRegisterIndex(des), value);
		}

		return false;
	}

	/**
	 * Vraća vrijednost koja će biti smjestena u odredište.
	 * 
	 * @return vrijednost argumenta
	 */
	private Object extractValue(Computer comp) {
		Object value;

		if (arg2.isRegister()) {
			Integer des = (Integer) arg2.getValue();
			if (RegisterUtil.isIndirect((Integer) arg2.getValue())) {
				Memory mem = comp.getMemory();
				Registers regs = comp.getRegisters();

				value = mem.getLocation((Integer) regs.getRegisterValue(RegisterUtil.getRegisterIndex(des))
						+ RegisterUtil.getRegisterOffset(des));
			} else {
				value = comp.getRegisters().getRegisterValue(RegisterUtil.getRegisterIndex(des));
			}
		} else {
			value = arg2.getValue();
		}

		return value;
	}
}