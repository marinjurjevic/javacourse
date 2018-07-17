package hr.fer.zemris.java.tecaj10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPKlijentString {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		if (args.length != 3) {
			System.out.println("Ocekivao sam host port broj1 broj2");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		// Pripremi podatke upita koje treba poslati:
		byte[] tekst = args[2].getBytes();
		short duljina = (short)tekst.length;
		byte[] podatci = new byte[duljina+2];
		ShortUtil.shortToBytes(duljina, podatci, 0);
		
		for(int i = 0; i<tekst.length;i++){
			podatci[i+2] = tekst[i];
		}

		// Stvori pristupnu tocku klijenta
		DatagramSocket dSocket = new DatagramSocket();

		// Umetni podatke u paket i paketu postavi adresu i port posluzitelja
		DatagramPacket packet = new DatagramPacket(podatci, podatci.length);

		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));

		// Posalji upit posluzitelju..
		System.out.println("Saljem upit...");
		try {
			dSocket.send(packet);
		} catch (IOException e) {
			System.out.println("Ne mogu poslati upit.");
		}

		dSocket.close();
	}
}
