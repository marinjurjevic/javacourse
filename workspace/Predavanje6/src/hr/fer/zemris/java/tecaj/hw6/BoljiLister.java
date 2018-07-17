package hr.fer.zemris.java.tecaj.hw6;

import java.io.File;

public class BoljiLister {

	private static class Ispis implements ObradaStabla{
		private int level;

		@Override
		public void ulazimUDirektorij(File dir) {
			ispisi(dir);
			level++;
		}

		@Override
		public void izlazimIzDirektorija(File dir) {
			level--;
		}

		@Override
		public void gledamDatoteku(File f) {
			ispisi(f);
		}
		
		private void ispisi(File file){
			if (level == 0){
				System.out.println(file.getAbsolutePath());
			}else{
				System.out.printf("%" + (2*level) + "s%s%n","", file.getName());
			}
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
		
		DirectoryTreeUtil.processDirectoryTree(dir, new Ispis());
	}
	
	
}
