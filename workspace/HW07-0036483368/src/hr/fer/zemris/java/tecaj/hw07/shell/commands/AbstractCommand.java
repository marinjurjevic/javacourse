package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;

/**
 * Represents an abstract representation of a command. This is superclass of all
 * commands which implement ShellCommand interface required for communication
 * with environment
 * 
 * @author Marin Jurjevic
 *
 */
public abstract class AbstractCommand implements ShellCommand {

	/**
	 * name of this command
	 */
	private final String commandName;

	/**
	 * description of this command
	 */
	private final List<String> commandDescription;

	/**
	 * Creates new command with proper name and description.
	 * 
	 * @param commandName
	 *            command name
	 * @param commandDescription
	 *            command description
	 */
	public AbstractCommand(String commandName, List<String> commandDescription) {
		this.commandName = commandName;
		this.commandDescription = commandDescription;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
