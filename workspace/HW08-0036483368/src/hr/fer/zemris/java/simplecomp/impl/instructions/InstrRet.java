package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija ret ne prima argumente. Vraća se iz potprograma pozvanog
 * instrukcijom call. S vrha stoga skida adresu i postavlja je kao vrijednost
 * registra PC (program counter). </br>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrRet implements Instruction {

	/**
	 * Stvara novu naredbu za povratak iz potprograma
	 * 
	 * @param arguments
	 *            nema efekta
	 */
	public InstrRet(List<InstructionArgument> arguments) {

	}

	public boolean execute(Computer computer) {
		// povećaj stack pointer
		int SPValue = (Integer) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, SPValue + 1);

		// stavi sadrzaj s vrha stoga u programsko brojilo
		computer.getRegisters().setProgramCounter((Integer) computer.getMemory().getLocation(SPValue + 1));

		return false;
	}

}