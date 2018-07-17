package book.java.network;

import java.net.InetAddress;

public class Util {

	public static String toHex(byte[] data, int group, char separator){
		if(data == null) return "-";
		if(data.length % group != 0){
			throw new IllegalArgumentException("Duljina adrese nije višekratnik grupe");
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<data.length ; i++){
			if(i!=0 && (i%group)==0)sb.append(separator);
			sb.append(String.format("%02X", data[i]));
		}
		return sb.toString();
	}
	
	public static String toHex(byte[] data){
		return toHex(data,1,':');
	}
	
	public static String toDec(byte[] data){
		if(data == null) return "";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<data.length;i++){
			if(i!=0)sb.append('.');
			sb.append(String.format("%d", data[i] &0xFF));
		}
		return sb.toString();
	}
	
	public static String addrToString(InetAddress addr){
		if(addr == null) return "-";
		byte[] data = addr.getAddress();
		if(data.length == 4)return toDec(data);
		return toHex(data,2,':');
	}
}
