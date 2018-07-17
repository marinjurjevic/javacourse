package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Prints out hex dump of specified file.
 * 
 * @author Marin Jurjevic
 *
 */
public class HexDumpCommand extends AbstractCommand {

	/**
	 * Help for formatting offset
	 */
	private final static String zero = "0000000000";

	public HexDumpCommand() {
		super("hexdump", Arrays.asList("Prints out hexdump on given file."));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		String file = line.trim();

		Path path = Paths.get(file);

		if (!Files.isRegularFile(path)) {
			env.writeln("This is not a regular file. Can not make hexdump");
		}

		try (BufferedInputStream bf = new BufferedInputStream(new FileInputStream(path.toFile()))) {

			byte[] buffer = new byte[16];
			int k = 0;
			while (true) {
				int r = bf.read(buffer);
				if (r < 1) {
					break;
				}
				
				// formatted hexadecimal bytes
				env.write(offset(k++) + ":");
				for (int i = 0; i < r; i++) {
					if (i != 8) {
						env.write(" ");
					}
					env.write(String.format("%02X", buffer[i]));

					if (i == 7) {
						env.write("|");
					}

				}
				for (int i = r; i < 16; i++) {
					if(i!=8){
						env.write(" ");
					}
					
					env.write("  ");
					if (i == 7) {
						env.write("|");
					}
					
				}

				env.write(" | ");
				
				// plain supported text (only ASCI signs)
				for (int i = 0; i < r; i++) {
					char c = (char) buffer[i];
					if (c < 32 || c > 127) {
						env.write(".");
					} else {
						env.write(Character.toString(c));
					}
				}

				env.writeln("");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method for offset
	 * 
	 * @param i
	 *            number to format
	 * @return formatted offset
	 */
	private String offset(int i) {
		String s = Integer.toString(16 * i, 16);
		String intAsString = s.length() <= 10 ? zero.substring(s.length()) + s : s;

		return intAsString.toUpperCase();
	}
}
