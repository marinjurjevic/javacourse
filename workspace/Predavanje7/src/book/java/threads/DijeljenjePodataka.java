package book.java.threads;

public class DijeljenjePodataka {

	public static volatile long counter = 0L;
	
	private static class Posao implements Runnable{
		private int n;
		
		public Posao(int n){
			super();
			this.n=n;
		}

		@Override
		public void run() {
			for(int i = 0; i< n; i++){
				counter++;
			}
		}
	}
	
	public static void main(String[] args) {
		final int brojRadnika = 2;
		Thread[] radnici = new Thread[brojRadnika];
		Posao posao = new Posao(1_000_000);
		
		for(int i = 0; i< brojRadnika; i++){
			radnici[i] = new Thread(posao);
		}
		
		for(int i = 0; i< brojRadnika; i++){
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
		
		System.out.println("Konacno stanje brojaca: " + counter);
	}

}
