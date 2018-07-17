package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Prints out all subdirectories and files that this directory is root.
 * 
 * @author Marin Jurjevic
 *
 */
public class TreeCommand extends AbstractCommand {

	/**
	 * Making it a member field for easier output handling
	 */
	private Environment env;

	public TreeCommand() {
		super("tree", Arrays.asList("Prints out all subdirectories and files and their info."));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		this.env = env;
		printTree(new File(line.trim()), 0);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Recursive traverse of tree that this dir is root to.
	 * 
	 * @param dir
	 *            root directory
	 * @param level
	 *            level of indentation
	 */
	private void printTree(File dir, int level) throws IOException {
		print(dir, level);

		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return; // preskacem
		}

		for (File file : djeca) {
			if (file.isFile()) {
				print(file, level + 1);
			} else if (file.isDirectory()) {
				printTree(file, level + 1);
			}
		}
	}

	private void print(File file, int level) throws IOException {
		if (level == 0) {
			env.writeln(file.getAbsolutePath());
		} else {
			env.write(String.format("%" + (2 * level) + "s%s%n", "", file.getName()));
		}
	}
}
