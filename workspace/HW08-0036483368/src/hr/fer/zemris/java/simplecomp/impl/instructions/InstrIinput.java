package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija <code>iinput</code> učitava cijeli broj sa standardnog ulaza i
 * zapisuje ga na danu memorijsku lokaciju. Ako učitani broj nije tipa Integer,
 * zastavica <code>flag</code> će se postaviti na false i sadržaj <b>neće</b>
 * biti upisan na traženu lokaciju, a ako je sve u redu s ulazom on će biti
 * zapisan na traženu lokaciju i zastavica će biti postavljena na true.
 * Instrukcija <b>ne podržava indirektno adresiranje </b> te radi isključivo
 * izravno s memorijskim lokacijama.</br>
 * 
 * Primjer poziva instrukcije:
 * 
 * <pre>
 *  iinput lokacija
 * </pre>
 * 
 * @author Marin Jurjevic
 *
 */
public class InstrIinput implements Instruction {

	/**
	 * adresa memorijske lokacije na koju će se spremiti cijeli broj
	 */
	private int memLokacija;

	/**
	 * Stvara novu naredbu za unos sadržaja sa standardnog ulaza
	 * 
	 * @param arguments
	 *            lokacija na koju će se spremiti broj
	 */
	public InstrIinput(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}

		this.memLokacija = (Integer) arguments.get(0).getValue();

	}

	public boolean execute(Computer computer) {
		@SuppressWarnings("resource") // ne zatvarati, puca iznimka inace!
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();

		try {
			computer.getMemory().setLocation(memLokacija, Integer.parseInt(line));
			computer.getRegisters().setFlag(true);
		} catch (RuntimeException e) {
			computer.getRegisters().setFlag(false);
		}

		return false;
	}
}