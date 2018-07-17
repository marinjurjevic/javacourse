package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Cat command prints out all data from given file with given charset. If no
 * charset is provided, default charset will be used
 * 
 * @author Marin Jurjevic
 *
 */
public class CatCommand extends AbstractCommand {

	public CatCommand() {
		super("cat", Arrays.asList("Prints out content from given file on standard output"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		String[] args = line.trim().split(" ");
		String charsetName;

		if (args.length == 1) {
			charsetName = Charset.defaultCharset().name();
		} else {
			charsetName = args[1];
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(args[0])), charsetName));) {

			String input;
			while ((input = br.readLine()) != null) {
				env.writeln(input);
			}
		}

		return ShellStatus.CONTINUE;
	}

}
