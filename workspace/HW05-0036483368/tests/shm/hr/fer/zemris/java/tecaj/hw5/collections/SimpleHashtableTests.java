package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public class SimpleHashtableTests {

	@Test (expected = IllegalArgumentException.class)
	public void testInvalidCapacity() {
		new SimpleHashTable<>(-2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testKeyNull() {
		SimpleHashTable<String, Integer> sh = new SimpleHashTable<>(10);
		
		sh.put(null, 5);
	}
	
	@Test
	public void testSize(){
		SimpleHashTable<String, Integer> sh = prepare();
		assertEquals("Expected 5", 5, sh.size());
	}
	
	@Test
	public void testRemove(){
		SimpleHashTable<String, Integer> sh = prepare();
		sh.remove("Bili");
		assertEquals("Expected 4", 4, sh.size());
		assertEquals("Expcted false", false, sh.containsKey("Bili"));
	}
	
	@Test
	public void testRemoveEmpty(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepareEmpty();
		sh.remove("Bili");
	}
	
	@Test
	public void testContainsKey(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		assertEquals("Expcted true", true, sh.containsKey("Bili"));
		assertEquals("Expcted false", false, sh.containsKey("Zeus"));
		assertEquals("Expcted true", true, sh.containsKey("Deba"));
		
	}
	
	@Test
	public void testContainsValue(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		assertEquals("Expcted false", false, sh.containsValue(1));
		assertEquals("Expcted true", true, sh.containsValue(2));
		assertEquals("Expcted true", true, sh.containsValue(3));
		assertEquals("Expcted true", true, sh.containsValue(4));
		assertEquals("Expcted true", true, sh.containsValue(5));
		
	}
	
	@Test
	public void testCleaer(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		sh.clear();

		assertEquals("Expcted 0", 0, sh.size());
	}
	
	@Test
	public void testGet(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();

		assertEquals("Expected", 2, (int)sh.get("Deba"));
		assertEquals("Expected", 3, (int)sh.get("Grle"));
		assertEquals("Expected", 5, (int)sh.get("Bili"));
	}
	
	@Test
	public void testIsEmpty(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		assertEquals("Expcted false", false, sh.isEmpty());
		sh.clear();
		assertEquals("Expcted true", true, sh.isEmpty());
	}
	
	@Test
	public void testIteratorRemove(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		Iterator<SimpleHashTable.TableEntry<String,Integer>> it = sh.iterator();
		while(it.hasNext()) {
			SimpleHashTable.TableEntry<String,Integer> pair = it.next();
				if(pair.getKey().equals("Žile")) {
					it.remove();
				}
		}
		
		assertEquals("Expcted false", false, sh.containsKey("Žile"));
	}
	
	@Test (expected = IllegalStateException.class)
	public void testIteratorDoubleRemove(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		Iterator<SimpleHashTable.TableEntry<String,Integer>> it = sh.iterator();
		while(it.hasNext()) {
			SimpleHashTable.TableEntry<String,Integer> pair = it.next();
				if(pair.getKey().equals("Žile")) {
					it.remove();
					it.remove();
				}
		}
		
		assertEquals("Expcted false", false, sh.containsKey("Žile"));
	}
	
	@Test (expected = ConcurrentModificationException.class)
	public void testUserRemove(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		Iterator<SimpleHashTable.TableEntry<String,Integer>> it = sh.iterator();
		while(it.hasNext()) {
			SimpleHashTable.TableEntry<String,Integer> pair = it.next();
				if(pair.getKey().equals("Žile")) {
					sh.remove("Žile");
				}
		}
		
		assertEquals("Expcted false", false, sh.containsKey("Žile"));
	}
	
	@Test
	public void testUserPut(){	// must not throw
		SimpleHashTable<String, Integer> sh = prepare();
		
		Iterator<SimpleHashTable.TableEntry<String,Integer>> it = sh.iterator();
		while(it.hasNext()) {
			SimpleHashTable.TableEntry<String,Integer> pair = it.next();
				if(pair.getKey().equals("Žile")) {
					sh.put("Žile", 1);
				}
		}
		
		assertEquals("Expcted false", 1, (int) sh.get("Žile"));
	}
	
	
	// help methods for tests
	public static SimpleHashTable<String, Integer> prepare(){
		SimpleHashTable<String, Integer> sh = new SimpleHashTable<>(10);
		sh.put("Deba", 2);
		sh.put("Grle", 3);
		sh.put("Bili", 5);
		sh.put("Žile", 3);
		sh.put("Manga", 4);
		
		return sh;
	}
	
	public static SimpleHashTable<String, Integer> prepareEmpty(){
		return new SimpleHashTable<>();
	}
}
