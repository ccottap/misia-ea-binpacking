package es.uma.lcc.caesium.problem.binpacking.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uma.lcc.caesium.problem.binpacking.BinPacking;

/**
 * Utility class for the Bin-Packing problem
 * @author ccottap
 * @version 1.0
 */
public class BinPackingUtil {
	
	/**
	 * Distribute the objects in the collection specified among the bins. Each object is allocated in
	 * an existing bin or, if no one has enough size left, in a new one.
	 * @param bp the problem instance
	 * @param objects the objects to be distributed
	 * @param bins the existing bins
	 */
	public static void distribute (BinPacking bp, Collection<Integer> objects, Map<Integer, Set<Integer>> bins) {
		int num = bp.getNumObjects();
		Map<Integer, Integer> weights = new HashMap<Integer, Integer>(num); // weight of each bin
		List<Integer> ids = new ArrayList<Integer>(num);
		for (int i=0; i<num; i++)
			ids.add(i);
		
		for (var e: bins.entrySet()) {
			ids.remove((Integer)e.getKey()); // remove used labels
			Set<Integer> bin = e.getValue();
			int w = 0;
			for (int o: bin)
				w += bp.getObjectWeight(o);
			weights.put(e.getKey(), w);
		}
		
		int binSize = bp.getBinSize();
		for (int o: objects) {
			boolean found = false;
			int ow = bp.getObjectWeight(o);
			for (var e: weights.entrySet()) {
				int w = e.getValue() + ow;
				if (w <= binSize) {
					weights.put(e.getKey(), w);
					bins.get(e.getKey()).add(o);
					found = true;
					break;
				}
			}
			if (!found) {
				Set<Integer> newbin =  new HashSet<Integer>();
				newbin.add(o);
				bins.put(ids.get(0), newbin);
				weights.put(ids.get(0), ow);
				ids.remove(0);
			}
		}
	}
}
