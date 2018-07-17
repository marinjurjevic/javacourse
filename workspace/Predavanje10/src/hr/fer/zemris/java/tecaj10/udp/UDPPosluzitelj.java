package hr.fer.zemris.java.tecaj10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPPosluzitelj {

	public static void main(String[] args) throws SocketException {
		if(args.length != 1){
			System.out.println("Očekivao sam port");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		
		// Stvori pristupnu tocku posluzitelja
		@SuppressWarnings("resource")
		DatagramSocket dSocket = new DatagramSocket(null);
		dSocket.bind(new InetSocketAddress((InetAddress)null, port));
		
		while(true){
			// Pripremi prijemni spremnik
			byte[] recvBuffer = new byte[40];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			// Čekaj na primitak upita
			try{
				dSocket.receive(recvPacket);
			}catch(IOException e){
				continue;
			}
			
			if(recvPacket.getLength() != 4){
				System.out.println("Primljen neispravan upit od:" + recvPacket.getSocketAddress()+".");
				continue;
			}
			
			short broj1 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset());
			short broj2 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset()+2);
			short result = (short)(broj1+broj2);
			
			
			// Pripremi podatke za poslati natrag klijentu
			byte[] sendBuffer = new byte[2];
			ShortUtil.shortToBytes(result, sendBuffer, 0);
			
			// Pripremi odlazni paket
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
			sendPacket.setSocketAddress(recvPacket.getSocketAddress());
			
			// Šalji
			try{
				dSocket.send(sendPacket);
			}catch(IOException e){
				System.out.println("Greška pri slanju odgovora!");
			}
		}
		
	}
}
