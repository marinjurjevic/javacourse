package hr.fer.zemris.java.tecaj.p05;

import java.util.HashSet;
import java.util.Set;

public class Demo2 {

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
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((ime == null) ? 0 : ime.hashCode());
			result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
			result = prime * result + ((sifra == null) ? 0 : sifra.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Zaposlenik other = (Zaposlenik) obj;
			if (ime == null) {
				if (other.ime != null)
					return false;
			} else if (!ime.equals(other.ime))
				return false;
			if (prezime == null) {
				if (other.prezime != null)
					return false;
			} else if (!prezime.equals(other.prezime))
				return false;
			if (sifra == null) {
				if (other.sifra != null)
					return false;
			} else if (!sifra.equals(other.sifra))
				return false;
			return true;
		}
		
		
	}

	public static void main(String[] args) {
		Set<Zaposlenik> skup = new HashSet<>();
		
		skup.add(new Zaposlenik("Ante","Antić","0001"));
		skup.add(new Zaposlenik("Željko","Željkić","0002"));
		skup.add(new Zaposlenik("Janko","Jankić","0003"));
		skup.add(new Zaposlenik("Joško","Joškić","0004"));
		
		Zaposlenik zap = new Zaposlenik("Janko","Jankić","0003");
		
		skup.forEach(System.out::println);
		
		System.out.println("Sadrzim " + zap + "? " + skup.contains(zap));
	}
}
