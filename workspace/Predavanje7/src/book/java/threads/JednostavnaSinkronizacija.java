package book.java.threads;

import java.util.Random;

public class JednostavnaSinkronizacija {

	private static class Posao implements Runnable{
		private double[] polje;
		private int pocetniIndeks;
		private int zavrsniIndeks;
		
		public Posao(double[] polje, int pocetniIndeks, int zavrsniIndeks) {
			super();
			this.polje = polje;
			this.pocetniIndeks = pocetniIndeks;
			this.zavrsniIndeks = zavrsniIndeks;
		}

		@Override
		public void run() {
			Random rand = new Random();
			
			for(int i = pocetniIndeks; i<zavrsniIndeks; i++){
				polje[i]=rand.nextDouble();
			}
		}
	}
	
	public static void main(String[] args) {
		double[] polje = new double[1024*64];
		
		final int brojRadnika = 4;
		Thread[] radnici = new Thread[brojRadnika];
		
		//stvori poslove i opisnike dretvi
		for(int i = 0; i<brojRadnika;i++){
			Posao posao = new Posao(
					polje,
					polje.length*i/brojRadnika,
					polje.length*(i+1)/brojRadnika);
			radnici[i] = new Thread(posao);
		}
		
		//pokreni dretve
		for(int i =0;i<brojRadnika;i++){
			radnici[i].start();
		}
		
		for(int i = 0;i<brojRadnika;i++){
			while(true){
				try{
					radnici[i].join();
					break;
				}catch(InterruptedException e){
					// joinaj opet
				}
			}
		}
		
		System.out.println("Polje napunjeno!");
	}

}
