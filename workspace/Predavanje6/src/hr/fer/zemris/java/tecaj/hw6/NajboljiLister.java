package hr.fer.zemris.java.tecaj.hw6;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class NajboljiLister {

	private static class Ispis implements FileVisitor<Path>{
		private int level;
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			ispisi(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			ispisi(file);
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
		private void ispisi(Path file){
			if (level == 0){
				System.out.println(file.normalize().toAbsolutePath());
			}else{
				System.out.printf("%" + (2*level) + "s%s%n","", file.getFileName());
			}
		}

	}
	
	public static void main(String[] args) throws IOException{
		if(args.length != 1){
			System.out.println("Ocekivao sam jedan argument : direktorij");
		}
		
		Path dir = Paths.get(args[0]);
		if(!Files.isDirectory(dir)){
			System.out.println("Argument mora biti direktorij");
			return;
		}
		
		Files.walkFileTree(dir, new Ispis());
	}
	
	
}
