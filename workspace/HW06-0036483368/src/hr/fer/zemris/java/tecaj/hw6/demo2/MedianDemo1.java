package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.Optional;

/**
 * Demonstration of LikeMedian class on Integer objects.
 * 
 * @author Marin Jurjevic
 *
 */
public class MedianDemo1 {

	public static void main(String[] args) {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();

		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));

		Optional<Integer> result = likeMedian.get();
		System.out.println(result.get());

		for (Integer elem : likeMedian) {
			System.out.println(elem);
		}
	}

}
