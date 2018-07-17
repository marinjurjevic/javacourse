package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija za zaustavljanje rada procesora. Ne prima argumente.
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrHalt implements Instruction {

	public InstrHalt(List<InstructionArgument> arguments) {

	}

	public boolean execute(Computer computer) {
		return true;
	}
}