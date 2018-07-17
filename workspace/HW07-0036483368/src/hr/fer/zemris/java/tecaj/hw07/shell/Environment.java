package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;

/**
 * Defines shell environment. This interface offers neccessary methods to
 * control shell functionality.
 * 
 * The Environment interface provides method to read a line
 * 
 * The Environment interface provides two methods for writing text on a screen
 * (with or without new line separator at the end).
 * 
 * The Environment provides factory method for creating Iterable object that
 * iterates over all available commands in the shell.
 *
 */
public interface Environment {

	/**
	 * Reads one String of text from keyboard
	 * 
	 * @return text that has been read from keyboard
	 */
	public String readLine() throws IOException;

	/**
	 * Writes String of text on screen in a line
	 * 
	 * @param message
	 *            text that will be written on screen
	 */
	public void write(String message) throws IOException;

	/**
	 * Writes a String of text on screen in a line and also separates line on
	 * the end of the message
	 * 
	 * @param message
	 *            text that will be written on screen.
	 */
	public void writeln(String message) throws IOException;

	/**
	 * Factory method for creating an iterator that goes through all available
	 * commands.
	 * 
	 * @return iterator iterating through all commands
	 */
	public Iterable<ShellCommand> commands();

	/**
	 * Returns currently used symbol for multiline command
	 * 
	 * @return currently used symbol for multiline command
	 */
	public Character getMultilineSymbol();

	/**
	 * Sets new character as multiline command symbol
	 * 
	 * @param symbol
	 *            new character for multiline command symbol
	 */
	public void setMultilineSymbol(Character symbol);

	/**
	 * Returns currently used symbol for prompt
	 * 
	 * @return currently used symbol for prompt
	 */
	public Character getPromptSymbol();

	/**
	 * Sets new character as prompt symbol
	 * 
	 * @param symbol
	 *            new character for prompt symbol
	 */
	public void setPromptSymbol(Character symbol);

	/**
	 * Returns currently used symbol for more lines
	 * 
	 * @return currently used symbol for more lines
	 */
	public Character getMorelinesSymbol();

	/**
	 * Sets new character as more lines symbol
	 * 
	 * @param symbol
	 *            new character for more lines symbol
	 */
	public void setMorelinesSymbol(Character symbol);

}
