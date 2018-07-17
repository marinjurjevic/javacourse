package hr.fer.zemris.java.tecaj.hw6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PrimjerDatoteke {

	public static void main(String[] args) {
		String tekst = "Šišćevapčić";
		
		byte[] podaci = tekst.getBytes(StandardCharsets.UTF_8);
		
		String novi = new String(podaci, StandardCharsets.UTF_8);
		
		OutputStream os = null;
		try{
			os = new FileOutputStream(new File("datoteka.txt"));
			for(byte b : podaci){
				os.write(b);
			}
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}finally{
			if(os != null){
				try{
					os.close();
				}catch(IOException ignorable){}
			}
		}
		
		
	}

}
