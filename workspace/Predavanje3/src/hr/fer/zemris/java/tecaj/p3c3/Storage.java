package hr.fer.zemris.java.tecaj.p3c3;

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
		return new ValueProvider(){
			;
			@Override
			public double getValue() {
				return value;
			}
			
			@Override
			public double getSquared() {
				// TODO Auto-generated method stub
				return value*value;
			}
			
			@Override
			public double getSquareRoot() {
				// TODO Auto-generated method stub
				return Math.sqrt(value);
			}
		};
	}
}
