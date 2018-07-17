package hr.fer.zemris.java.tecaj.p3b;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demonstracija {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[]{
				new Pravokutnik(0,0,50,50),
				new Pravokutnik(50,50,25,25)
		};
		
		Slika slika = new Slika(100, 100);
		
		for(GeometrijskiLik lik : likovi){
			renderiraj(slika,lik);
		}
		
		//slika.nacrtajSliku(System.out);
		
		PrikaznikSlike.prikaziSliku(slika, 4);
	}
	
	public static void renderiraj(Slika slika, SadrziocTocaka t){
		int sirina = slika.getSirina();
		
		for(int y = 0, visina = slika.getVisina(); y < visina; y++){
			for(int x = 0; x < sirina; x++){
				if(t.sadrziTocku(x, y)){
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}
