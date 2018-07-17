package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija podatkovnog dijela jednostavno mikroračunala. Računalo se
 * sastoji od uobičajenih dijelova: registara i memorije. Implemntacija nudi
 * dohvat navedenih dijelova kroz standardne gettere. Registri opće namjene (kao
 * i sama memorija) umjesto jednostavnih brojeva mogu pamtiti proizvoljne
 * objekte.
 * 
 * @author Marin Jurjevic
 *
 */
public class ComputerImpl implements Computer {

	/**
	 * privatna reference na memoriju računala
	 */
	private Memory memory;

	/**
	 * privatna reference na registre računala
	 */
	private Registers registers;

	public ComputerImpl(int memorySize, int numberOfRegisters) {
		memory = new MemoryImpl(memorySize);
		registers = new RegistersImpl(numberOfRegisters);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
