package hr.fer.zemris.java.tecaj.p05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Demo5 {

	public static class Zaposlenik {
		private String ime;
		private String prezime;
		private String sifra;
		private int ocjena;
		

		public Zaposlenik(String ime, String prezime, String sifra, int ocjena) {
			super();
			this.ime = ime;
			this.prezime = prezime;
			this.sifra = sifra;
			this.ocjena = ocjena;
		}

		public String getIme() {
			return ime;
		}

		public String getPrezime() {
			return prezime;
		}

		public String getSifra() {
			return sifra;
		}
		
		public int getOcjena() {
			return ocjena;
		}
		
		@Override
		public String toString() {
			return "(" + sifra +") " + prezime + "," + ime + ", ocjena = " + ocjena;
		}
		
		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof Zaposlenik)){
				return false;
			}
			Zaposlenik drugi = (Zaposlenik)obj;
			return this.sifra.equals(drugi.sifra);
		}
	}

	public static void main(String[] args) {
		List<Zaposlenik> lista = new ArrayList<>();
		
		lista.add(new Zaposlenik("Ante","Antić","0001", 3));
		lista.add(new Zaposlenik("Željko","Željkić","0002", 1));
		lista.add(new Zaposlenik("Janko","Jankić","0003", 5));
		lista.add(new Zaposlenik("Joško","Joškić","0004", 4));
		lista.add(new Zaposlenik("Pero","Perić","0005", 2));
		
		lista.stream().forEach(System.out::println);
		System.out.println("***********************************");
		
		lista.stream()
			.filter(z -> z.getOcjena()>2)
			.forEach(System.out::println);
		System.out.println("***********************************");
		
		lista.stream()
		.filter(z -> z.getOcjena()>2)
		.sorted((z1,z2) -> -Integer.compare(z1.getOcjena(), z2.getOcjena()))
		.forEach(System.out::println);
		
		System.out.println("***********************************");
		
		lista.stream()
		.filter(z -> z.getOcjena()>2)
		.sorted((z1,z2) -> -Integer.compare(z1.getOcjena(), z2.getOcjena()))
		.map(z -> z.getSifra())
		.forEach(System.out::println);
		
		System.out.println("***********************************");
		
		List<String> sifraZaposlenika =lista.stream()
		.filter(z -> z.getOcjena()>2)
		.sorted((z1,z2) -> -Integer.compare(z1.getOcjena(), z2.getOcjena()))
		.map(z -> z.getSifra())
		.collect(Collectors.toList());
		
		System.out.println(sifraZaposlenika);
	}
}
