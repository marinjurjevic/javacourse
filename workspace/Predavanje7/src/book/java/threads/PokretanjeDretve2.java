package book.java.threads;

public class PokretanjeDretve2 {

	private static class Posao implements Runnable{
		@Override
		public void run(){
			Thread current = Thread.currentThread();
			System.out.println("Počinjem: " + current);
			
			try{
				Thread.sleep(1000);
			}catch(InterruptedException ignorable){
			}
			System.out.println("Pozdrav iz nove dretve!");
			System.out.println("Završavam: " + current);
		}
	}
	
	public static void main(String[] args){
		Posao posao = new Posao();
		new Thread(posao).start();
		
		System.out.println("Završavam main...");
	}

}
