package hr.fer.zemris.java.tecaj.p05;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {

	public static class Zaposlenik {
		private String ime;
		private String prezime;
		private String sifra;

		public Zaposlenik(String ime, String prezime, String sifra) {
			super();
			this.ime = ime;
			this.prezime = prezime;
			this.sifra = sifra;
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
		
		@Override
		public String toString() {
			return "(" + sifra +")" + prezime + "," + ime;
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
		
		lista.add(new Zaposlenik("Ante","Antić","0001"));
		lista.add(new Zaposlenik("Željko","Željkić","0002"));
		lista.add(new Zaposlenik("Janko","Jankić","0003"));
		lista.add(new Zaposlenik("Joško","Joškić","0004"));
		
		Zaposlenik zap = new Zaposlenik("Janko","Jankić","0003");
		
		lista.forEach(System.out::println);
		
		Zaposlenik zap1 = lista.get(2);
		System.out.println("Sadrzim " + zap1 + "? " + lista.contains(zap1));
		System.out.println("Sadrzim " + zap1 + "? " + lista.contains(zap));
	}
}
