package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * 
 * Implementacija jednostavne memorije računala. Memorija je može sadržavati
 * bilo koje objekte.
 * 
 * @author Marin Jurjevic
 *
 */
public class MemoryImpl implements Memory {

	/**
	 * privatno polje koje predstavlja sadrzaj memorijskih lokacija
	 */
	private Object[] memory;

	/**
	 * Stvara novu memoriju odgovarajuće veličine. Minimalna dopustena veličina
	 * memorije je 1.
	 * 
	 * @param size
	 *            veličina memorija
	 * @throws IllegalArgumentException
	 *             ako je veličina manje od 1
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Veličina memorije mora biti pozitivna vrijednost!");
		}

		memory = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location > memory.length - 1) {
			throw new IndexOutOfBoundsException("Invalid memory location!");
		}

		memory[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		if (location < 0 || location > memory.length - 1) {
			throw new IndexOutOfBoundsException("Invalid memory location!");
		}

		return memory[location];
	}

}
