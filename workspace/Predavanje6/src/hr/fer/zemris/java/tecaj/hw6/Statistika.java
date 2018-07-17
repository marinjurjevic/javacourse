package hr.fer.zemris.java.tecaj.hw6;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistika {

	private static class ExtensionInfo{
		private String extension;
		private long totalSize;
		private int count;
		
		public ExtensionInfo(String extension) {
			super();
			this.extension = extension;
		}

		public String getExtension() {
			return extension;
		}

		public long getTotalSize() {
			return totalSize;
		}

		public int getCount() {
			return count;
		}
		
		public double getAverageSize(){
			return  totalSize/(double)count;
		}
		
		private void update(long size){
			totalSize += size;
			count++;
		}
		
	}
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Ocekivao sam jedan argument : direktorij");
		}
		
		File dir = new File(args[0]);
		if(!dir.isDirectory()){
			System.out.println("Argument mora biti direktorij");
			return;
		}
		
		Map<String,ExtensionInfo> stats = calcStats(dir);
		
		List<ExtensionInfo> statsList = new ArrayList<>(stats.values());
		Collections.sort(statsList, (s1,s2)-> Double.compare(s1.getAverageSize(),s1.getAverageSize()));
		
		for(ExtensionInfo e : statsList){
			System.out.printf(
					"%s, %d, %d, %f%n, args",
					e.getExtension(),
					e.getCount(),
					e.getTotalSize(),
					e.getAverageSize()
				);
		}
		
	}
	private static Map<String, ExtensionInfo> calcStats(File dir) {
		Map<String, ExtensionInfo> stats = new HashMap<>();
		
		calcStatsRecursive(dir, stats);

		return stats;
	}
	
	private static void calcStatsRecursive(File dir, Map<String, ExtensionInfo> stats) {
		File[] children = dir.listFiles();
		
		if(children == null){
			return;
		}
		
		for(File f : children){
			if(f.isFile()){
				updateMap(f,stats);
			}else if(f.isDirectory()){
				calcStatsRecursive(f, stats);
			}
		}
		
	}
	
	private static void updateMap(File f, Map<String, ExtensionInfo> stats) {
		String fileName = f.getName();
		
		int pos = fileName.lastIndexOf('.');
		if( pos < 1 || pos == fileName.length()-1){
			return;
		}
		
		String ext = fileName.substring(pos+1).toLowerCase();
		
		ExtensionInfo einfo = stats.get(ext);
		if(einfo == null){
			einfo = new ExtensionInfo(ext);
			stats.put(ext, einfo);
		}
		
		einfo.update(f.length());
	}
	
	
	

}
