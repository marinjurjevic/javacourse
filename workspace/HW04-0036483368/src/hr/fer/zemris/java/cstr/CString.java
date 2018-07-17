package hr.fer.zemris.java.cstr;

import java.util.Arrays;

/**
 * CString class offers similar functionality as the old official implementation
 * of the String class (before Java 7 update 6). It offers various methods for
 * string manipulation.
 * 
 * @author Marin Jurjevic
 *
 */
public class CString {

	/**
	 * The data is used for character storage.
	 */
	private final char data[];

	/**
	 * The offset is the first index of the storage that is used.
	 */
	private final int offset;

	/**
	 * The length is the number of characters in CString.
	 */
	private final int length;

	/**
	 * Constructs new CString that contains specified substring by passed
	 * arguments. New char data is allocated with offset equal to 0. This char
	 * array can not be changed after initialization securing immutability of
	 * these objects.
	 * 
	 * @param data
	 *            characters to be used in new CString
	 * @param offset
	 *            index at which new CString starts
	 * @param length
	 *            length of new CString
	 */
	public CString(char[] data, int offset, int length) {
		if (data == null) {
			throw new IllegalArgumentException("data can not be null");
		} else if (offset > data.length) {
			throw new IndexOutOfBoundsException("Offset must be within data bounds");
		} else if (offset + length > data.length) {
			throw new IndexOutOfBoundsException("Specified CString is not within data bounds");
		}

		this.data = Arrays.copyOfRange(data, offset, offset + length);
		this.offset = 0;
		this.length = length;
	}

	/**
	 * Constructs new CString that contains all characters from data.
	 * 
	 * @param data
	 *            characters to be used in CString
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}

	/**
	 * Creates new CString object from original one. If original CString
	 * internal character array is larger than needed, this constructor will
	 * allocate new aray containing only characters that belong to the original
	 * CString.
	 * 
	 * @param original
	 *            original CString object
	 */
	public CString(CString original) {
		if (original.length == original.data.length) {
			data = original.data;
			length = original.length;
		} else {
			data = new char[original.length];
			length = original.length;
			for (int i = 0, j = original.offset; i < length; i++, j++) {
				data[i] = original.data[j];
			}
		}
		offset = 0;
	}

	/**
	 * Private constructor which shares char array for speed.
	 * 
	 * @param offset
	 *            offset at
	 * @param length
	 * @param data
	 */
	private CString(int offset, int length, char[] data) {
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * @return number of characters in this CString
	 */
	public int length() {
		return length;
	}

	/**
	 * Returns character specified by index.
	 * 
	 * @param index
	 *            index in this CString
	 * @return character at specified index in this CString
	 */
	public char charAt(int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("Invalid index specified.");
		}

		return data[offset + index];
	}

	/**
	 * @return array of characters that are stored in this CString
	 */
	public char[] toCharArray() {
		char[] charArray = new char[length];

		for (int i = 0, j = offset; i < length; i++) {
			charArray[i] = data[j];
		}

		return charArray;
	}

	/**
	 * Converts this CString to java.lang.String.
	 * 
	 * @return String representation of CString.
	 */
	@Override
	public String toString() {
		return new String(data, offset, length);
	}

	/**
	 * Returns index of first occurence of char c in CString or -1 if char is
	 * not in this CString.
	 * 
	 * @param c
	 *            character we search for
	 * @return index index of given char in CString or -1 if it can not be found
	 */
	public int indexOf(char c) {
		for (int i = offset; i < offset + length; i++) {
			if (data[i] == c) {
				return i - offset;
			}
		}

		return -1;
	}

