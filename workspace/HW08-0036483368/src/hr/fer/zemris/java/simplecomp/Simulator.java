package hr.fer.zemris.java.simplecomp;

import java.io.FileNotFoundException;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Simulator mikroračunala za testiranje programa.
 * 
 * @author Marin Jurjevic
 *
 */
public class Simulator {

	/**
	 * Ulazna točka programa.
	 * 
	 * @param args
	 *            putanja do asemblerskog koda
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String source;
		Scanner sc = new Scanner(System.in);

		if (args.length != 1) {

			System.out.println("Please provide path to program code: ");
			source = sc.nextLine();
		} else {
			source = args[0];
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		try {
			ProgramParser.parse(source, comp, creator);
		} catch (FileNotFoundException e) {
			System.err.println("File can not be found!");
			System.exit(-1);
		}

		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		// Izvedi program
		try {
			exec.go(comp);
		} catch (Exception e) {
			System.err.println("An error occured while running the program!");
			e.printStackTrace();
		}

		sc.close();
	}

}
