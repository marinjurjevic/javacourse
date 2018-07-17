package hr.fer.zemris.java.hw16.model;

import java.nio.file.Path;

/**
 * Simple implementation of Document vector used in query engine simulation.
 * @author Marin Jurjevic
 *
 */
public class DocVector implements Comparable<DocVector>{

	/**
	 * path to the document
	 */
	private Path documentPath;
	
	/**
	 * vector components
	 */
	private double[] factors;
	
	/**
	 * semantic similarity with last executed query
	 */
	private double querySim;
	
	/**
	 * Creates new vector, associates with with document and allocates new array of factors used.
	 * @param documentPath path to the document
	 * @param vecSize size of this vector, number of dimensions
	 */
	public DocVector(Path documentPath, int vecSize) {
		this.documentPath = documentPath;
		factors = new double[vecSize];
	}
	
	/**
	 * Sets factor value on given index.
	 * @param index vector index
	 * @param value new factor value
	 */
	public void setFactor(int index,double value){
		factors[index] = value;
	}
	
	/**
	 * Returns factor by index.
	 * @param index factor index
	 * @return factor value
	 */
	public double getFactor(int index){
		return factors[index];
	}
	
	/**
	 * Calculates vector magnitude.
	 * @return vector magnitude
	 */
	public double getMagnitude(){
		double sum = 0;
		for(Double d : factors){
			sum += d*d;
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * Calculates scalar product of two document vectors.
	 * @param v1 first vector
	 * @param v2 second vector 
	 * @return real number,value of scalar product
	 */
	public static double scalarProduct(DocVector v1, DocVector v2){
		double product = 0;
		for(int i = 0; i<v1.factors.length; i++){
			product += (v1.factors[i]*v2.factors[i]);
		}
		
		return product;
	}
	
	/**
	 * Returns calculated query similarity with this document vector.
	 * @return query similarity
	 */
	public double querySim(){
		return querySim;
	}
	
	/**
	 * Sets new query similarity 
	 * @param querySim new query similarity
	 */
	public void setQuerySim(double querySim){
		this.querySim = querySim;
	}
	
	/**
	 * Returns document path associated with this vector.
	 * @return path to the document
	 */
	public Path getDocumentPath(){
		return documentPath;
	}
	
	@Override
	public int compareTo(DocVector o) {
		return Double.compare(o.querySim, this.querySim);
	}
}
