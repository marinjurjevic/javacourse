package hr.fer.zemris.java.tecaj_7.ispis3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	static class Upis {
		Predmet predmet;
		Student student;

		public Upis(Predmet predmet, Student student) {
			super();
			this.predmet = predmet;
			this.student = student;
		}
	}

	static class Predmet {
		String sifra;
		String naziv;

		public Predmet(String sifra, String naziv) {
			super();
			this.sifra = sifra;
			this.naziv = naziv;
		}
	}

	static class Student {
		String jmbag;
		String ime;
		String prezime;

		public Student(String jmbag, String ime, String prezime) {
			super();
			this.jmbag = jmbag;
			this.ime = ime;
			this.prezime = prezime;
		}
	}

	public static void main(String[] args) throws IOException {

		if (args.length != 3) {
			System.err.println("Očekujem tri argumenta.");
			System.exit(0);
		}

		Map<String, Student> studentMap = ucitajStudente(args[0]);
		Map<String, Predmet> predmetMap = ucitajPredmete(args[1]);
		List<Upis> upisi = ucitajUpise(args[2], studentMap, predmetMap);

		for (Upis u : upisi) {
			System.out.println(
					String.format("| %-40s | %-20s | %-20s |", u.predmet.naziv, u.student.prezime, u.student.ime));
		}

	}

	private static Map<String, Predmet> ucitajPredmete(String fileName) {
		Map<String, Predmet> predmet = new HashMap<>();
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
				if(elementi.length != 2) throw new RuntimeException("Format datoteke nije ispravan! Očekivao sam 2 argumenta a pronašao ih: " + elementi.length);
				
				Predmet p = new Predmet(elementi[0],elementi[1]);
				predmet.put(p.sifra,p);
			}
		}catch(IOException e){
			
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(IOException ignorable){}
			}
		}
		
		return predmet;
	}

	private static String ukloniKomentar(String redak){

		if(redak.startsWith("#"))redak = "";
		int pos = redak.indexOf('%');
		if(pos != -1){
			redak = redak.substring(0, pos).trim();
		}
		if(redak.contains("REM"))redak = "";
		
		return redak;
	}
	private static Map<String, Student> ucitajStudente(String fileName) {
		ObradaDatoteke<Map<String,Student>> obrada = new ObradaDatoteke<Map<String,Student>>() {
			Map<String, Student> mapa = new HashMap<>();
			
			@Override
			protected void obradiRedak(String[] elementi) {
				Student s = new Student(elementi[0],elementi[2],elementi[1]);
				mapa.put(s.jmbag, s);
			}
			
			@Override
			protected Map<String, Student> dohvatiRezultat() {
				return mapa;
			}
			
			@Override
			protected int brojStupaca() {
				// TODO Auto-generated method stub
				return 3;
			}
		};
		
		return obrada.obradi(fileName);
	}
	

	private static List<Upis> ucitajUpise(String fileName, Map<String, Student> studentMap,
			Map<String, Predmet> predmetMap) {
		
		ObradaDatoteke<List<Upis>> obrada = new ObradaDatoteke<List<Upis>>() {
			List<Upis> upisi = new ArrayList<>();
			
			@Override
			protected void obradiRedak(String[] elementi) {
				Student s = studentMap.get(elementi[0]);
				if(s == null)throw new RuntimeException("Student s JMBAGom " + elementi[0] + " ne postoji");
				
				Predmet p = predmetMap.get(elementi[1]);
				if(p == null)throw new RuntimeException("Predmet sa sifrom " + elementi[1] + " ne postoji");
				
				upisi.add(new Upis(p,s));
			}
			
			@Override
			protected List<Upis> dohvatiRezultat() {
				return upisi;
			}
			
			@Override
			protected int brojStupaca() {
				return 2;
			}
		};
		
		return obrada.obradi(fileName);
	}
}
