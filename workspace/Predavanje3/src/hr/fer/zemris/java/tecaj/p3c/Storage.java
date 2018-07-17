package hr.fer.zemris.java.tecaj.p3c;

public class Storage {

	private double value;

	public Storage(double value) {
		super();
		this.value = value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public ValueProvider getValueProvider(){
		return new ValueProviderImpl(this);
	}
	
	private static class ValueProviderImpl implements ValueProvider{
		private Storage myStorage;

		public ValueProviderImpl(Storage storage) {
			myStorage = storage;
		}
		
		@Override
		public double getValue() {
			return myStorage.value;
		}
		
		@Override
		public double getSquared() {
			return myStorage.value*myStorage.value;
		}
		
		@Override
		public double getSquareRoot() {
			return Math.sqrt(myStorage.value);
		}
	}
}
