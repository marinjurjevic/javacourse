package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Creates directory(ies) at given location.
 * 
 * @author Marin Jurjevic
 *
 */
public class MkdirCommand extends AbstractCommand {

	public MkdirCommand() {
		super("mkdir", Arrays.asList("Creates directory (or more of them) at specified locaiton"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {

		Path path = Paths.get(line.trim());

		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				env.writeln("Directory created.");
			} catch (IOException e) {
				env.writeln("Failed to create directory. Check do you have rights to modify given folder.");
			}
		}else{
			env.writeln("Directory already exists");
		}

		return ShellStatus.CONTINUE;
	}

}
