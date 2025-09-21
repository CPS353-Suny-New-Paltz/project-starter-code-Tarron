package api.compute;

import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {
	
	 @ConceptualAPIPrototype
	    public List<Integer> prototype(ComputeEngineAPI compute) {
		 
	        //Example input.
		 	int n = 2342342;

	        //Ask compute engine for factors.
	        List<Integer> results = (compute != null) ? compute.factors(n) : null;
	
	        //Return results.
	        return (results != null) ? results : new ArrayList<>();
	    }
}
