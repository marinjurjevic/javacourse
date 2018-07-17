package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.List;

/**
 * Descrbes a single command in the shell. The ShellCommand offers 3 methods for
 * command description.
 *
 */
public interface ShellCommand {

	/**
	 * Returns command name
	 * 
	 * @return command name
	 */
	public String getCommandName();

	/**
	 * Offers command description
	 * 
	 * @return command description
	 */
	public List<String> getCommandDescription();

	/**
	 * Executes given command. Method has two parameters.
	 * 
	 * @param env
	 *            reference to environment in which command is executed
	 * @param line
	 *            read line from shell without command name
	 * @return shell status after execution
	 * @throws IOException
	 */

	public ShellStatus execute(Environment env, String line) throws IOException;

}
