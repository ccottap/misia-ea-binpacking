package es.uma.lcc.caesium.problem.binpacking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


/**
 * Bin-Packing problem
 * @author ccottap
 * @version 1.0
 */
public class BinPacking {
	/**
	 * weight of each object
	 */
	protected int[] objects;
	/**
	 * size of the bins
	 */
	protected int binSize;
	
	/**
	 * class-wide RNG
	 */
	protected static Random rng = new Random(1);
	
	/**
	 * Sets the seed for the RNG
	 * @param seed the seed for the RNG
	 */
	public static void setSeed (long seed) {
		rng.setSeed(seed);
	}
	
	/**
	 * Creates a random instance given the bin size and the number of objects
	 * @param binSize bin size
	 * @param numObjects the number of objects
	 */
	public BinPacking(int binSize, int numObjects){
		this.binSize = binSize;
		objects = new int[numObjects];
		for (int i=0; i<numObjects; i++) {
			objects[i] = rng.nextInt(binSize);
		}
	}
	
	
	/**
	 * Creates an instance from a file
	 * @param filename file with the instance data
	 * @throws FileNotFoundException if the file cannot be opened
	 */
	public BinPacking(String filename) throws FileNotFoundException {
		readFromfile(filename);
	}

	/**
	 * Reads an instance from a file
	 * @param filename the name of the file
	 * @throws FileNotFoundException if the file cannot be opened
	 */
	public void readFromfile(String filename) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(filename));
		binSize = reader.nextInt();
		int num = reader.nextInt();
		objects = new int[num];
		
		for (int i=0; i<num; i++) {
			objects[i] = reader.nextInt();
		}
		reader.close();		
	}
	
	/**
	 * Saves the data to a file
	 * @param filename name of the file
	 * @throws FileNotFoundException if the file cannot be created
	 */
	public void saveToFile (String filename) throws FileNotFoundException {
		PrintWriter file = new PrintWriter(filename);
		file.println(binSize);
		int num = objects.length;
		file.println(num);
		for (int i=0; i<num; i++) {
			file.println(objects[i]);
		}
		file.close();
	}
	
	/**
	 * Returns the bin size
	 * @return the bin size
	 */
	public int getBinSize() {
		return binSize;
	}
	
	/**
	 * Returns the number of objects
	 * @return the number of objects
	 */
	public int getNumObjects () {
		return objects.length;
	}
	
	/**
	 * Returns the weight of a certain object
	 * @param i the index of the object
	 * @return the weight of the object indicated
	 */
	public int getObjectWeight(int i) {
		return objects[i];
	}

	@Override
	public String toString() {
		int num = objects.length;
		String str = "bin size: " + binSize + "\n#objects: " + num;
		for (int i=0; i<num; i++) {
			str += (i%10 == 0) ? "\n" : "\t";
			str += objects[i];
		}
		str += "\n";
		return str;
	}
}
