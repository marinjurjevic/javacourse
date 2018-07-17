package hr.fer.zemris.java.hw1;

/**
 * Demonstration of basic operations with list.
 * Program will implement operations size, 
 * insert and sort.
 * @author Marin Jurjeviæ
 */
public class ProgramListe {
	
	/**
	 * Class CvorListe is basic unit of a list.
	 * It has data element and reference to the next
	 * connected node in the list.
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}
	
	/**
	 * Starting point of this program.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Pero");
		cvor = ubaci(cvor, "Andro");
		cvor = ubaci(cvor, "Miro");
		cvor = ubaci(cvor, "Zlatko");
		
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}
	
	/**
	 * Returns size of this list.
	 * @param cvor first node in the list
	 * @return number of elements in this list
	 */
	private static int velicinaListe(CvorListe cvor) {
		int velicina = 0;
		while(cvor!=null){
			cvor=cvor.sljedeci;
			velicina+=1;
		}
		return velicina;
	}
	
	/**
	 * This method will insert new node in the list and return first node.
	 * @param prvi first node in the list
	 * @param podatak data we want to insert
	 * @return first node in the list
	 */
	private static CvorListe ubaci(CvorListe prvi, String podatak) {
		
		CvorListe cvor = prvi;
		CvorListe novi = new CvorListe();
		
		if(prvi == null){
			novi.podatak = podatak;
			novi.sljedeci = null;
			return novi;
		}
		
		while(cvor.sljedeci!=null){
			cvor=cvor.sljedeci;
		}
		
		cvor.sljedeci = novi;
		novi.sljedeci = null;
		novi.podatak = podatak;
		
		return prvi;
	}
	
	/**
	 * Prints out list starting from the first node.
	 * @param cvor first node in the list
	 */
	private static void ispisiListu(CvorListe cvor) {
		while(cvor!=null){
			System.out.println(cvor.podatak);
			cvor=cvor.sljedeci;
		}
	}
	
	/**
	 * Sorts the list using bubble-sort algorithm.
	 * @param cvor first node in the list
	 * @return first node in the list
	 */
	private static CvorListe sortirajListu(CvorListe cvor) {
		if( velicinaListe(cvor) < 2)
			return cvor;
		
		boolean sorted = false;
		CvorListe prvi = cvor;
		
		while(sorted == false){
			sorted = true;
			while(cvor.sljedeci != null){
				if(cvor.podatak.compareTo(cvor.sljedeci.podatak) > 0){
					String temp = cvor.podatak;
					cvor.podatak = cvor.sljedeci.podatak;
					cvor.sljedeci.podatak = temp;
					sorted = false;
				}
				cvor = cvor.sljedeci;
			}
			cvor = prvi;
		}
		
		return prvi;
	}
}
