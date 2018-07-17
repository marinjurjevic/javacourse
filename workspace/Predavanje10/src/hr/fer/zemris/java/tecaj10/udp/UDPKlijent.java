package hr.fer.zemris.java.tecaj10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPKlijent {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		if(args.length != 4){
			System.out.println("Ocekivao sam host port broj1 broj2");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		short broj1 = Short.parseShort(args[2]);
		short broj2 = Short.parseShort(args[3]);
		
		// Pripremi podatke upita koje treba poslati:
		byte[] podatci = new byte[4];
		ShortUtil.shortToBytes(broj1, podatci, 0);
		ShortUtil.shortToBytes(broj2, podatci, 2);
		
		// Stvori pristupnu tocku klijenta
		DatagramSocket dSocket = new DatagramSocket();
		
		// Umetni podatke u paket i paketu postavi adresu i port posluzitelja
		DatagramPacket packet = new DatagramPacket(podatci, podatci.length);
		
		packet.setSocketAddress(new InetSocketAddress(
				InetAddress.getByName(hostname), 
				port));
		
		// postavi timeout od 4 sekunde na primitak odgovora
		dSocket.setSoTimeout(4000);
		
		// Salji upite sve dok ne dobijes odgovor
		boolean answerRecieved = false;
		while(!answerRecieved){
			
			// Posalji upit posluzitelju..
			System.out.println("Saljem upit...");
			try{
				dSocket.send(packet);
			}catch(IOException e){
				System.out.println("Ne mogu poslati upit.");
				break;
			}
			
			// Pripremi prazan paket za primitak odgovora
			byte[] recvBuffer = new byte[4];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			// Cekaj odgovor
			try{
				dSocket.receive(recvPacket);
			}catch(SocketTimeoutException ste){
				// Ako je istekao timeout, ponovno salji
				continue;
			}catch(IOException e){
				// U Slucaju drugih pogresaka - dogovoriti se sto raditi
				
				continue;
			}
			
			
			// Analiziraj sadrzaj paketa
			if(recvPacket.getLength() != 2){
				System.out.println("Primljen je odgovor neocekivane duljine");
				break;
			}
			
			// Ispisi rezultat, i prekini slanje retransmisija:
			System.out.println("Rezultat je " + ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset()));
			
			answerRecieved = true;
			// break;
		}
		
		dSocket.close();
	}
}
