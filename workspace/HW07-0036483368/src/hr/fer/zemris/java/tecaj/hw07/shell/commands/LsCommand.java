package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command ls takes a single argument, directory, and writes a directory
 * listing.
 * 
 * @author Marin Jurjevic
 *
 */
public class LsCommand extends AbstractCommand {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public LsCommand() {
		super("ls", Arrays.asList("Prints out info about all stored objects in this directory"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		String path = line.trim();

		File dir = new File(path);
		File[] children = dir.listFiles();

		for (File child : children) {
			BasicFileAttributes attr = Files
					.getFileAttributeView(child.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
					.readAttributes();

			env.writeln(String.format("%c%c%c%c %10d %s %s", child.isDirectory() ? 'd' : '-',
					child.canRead() ? 'r' : '-', child.canRead() ? 'w' : '-', child.canRead() ? 'x' : '-', attr.size(),
					fDate(attr.creationTime()), child.getName()));
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Help method for formatting date
	 * 
	 * @param time
	 *            time to format
	 * @return formatted String
	 */
	private String fDate(FileTime time) {
		return sdf.format(new Date(time.toMillis()));

	}
}
