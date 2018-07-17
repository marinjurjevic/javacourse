package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Copies a specified file to specified location. Note that if location doesn't
 * exist, it will be created.
 * 
 * @author Marin Jurjevic
 *
 */
public class CopyCommand extends AbstractCommand {

	public CopyCommand() {
		super("copy", Arrays.asList("copies file from path1 to path2"));
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		// Getting path1(paths[0]) and path2(paths[1])
		String paths[] = line.split(" ");
		if (paths.length != 2) {
			throw new IllegalArgumentException("Invalid paths");
		}

		Path path1 = Paths.get(paths[0]);
		Path path2 = Paths.get(paths[1]);

		// check for wrong argument
		if (Files.isDirectory(path1)) {
			throw new IllegalArgumentException("Path1 must be a file");
		}

		// file exists?
		if (Files.exists(Paths.get(path2.toString(), path1.getFileName().toString()))) {
			env.writeln("File already exists. Do you want to overwrite it (Y/N)");
			String option = env.readLine();

			if (option.equals("N")) {
				return ShellStatus.CONTINUE;
			}
		}

		// create directory
		if (Files.isDirectory(path2) == false) {
			Files.createDirectories(path2);
		}

		path2 = Paths.get(path2.toString(), path1.getFileName().toString());

		File file1 = path1.toFile();
		File file2 = path2.toFile();

		try (InputStream is = new BufferedInputStream(new FileInputStream(file1));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(file2));) {
			byte[] buffer = new byte[1024];
			while (true) {
				int procitano = is.read(buffer);

				if (procitano < 1) {
					break;
				}

				os.write(buffer, 0, procitano);
			}
			env.writeln("File has been copied!");
		} catch (FileNotFoundException ex) {
			env.writeln("File does not exist");
		}

		return ShellStatus.CONTINUE;
	}

}
