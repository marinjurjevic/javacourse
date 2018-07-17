package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names of supported charsets for
 * this Java platform.
 * 
 * @author Marin Jurjevic
 *
 */
public class CharsetCommand extends AbstractCommand {

	public CharsetCommand() {
		super("charsets", Arrays.asList("Lists all supported charsets"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		Set<String> charsets = Charset.availableCharsets().keySet();

		for (String c : charsets) {
			env.writeln(c);
		}

		return ShellStatus.CONTINUE;
	}

}
