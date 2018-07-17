package hr.fer.zemris.java.tecaj.p05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Demo3 {

	public static class Zaposlenik implements Comparable<Zaposlenik>{
		private String ime;
		private String prezime;
		private String sifra;
		
		public static final Comparator<Zaposlenik> PO_IMENU = (z1,z2) -> z1.getIme().compareTo(z2.getIme());
		public static final Comparator<Zaposlenik> PO_PREZIMENU = (z1,z2) -> z1.getPrezime().compareTo(z2.getPrezime());
		public static final Comparator<Zaposlenik> PO_SIFRI = (z1,z2) -> z1.getSifra().compareTo(z2.getSifra());
		
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
		
		@Override
		public int compareTo(Zaposlenik o){
			return -this.ime.compareTo(o.sifra);
		}
	}

	private static class MojKomparator implements Comparator<Zaposlenik>{
		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2){
			return o1.getIme().substring(1).compareTo(o2.getIme().substring(1));
		}
	}
	
	private static class KomparatorPoImenu implements Comparator<Zaposlenik>{
		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2){
			return o1.getIme().compareTo(o2.getIme());
		}
	}
	private static class KomparatorPoPrezimenu implements Comparator<Zaposlenik>{
		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2){
			return o1.getPrezime().compareTo(o2.getPrezime());
		}
	}
	
	private static class OkreniKomparator<Z> implements Comparator<Z>{
		private Comparator<Z> original;
		
		public OkreniKomparator(Comparator<Z> original) {
			this.original = original;
		}
		
		@Override
		public int compare(Z o1, Z o2) {
			return -original.compare(o1, o2);
		}
	}
	
	private static class KompozitniKomparator<Z> implements Comparator<Z>{
		private List<Comparator<Z>> komparatori;
		
		@SafeVarargs
		public KompozitniKomparator(Comparator<Z> prvi, Comparator<Z> ... ostali){
			komparatori = new ArrayList<>();
			komparatori.add(prvi);
			for(Comparator<Z> k : ostali){
				komparatori.add(k);
			}
		}
		
		@Override
		public int compare(Z o1, Z o2) {
			for(Comparator<Z> trenutni : komparatori){
				int rez = trenutni.compare(o1, o2);
				
				if(rez != 0){
					return rez;
				}
			}
			return 0;
		}
	}
	
	public static void main(String[] args) {
//		Comparator<Zaposlenik> reverzni = Collections.reverseOrder(new KomparatorPoImenu());
//		Comparator<Zaposlenik> reverzni2 = new KomparatorPoImenu().reversed();
//		
//		Comparator<Zaposlenik> kompozitni = 
//				new KomparatorPoImenu().reversed().thenComparing(new KomparatorPoPrezimenu());
//				
//		Set<Zaposlenik> skup = new TreeSet<Zaposlenik>(
//				new KompozitniKomparator<>(
//						new OkreniKomparator<>(new KomparatorPoImenu()),
//						new KomparatorPoPrezimenu()
//				));
		
		Set<Zaposlenik> skup = new TreeSet<Zaposlenik>(
				Zaposlenik.PO_IMENU.reversed()
				.thenComparing(Zaposlenik.PO_PREZIMENU)
		);
		
		skup.add(new Zaposlenik("Ante","Antić","0001"));
		skup.add(new Zaposlenik("Željko","Željkić","0002"));
		skup.add(new Zaposlenik("Janko","Jankić","0003"));
		skup.add(new Zaposlenik("Joško","Joškić","0004"));
		skup.add(new Zaposlenik("Janko","Babić","0005"));
		
		Zaposlenik zap = new Zaposlenik("Janko","Jankić","0003");
		
		skup.forEach(System.out::println);
		
		System.out.println("Sadrzim " + zap + "? " + skup.contains(zap));
	}
	
	
}
