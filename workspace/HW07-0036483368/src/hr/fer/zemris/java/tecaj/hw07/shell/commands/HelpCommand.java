package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * 
 * @author Marin Jurjevic
 *
 */
public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("help", Arrays.asList("Shows some information about command"));
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		String arg = line == null ? null : line.trim();

		if (arg == null) {
			for (ShellCommand c : env.commands()) {
				env.writeln(c.getCommandName() + " - " + c.getCommandDescription());
			}
		} else {
			for (ShellCommand c : env.commands()) {
				if (c.getCommandName().equals(arg)) {
					env.writeln("---" + c.getCommandName() + "---");

					for (String desc : c.getCommandDescription()) {
						env.writeln(desc);
					}
				}
			}
		}

		return ShellStatus.CONTINUE;
	}

}
