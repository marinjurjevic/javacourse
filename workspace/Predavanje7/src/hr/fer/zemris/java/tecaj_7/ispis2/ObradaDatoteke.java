package hr.fer.zemris.java.tecaj_7.ispis2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ObradaDatoteke {

	private static String ukloniKomentar(String redak){

		if(redak.startsWith("#"))redak = "";
		int pos = redak.indexOf('%');
		if(pos != -1){
			redak = redak.substring(0, pos).trim();
		}
		if(redak.contains("REM"))redak = "";
		
		return redak;
	}
	
	public static <T> T obradi(String fileName, IObrada<T> obrada) {
		int ocekivaniBrojElemenata = obrada.brojStupaca();
		BufferedReader br = null;
		
		try{
			br =new BufferedReader(
					new InputStreamReader(
							new BufferedInputStream(
									new FileInputStream(fileName)),
							StandardCharsets.UTF_8));
			
			while(true){
				String redak = br.readLine();
				
				if(redak==null)break;
				redak = ukloniKomentar(redak.trim());
				if(redak.isEmpty())continue;
				
				String[] elementi = redak.split("\t");
				if(elementi.length != ocekivaniBrojElemenata) throw new RuntimeException("Format datoteke nije ispravan! Očekivao sam 3 argumenta a pronašao ih: " + elementi.length);
				
				obrada.obradiRedak(elementi);
			}
		}catch(IOException e){
			
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(IOException ignorable){}
			}
		}
		
		return obrada.dohvatiRezultat();
	}
}
