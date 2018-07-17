package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Demonstration of LikeMedian class on String objects.
 * 
 * @author Marin Jurjevic
 *
 */
public class MedianDemo2 {

	public static void main(String[] args) {
		LikeMedian<String> likeMedian = new LikeMedian<String>();

		likeMedian.add("Joe");
		likeMedian.add("Jane");
		likeMedian.add("Adam");
		likeMedian.add("Zed");

		Optional<String> result = likeMedian.get();
		System.out.println(result.get()); // Writes: Jane
	}

}
