package es.uma.lcc.caesium.problem.binpacking.ea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import es.uma.lcc.caesium.ea.base.Individual;
import es.uma.lcc.caesium.ea.operator.variation.mutation.MutationOperator;
import es.uma.lcc.caesium.problem.binpacking.BinPacking;

/**
 * A repair method for the Bin-packing Problem
 * @author ccottap
 * @version 1.0
 *
 */
public class BinPackingRepair extends MutationOperator {
	/**
	 * a local RNG for shuffling
	 */
	private Random myrng;
	/**
	 * Creates the operator. 
	 * @param pars String representation of the mutation probability
	 */
	public BinPackingRepair(List<String> pars) {
		super(pars);
		myrng = new Random(1);
	}

	@Override
	protected Individual _apply(List<Individual> parents) {
		BinPackingObjectiveFunction bpof = (BinPackingObjectiveFunction)obj;
		BinPacking bp = bpof.getBinPackingData();
		Map<Integer,Set<Integer>> bins = bpof.genotype2Bins(parents.get(0).getGenome());
		
		List<Integer> removed = new ArrayList<Integer>();
		int binSize = bp.getBinSize();
		for (var e: bins.entrySet()) {
			var bin = e.getValue();
			var elements = new ArrayList<Integer>(bin);
			Collections.shuffle(elements, myrng);
			int w = 0;
			for (int o: elements) {
				w += bp.getObjectWeight(o);
			}
			for (int o: elements) {
				if (w > binSize) {
					w -= bp.getObjectWeight(o);
					bin.remove(o);
					removed.add(o);
				}
				else
					break;
			}
		}
		Collections.shuffle(removed, myrng);
		
		BinPackingUtil.distribute(bp, removed, bins);
		
		Individual ind = new Individual();
		ind.setGenome(bpof.bins2Genotype(bins));
		ind.touch();
		return ind;
	}

	@Override
	public String toString() {
		return "MyMutationOperator(" + prob + ")";
	}

}
