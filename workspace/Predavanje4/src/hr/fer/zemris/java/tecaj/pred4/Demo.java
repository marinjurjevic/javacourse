package hr.fer.zemris.java.tecaj.pred4;

import java.util.Iterator;

public class Demo {

	public static void main(String[] args) {
		SlijedParnihBrojeva spb = new SlijedParnihBrojeva(2, 5);
		
		for(Integer i : spb){
			for(Integer j : spb){
				System.out.println(i + "-" + j);
			}
		}
		
		Iterator<Integer> it4 = spb.iterator();
		it4.next();
//		it4.forEachRemaining(new Consumer<Integer>(){
//			@Override
//			public void accept(Integer t){
//				System.out.println(t);
//			}
//		});
		
//		it4.forEachRemaining(t -> System.out.println(t));
		
		it4.forEachRemaining(System.out::println);
	}

}
