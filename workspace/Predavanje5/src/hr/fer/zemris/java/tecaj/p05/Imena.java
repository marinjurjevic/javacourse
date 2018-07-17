package hr.fer.zemris.java.tecaj.p05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Imena {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		
		Map<String,Integer> imena = new HashMap<>();
		
		while(true){
			String redak = br.readLine();
			
			if(redak == null){
				break;
			}
			
			redak = redak.trim();
			if(redak.isEmpty()){
				continue;
			}
			
			if(redak.equals("quit")){
				break;
			}
			
			Integer trenutni = imena.get(redak);
			
			if(trenutni == null){
				trenutni = 1;
			}else{
				trenutni+=1;
			}
			
			imena.put(redak, trenutni);
		}
		
		imena.forEach((k,v) -> System.out.println(k + " => " + v) );
		imena.entrySet().forEach(par -> System.out.println(par.getKey() + " => " + par.getValue()));
		
	}
}
