package api.compute;

import project.annotations.ConceptualAPI;
import java.util.List;

@ConceptualAPI
public interface ComputeEngineAPI {

	//Computes factors
	List<Integer> factors(int n);
}
