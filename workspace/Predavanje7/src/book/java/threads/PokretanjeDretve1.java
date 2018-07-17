package book.java.threads;

public class PokretanjeDretve1 {

	private static class PosaoDretva extends Thread{
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
		new PosaoDretva().start();
		
		System.out.println("Završavam main...");
	}

}
