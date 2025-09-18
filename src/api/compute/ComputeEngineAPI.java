package api.compute;

import project.annotations.ConceptualAPI;
import java.util.List;

@ConceptualAPI
public interface ComputeEngineAPI {
	
	//Computes GCD
	int computeGCD(List<Integer> numbers);
}
