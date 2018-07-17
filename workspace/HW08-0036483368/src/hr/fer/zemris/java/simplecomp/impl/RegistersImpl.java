package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija registara mikroračunala. Na raspolaganju su registri opće
 * namjene, programsko brojilo te jedna zastavica. Registri mogu sadržavati
 * proizvoljne objekte bez ograničenja na tip razreda.
 * 
 * @author Marin Jurjevic
 *
 */
public class RegistersImpl implements Registers {

	/**
	 * privatno polje koje predstavlja vrijednosti svih registara opće namjene
	 */
	private Object[] registers;

	/**
	 * primjerak programskog brojila
	 */
	private int progBrojilo;

	/**
	 * zastavica
	 */
	private boolean flag;

	public RegistersImpl(int regsLen) {
		if (regsLen < 1) {
			throw new IllegalArgumentException("Broj registara mora biti pozitivan broj!");
		}

		registers = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		if (index < 0 || index > registers.length - 1) {
			throw new IndexOutOfBoundsException("Ne postoji registar sa traženim indeksom");
		}

		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		if (index < 0 || index > registers.length - 1) {
			throw new IndexOutOfBoundsException("Ne postoji registar sa traženim indeksom");
		}

		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return progBrojilo;
	}

	@Override
	public void setProgramCounter(int value) {
		progBrojilo = value;
	}

	@Override
	public void incrementProgramCounter() {
		progBrojilo++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

}
