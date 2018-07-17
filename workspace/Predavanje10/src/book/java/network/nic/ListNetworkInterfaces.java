package book.java.network.nic;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import book.java.network.Util;

public class ListNetworkInterfaces {

	public static void main(String[] args) throws SocketException {
		Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
		
		while(nics.hasMoreElements()){
			NetworkInterface nic = nics.nextElement();
			showInfo(nic);
			System.out.println();
		}
	}
	
	private static void showInfo(NetworkInterface nic) throws SocketException{
		System.out.printf("Name: %s%n", nic.getName());
		System.out.printf("Index: %d%n", nic.getIndex());
		System.out.printf("Description: %s%n", nic.getDisplayName());
		
		System.out.printf("MTU: %d%n", nic.getMTU());
		System.out.printf("Loopback: %b%n", nic.isLoopback());
		System.out.printf("PointToPoint: %b%n", nic.isPointToPoint());
		
		System.out.printf("Up: %b%n", nic.isUp());
		System.out.printf("Supports multicast: %b%n", nic.supportsMulticast());
		System.out.printf("Virtual: %b%n", nic.isVirtual());
		
		System.out.printf("Hardware address: %s%n", Util.toHex(nic.getHardwareAddress()));
		
		Enumeration<InetAddress> addrs = nic.getInetAddresses();
		while(addrs.hasMoreElements()){
			InetAddress addr = addrs.nextElement();
			System.out.printf("  Internet address: %s%n",Util.addrToString(addr));
		}
		
		System.out.println("  ----");
		for(InterfaceAddress addr : nic.getInterfaceAddresses()){
			System.out.printf("  Internet address: %s/%d brd %s%n", 
					Util.addrToString(addr.getAddress()),
					addr.getNetworkPrefixLength(),
					Util.addrToString(addr.getBroadcast())
					);
		}
	}
}
