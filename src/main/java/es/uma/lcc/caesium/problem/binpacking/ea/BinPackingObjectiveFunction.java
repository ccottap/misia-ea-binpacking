package es.uma.lcc.caesium.problem.binpacking.ea;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Genotype;
import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.fitness.DiscreteObjectiveFunction;
import es.uma.lcc.caesium.ea.fitness.OptimizationSense;
import es.uma.lcc.caesium.problem.binpacking.BinPacking;


/**
 * Objective function for the Bin-Packing Problem. Solutions are represented as 
 * a list of the same length as number of objects, each position indicating the 
 * label of the bin that object goes to.
 * @author ccottap
 * @version 1.0
 */
public class BinPackingObjectiveFunction extends DiscreteObjectiveFunction {
	/**
	 * the problem instance
	 */
	private BinPacking bp;
	
	/**
	 * Basic constructor of the objective function
	 * @param bp the problem instance
	 */
	public BinPackingObjectiveFunction(BinPacking bp) {
		super(bp.getNumObjects(), bp.getNumObjects());
		this.bp = bp;
	}

	@Override
	public OptimizationSense getOptimizationSense() {
		return OptimizationSense.MINIMIZATION;
	}
	
	/**
	 * Returns the Bin-Packing instance being solved
	 * @return the Bin-Packing instance being solved
	 */
	public BinPacking getBinPackingData () {
		return bp;
	}
	
	/**
	 * Creates a collection of bins from the information in an individual's genotype
	 * @param g the genotype
	 * @return a map representing the objects in each bin
	 */
	public Map<Integer, Set<Integer>> genotype2Bins(Genotype g) {
		int l = bp.getNumObjects();
		Map<Integer, Set<Integer>> bins = new HashMap<Integer,Set<Integer>>(l);
		for (int k=0; k<l; k++) {
			int label = (int)g.getGene(k);
			Set<Integer> bin = bins.get(label);
			if (bin == null) {
				bin = new HashSet<Integer>();
				bins.put(label, bin);
			}
			bin.add(k);
		}
		return bins;
	}
	
	/**
	 * Creates a genotype given a collection of bins
	 * @param bins a map representing the objects in each bin
	 * @return a genotype
	 */
	public Genotype bins2Genotype(Map<Integer, Set<Integer>> bins) {
		Genotype g = new Genotype(bp.getNumObjects());
		for (var e: bins.entrySet()) {
			Set<Integer> bin = e.getValue();
			int label = e.getKey();
			for (int o: bin) 
				g.setGene(o, label);
		}
		return g;
	}

	@Override
	protected double _evaluate(Individual i) {
		int l = bp.getNumObjects();
		Map<Integer, Set<Integer>> bins = genotype2Bins(i.getGenome());
		int penalty = 0;
		int binSize = bp.getBinSize();
		int offset = l*binSize;
		for (int label: bins.keySet()) {
			Set<Integer> bin = bins.get(label);
			int w = 0;
			for (int o: bin) 
				w += bp.getObjectWeight(o);
			if (w > binSize) {
				penalty += offset + (w - binSize); 
			}
		}
		return bins.size() + penalty;
	}



}
