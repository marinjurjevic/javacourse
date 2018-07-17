package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.AbstractCommand;

/**
 * Exits the program.
 * @author Marin Jurjevic
 *
 */
public class ExitCommand extends AbstractCommand {

	
	public ExitCommand() {
		super("exit", Arrays.asList("exits my shell"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		return ShellStatus.TERMINATE;
	}

}
