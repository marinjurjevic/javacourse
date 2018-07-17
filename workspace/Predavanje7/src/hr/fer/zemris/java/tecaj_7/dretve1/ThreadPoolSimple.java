package hr.fer.zemris.java.tecaj_7.dretve1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolSimple {
	
	private BlockingQueue<Runnable> jobs = new LinkedBlockingQueue<>();
	private Thread[] workers;
	private final static Runnable RED_PILL = ()->{};
	
	public ThreadPoolSimple() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	public ThreadPoolSimple(int numberOfWorkers){
		workers = new Thread[numberOfWorkers];
		
		for(int i = 0; i<numberOfWorkers; i++){
			workers[i] = new Thread( ()->{
				while(true){
					Runnable job = nextJob();
					if(job == RED_PILL){
						break;
					}
					job.run();
				}
			});
			workers[i].start();
		}
	}
	
	private Runnable nextJob(){
		while(true){
			try{
				return jobs.take();
			}catch(InterruptedException e){
			}
		}
	}
	
	public void shutdown(){
		for(int i = 0; i<workers.length; i++){
			addJob(RED_PILL);
		}
	}
	public void addJob(Runnable job){
		while(true){
			try{
				jobs.put(job);
			break;
			}catch(InterruptedException e){
			}
		}
	}
}
