package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Arrays;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Enables change of prompt symbols.
 * @author Marin Jurjevic
 *
 */
public class SymbolCommand extends AbstractCommand {

	public SymbolCommand() {
		super("symbol", Arrays.asList("Gets current symbol for specified area or changes is to the given character"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public ShellStatus execute(Environment env, String line) throws IOException {
		String[] args = line.split(" ");
		
		if(args.length == 2){
			if(args[1].length()!=1){
				throw new IllegalArgumentException("Invalid symbol character!");
			}
			switchSymbol(args[0],args[1].charAt(0), env);
		}else{
			printSymbol(args[0], env);
		}
		
		return ShellStatus.CONTINUE;
	}

	private void switchSymbol(String part, Character symbol, Environment env) throws IOException{
		switch(part.toUpperCase()){
		case "PROMPT":
			env.writeln("Symbol for PROMPT changed from " + env.getPromptSymbol() + " to " + symbol);
			env.setPromptSymbol(symbol);
			break;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE changed from " + env.getMultilineSymbol() + " to " + symbol);
			env.setMultilineSymbol(symbol);
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES changed from " + env.getMorelinesSymbol() + " to " + symbol);
			env.setMorelinesSymbol(symbol);
			break;
		default:
			throw new IllegalArgumentException("No such parameter to make change on!");
		}
	}
	
	private void printSymbol(String part, Environment env) throws IOException{
		switch(part.toUpperCase()){
		case "PROMPT":
			env.writeln("Symbol for PROMPT is " + env.getPromptSymbol());
			break;
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is " + env.getMultilineSymbol());
			break;
		case "MORELINES":
			env.writeln("Symbol for MORELINES is " + env.getMorelinesSymbol());
			break;
		default:
			throw new IllegalArgumentException("No such parameter to print symbol for!");
		}
	}
}
