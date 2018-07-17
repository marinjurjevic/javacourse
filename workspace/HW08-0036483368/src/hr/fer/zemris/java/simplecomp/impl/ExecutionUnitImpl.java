package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implemntacije upravljacke jedinke, tj. dijela računala koji izvodi pokreće i
 * izvodi instrukcije u "procesoru".
 * 
 * @author Marin Jurjevic
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Memory mem = computer.getMemory();
		Registers regs = computer.getRegisters();

		regs.setProgramCounter(0);
		while (true) {
			Instruction instr = (Instruction) mem.getLocation(regs.getProgramCounter());
			regs.incrementProgramCounter();

			if (instr.execute(computer)) {
				break;
			}
		}

		return true;
	}

}
