package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik {

	public abstract boolean sadrziTocku(int x, int y);
	
	public void popuniLik(Slika slika){
		int sirina = slika.getSirina();
		
		for(int y = 0, visina = slika.getVisina(); y < visina; y++){
			for(int x = 0; x < sirina; x++){
				if(this.sadrziTocku(x, y)){
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
