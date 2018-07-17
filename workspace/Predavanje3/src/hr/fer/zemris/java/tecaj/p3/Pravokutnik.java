package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

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
	
	@Override
	public void popuniLik(Slika slika) {
		for(int y = this.y, ymax = this.y+this.h; y<ymax; y++){
			for(int x = this.x, xmax = this.x + this.w; x<xmax; x++){
				slika.upaliTocku(x, y);
			}
		}
	}
}