	/**
	 * Checks if this CString starts with the CString substring.
	 * 
	 * @param s
	 *            substring we check is this CString is starting with
	 * @return true if this CString starts with given substring, false otherwise
	 */
	public boolean startsWith(CString s) {

		if (length < s.length) {
			return false;
		}
		// i - iterator of this CString, j - iterator of CString s
		for (int i = offset, j = s.offset; j < s.offset + s.length; i++, j++) {
			if (data[i] != s.data[j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if this CString ends with the CString substring.
	 * 
	 * @param s
	 *            substring we check is this CString is ending with
	 * @return true if this CString ends with given substring, false otherwise
	 */
	public boolean endsWith(CString s) {
		if (length < s.length) {
			return false;
		}

		for (int i = offset + length - 1, j = s.offset + s.length - 1; j >= s.offset; i--, j--) {
			if (data[i] != s.data[j]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if this CString contains specified substring.
	 * 
	 * @param s
	 *            CString we search for in this string
	 * @return true if this CString contains s CString at any position, false
	 *         otherwise
	 */
	public boolean contains(CString s) { //we could use private method findLocations here, but this algorithm is faster
		int i,j,firstOcc;
		i = 0; // string iterator
		
		while(i<length){
			j = 0; //substring iterator
			
			while(i < length && data[offset+i] != s.data[s.offset] )
				i++; // searching for first letter match
			
			if(i == length){
				return false; // end of string, no match
			}
			
			firstOcc = i; // save first occurence
			
			while(i < length && j<s.length && data[offset+i] == s.data[s.offset+j]){
				i++;
				j++;
			}
			
			if(j == s.length){
				return true; // substring found
			}
			
			if(i == length){
				return false; // end of string, no match
			}
			
			i = firstOcc+1;
		}
			
		return false;
		
	}

	/**
	 * Returns new CString object containing substring of this CString.
	 * 
	 * @param startIndex
	 *            starting index of new CString
	 * @param endIndex
	 *            last exclusive index in this substring
	 * @return new CString containing specified substring of characters
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0 || endIndex > length || endIndex < startIndex) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		int newLength = endIndex - startIndex;
		return new CString(this.offset + startIndex, newLength, this.data);
	}

	/**
	 * Returns new CString object with n characters from the start
	 * 
	 * @param n
	 *            number of characters
	 * @return new CString which represents starting part of original CString
	 */
	public CString left(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Number of characters must be 0 or greater");
		}
		return this.substring(0, n);
	}

	/**
	 * Return new CString object with n characters from the end
	 * 
	 * @param n
	 *            number of characters
	 * @return new CString which represents ending part of original CString
	 */
	public CString right(int n) {
		return this.substring(length - n, length);
	}

	/**
	 * Creates new CString that is concatenation of current and given CString.
	 * 
	 * @param s
	 *            string to be concatenated on original one.
	 * @return new CString
	 */
	public CString add(CString s) {
		char[] value = new char[length + s.length];

		// copy original
		for (int i = 0, j = offset; i < length; i++, j++) {
			value[i] = data[j];
		}
		for (int i = this.length, j = s.offset; i < length + s.length; i++, j++) {
			value[i] = s.data[j];
		}

		return new CString(value);
	}

	/**
	 * Creates new CString in which each occurence of the old character is
	 * replaced with a new character.
	 * 
	 * @param oldChar
	 *            old character to be replaced
	 * @param newChar
	 *            new character to replace old one
	 */
	public CString replaceAll(char oldChar, char newChar) {
		char[] value = new char[length];

		for (int i = 0, j = offset; i < length; i++, j++) {
			if (data[j] == oldChar) {
				value[i] = newChar;
			} else {
				value[i] = data[j];
			}
		}

		return new CString(value);
	}

	/**
	 * Replaces old char sequence from this CString with a new specified char
	 * sequence.
	 * 
	 * @param oldStr
	 *            old substring to be replaced
	 * @param newStr
	 *            new string to replace with old one
	 * @return new CString with replaced all substrings
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if( this.contains(oldStr) == false){
			return this;
		}
		
		StringBuilder sb = new StringBuilder(length);
		int[] loc = findLocations(oldStr); // locations of substrings
		
		for(int i = 0, counter=0; i<length && counter<loc.length;i++){
			if(i == loc[counter]){
				sb.append(newStr.toString());
				counter++;
				i+=oldStr.length-1;
			}else{
				sb.append(data[offset+i]);
			}
		}
		
		
		return CString.fromString(sb.toString());
	}
	
	/**
	 * Finds locations of oldStr in this string and saves them in an array.
	 * @param oldStr old string to be searched in this string
	 * @return array of locations (indexes) of old string to be replaced with new one
	 */
	private int[] findLocations(CString oldStr){
		int[] loc = new int[Math.floorDiv(length, oldStr.length)]; // max elements in 
		int i,j;
		i = 0; // string iterator
		int occ; // last index of first char of found substring
		int counter = 0; // counter for how many substrings are found
		
		while(i<length){
			j = 0; //substring iterator
			
			while(i < length && data[offset+i] != oldStr.data[oldStr.offset] )
				i++; // searching for first letter match
			
			if(i == length){
				break; // end of string, no match
			}
			
			occ = i; // save first occurence
			
			while(i < length && j<oldStr.length && data[offset+i] == oldStr.data[oldStr.offset+j]){
				i++;
				j++;
			}
			
			if(j == oldStr.length){
				loc[counter++] = occ; // substring found, save location
			}
			
			if(i == length){
				break; // end of string, no match
			}
			
			i = occ+1;
		}
	return loc;
	}

	/**
	 * Returns new CString object which has the same character data as given
	 * Java's String object.
	 * 
	 * @param s
	 *            string to convert to CString
	 * @return new CString object
	 */
	public static CString fromString(String s) {
		return new CString(s.toCharArray());
	}
}
