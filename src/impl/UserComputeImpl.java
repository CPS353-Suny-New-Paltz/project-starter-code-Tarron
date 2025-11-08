package impl;

import java.util.List;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;

public class UserComputeImpl implements UserComputeAPI {
	StorageComputeAPI storage;
	ComputeEngineAPI engine;
	
	public UserComputeImpl(StorageComputeAPI storage, ComputeEngineAPI engine) {
	    this.storage = storage;
	    this.engine = engine;
	}
	
	@Override
	public List<Integer> computeFactors(String inputSource, String outputSource, String delimiter) {
		//Read all input integers from the storage
	    List<Integer> inputs = storage.readInput(inputSource);

	    //Return the last set of factors we computed
	    List<Integer> lastFactors = List.of();

	    //For each input n, compute its factors with the real engine,
	    //then ask the storage to write that list using the configured delimiter.
	    for (int n : inputs) {
	        List<Integer> factors = engine.factors(n);
	        lastFactors = factors;               
	        storage.writeOutput(factors, delimiter);
	    }

	    //Return the final factors list
	    return lastFactors;

	}

}
