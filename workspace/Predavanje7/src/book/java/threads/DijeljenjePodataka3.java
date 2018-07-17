package book.java.threads;

public class DijeljenjePodataka3 {

	public static long counter = 0L;
	
	private static class Posao implements Runnable{
		private int n;
		private Object vratar;
		
		public Posao(int n, Object vratar){
			super();
			this.n=n;
			this.vratar = vratar;
		}

		@Override
		public void run() {
			for(int i = 0; i< n; i++){
				synchronized(vratar){
					counter++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		final int brojRadnika = 4;
		Thread[] radnici = new Thread[brojRadnika];
		
		final Object vratar = new Object();
		Posao posao = new Posao(1_000_000,vratar);
		
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
