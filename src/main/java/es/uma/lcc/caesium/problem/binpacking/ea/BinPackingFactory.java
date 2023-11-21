package es.uma.lcc.caesium.problem.binpacking.ea;

import java.util.List;

import es.uma.lcc.caesium.ea.operator.variation.VariationFactory;
import es.uma.lcc.caesium.ea.operator.variation.VariationOperator;

/**
 * A user-defined factory for variation operators for Bin-Packing
 * @author ccottap
 * @version 1.0
 */
public class BinPackingFactory extends VariationFactory {

	@Override
	public VariationOperator create (String name, List<String> pars) {
		VariationOperator op = null;
				
		switch (name.toUpperCase()) {
		case "BINPACKINGREPAIR":
			op = new BinPackingRepair(pars);
			break;
		
		default:
			op = super.create(name, pars);
		}
		
		return op;
	}

}
