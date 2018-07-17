package hr.fer.zemris.java.tecaj.p3b;

public class Pravokutnik extends GeometrijskiLik {

	private int x;
	private int y;
	private int w;
	private int h;

	public Pravokutnik(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	

	@Override
	public boolean sadrziTocku(int x, int y) {
		if(x < this.x) return false;
		if(y < this.y) return false;
		if(x >= this.x + this.w)return false;
		if(y >= this.y + this.h)return false;

		return true;
	}
	
}
