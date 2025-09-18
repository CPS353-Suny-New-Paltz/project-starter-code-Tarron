package api.compute;

import project.annotations.ConceptualAPIPrototype;
import java.util.ArrayList;
import java.util.List;

public class ComputeEngineAPIPrototype {
	
	 @ConceptualAPIPrototype
	    public List<Integer> prototype(ComputeEngineAPI compute) {
		 
	        //Example input
	        List<Integer> nums = new ArrayList<>();
	        nums.add(57);
	        nums.add(23);

	        //Ask the compute engine to compute a result
	        int gcd = compute.computeGCD(nums);

	        //Return as a list 
	        List<Integer> results = new ArrayList<>();
	        results.add(gcd);
	        return results;
	    }
}
