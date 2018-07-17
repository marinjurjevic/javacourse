package hr.fer.zemris.java.tecaj.hw6.observer2;

public class IntegerStorageChange {

	private final IntegerStorage storage;

	private final int oldValue;

	private final int newValue;

	public IntegerStorageChange(IntegerStorage storage, int oldValue) {
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = storage.getValue();
	}

	public IntegerStorage getStorage() {
		return storage;
	}

	public int getOldValue() {
		return oldValue;
	}

	public int getNewValue() {
		return newValue;
	}

}
