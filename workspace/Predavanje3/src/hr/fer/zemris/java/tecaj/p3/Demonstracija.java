package hr.fer.zemris.java.tecaj.p3;

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
			lik.popuniLik(slika);
		}
		
		//slika.nacrtajSliku(System.out);
		
		PrikaznikSlike.prikaziSliku(slika, 4);
	}

}
