package hr.fer.zemris.java.simplecomp;

/**
 * Pomoćni razred sa statičkim metodama za dohvat informacija iz deskriptora. Za
 * informacije o sadržaju deskriptora pogledati
 * {@link hr.fer.zemris.java.simplecomp.models.InstructionArgument
 * InstructionArgument}
 * 
 * @author Marin Jurjevic
 *
 */
public class RegisterUtil {

	/**
	 * Izračunava index registra iz danog deskriptora.
	 * 
	 * @param registerDescriptor
	 *            desktiptor registra
	 * @return index registra
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & 0xFF;
	}

	/**
	 * Provjerava je li argument koristi indirektno adresiranje
	 * 
	 * @param registerDescriptor
	 *            desktiptor registra
	 * @return <code>true</code> ako se koristi indirektno adresiranje, inace
	 *         <code>false</code>
	 */
	public static boolean isIndirect(int registerDescriptor) {
		int flag = registerDescriptor >> 24;

		if (flag == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Izračunava pomak registra iz danog deskriptora.
	 * 
	 * @param registerDescriptor
	 *            desktiptor registra
	 * @return pomak registra;
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		int offset = registerDescriptor >> 8;
		return (short) (offset & 0xFFFF);
	}
}
