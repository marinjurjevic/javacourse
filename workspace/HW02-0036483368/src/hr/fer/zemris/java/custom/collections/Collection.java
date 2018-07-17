package hr.fer.zemris.java.custom.collections;

/**
 * Class which represents some general collection of objects.
 */
public class Collection {
	
	/**
	 * Method to determine if collection is empty or not.
	 * @return true if collection contains no objects and 
	 * 	false otherwise 
	 */
	boolean isEmpty(){
		if(size() == 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns number of elements.
	 * @return number of currently stored objects in this collection
	 */
	int size(){
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * @param value value to be added
	 */
	void add(Object value){
		
	}
	
	/**
	 * Returns true only if the collection contains given value, as determined
	 * by the equals method. <code>null</code> is also a legal parameter.
	 * @param value value to be searched
	 * @return true if value is in collection, false otherwise
	 */
	boolean contains(Object value){
		return false;
	}
	
	/**
	 * Returns true only if the collection contains given value as determined
	 * by the equals method. It removes only one occurence of given value, it is
	 * not specified which.
	 * @param value value to be removed
	 * @return true if object is removed, false otherwise
	 */
	boolean remove(Object value){
		return false;
	}
	
	/**
	 * Allocates new array with size equals to the size of this collection.
	 * It fills array with collection content and returns it. This method
	 * never returns <code>null</code>.
	 * @return new allocated array filled with content from collection
	 * @throws UnsupportedOperationException if collection is empty
	 */
	Object[] toArray() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method calls processor.process method for each element of this collection.
	 * The order in which elements will be sent is undefined in this class.
	 * @param processor processor
	 */
	void forEach(Processor processor){
		
	}
	
	/**
	 * Method adds into this collection all elements from given collection.
	 * The other collection remains unchanged.
	 * @param other other collection's elements will be added to this collection 
	 */
	void addAll(Collection other){
		class LocalProcessor extends Processor{
			@Override
			public void process(Object value){
				add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	void clear(){
		
	}
}
