package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexDumpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeCommand;

/**
 * Simulation of a simple shell. MyShell supports simple commands like copy, make directory,
 * print file on screen and hexdump. For extra info call command help to find out more.
 * @author Marin Jurjevic
 *
 */
public class MyShell {
	
	/**
	 * private map of commands for easy retrieval
	 */
	private static Map<String, ShellCommand> commands;

	/**
	 * Static initialization of commands
	 */
	static {
		commands = new HashMap<>();
		ShellCommand[] cc = { new CharsetCommand(), new CatCommand(), new LsCommand(), new TreeCommand(),
				new CopyCommand(), new MkdirCommand(), new HexDumpCommand(), new HelpCommand(), new ExitCommand(),
				new SymbolCommand() };
		for (ShellCommand c : cc) {
			commands.put(c.getCommandName(), c);
		}
	}

	/**
	 * Simple implementation of an environemnt which communicates to client through standard I/O.
	 * @author Marin Jurjevic
	 *
	 */
	public static class EnvironmentImpl implements Environment {
		private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

		private Character promptSymbol = '>';
		private Character multilineSymbol = '|';
		private Character morelinesSymbol = '\\';

		@Override
		public String readLine() throws IOException {
			return reader.readLine();
		}

		@Override
		public void write(String line) throws IOException {
			writer.write(line);
			writer.flush();
		}

		@Override
		public void writeln(String line) throws IOException {
			writer.write(line);
			writer.newLine();
			writer.flush();
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character promptSymbol) {
			this.promptSymbol = promptSymbol;
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character multilineSymbol) {
			this.multilineSymbol = multilineSymbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character morelinesSymbol) {
			this.morelinesSymbol = morelinesSymbol;
		}

		@Override
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

	}

	public static Environment environment = new EnvironmentImpl();

	public static void main(String[] args) throws IOException {
		environment.writeln("Welcome to MyShell v 1.0");
		while (true) {
			environment.write(environment.getPromptSymbol() + " ");
			String line = environment.readLine();

			String s[] = line.trim().split(" ", 2);

			String cmd = s[0];
			String arg = null;
			if (s.length == 2) {
				arg = s[1];
				// more lines..
				if (arg.endsWith("" + environment.getMorelinesSymbol())) {
					arg = arg.substring(0, arg.length() - 1);
					String nextLine;
					while (true) {
						environment.write(environment.getMultilineSymbol() + " ");

						nextLine = environment.readLine();

						if (!nextLine.endsWith("" + environment.getMorelinesSymbol())) {
							arg += nextLine;
							break;
						}

						arg += nextLine.substring(0, nextLine.length() - 1);
					}
				}
				arg = arg.replace("\"", "");
			}
			
			
			ShellCommand shellCommand = commands.get(cmd);
			if (shellCommand == null) {
				environment.writeln("Uknown Command");
				continue;
			}

			ShellStatus status = null;
			try {
				status = shellCommand.execute(environment, arg);
			} catch (IllegalArgumentException ex) {
				environment.writeln(ex.getMessage());
			}

			if (status == ShellStatus.TERMINATE) {
				environment.writeln("Goodbye");
				break;
			}

		}

	}

}
