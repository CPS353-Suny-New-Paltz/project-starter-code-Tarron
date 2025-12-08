package impl;

import java.util.List;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;

public class UserComputeImpl implements UserComputeAPI {
	StorageComputeAPI storage;
	ComputeEngineAPI engine;

	public UserComputeImpl(StorageComputeAPI storage, ComputeEngineAPI engine) {
		if (storage == null) {
			throw new IllegalArgumentException("storage must not be null");
		}
		if (engine == null) {
			throw new IllegalArgumentException("engine must not be null");
		}
		this.storage = storage;
		this.engine = engine;
	}



	@Override
	public List<Integer> computeFactors(String inputSource, String outputSource, String delimiter) {
		if (inputSource == null || inputSource.isBlank()) {
			return List.of();
		}
		if (outputSource == null || outputSource.isBlank()) {
			return List.of();
		}
		String effectiveDelimiter =
				(delimiter == null || delimiter.isEmpty()) ? "," : delimiter;

		try {
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
		} catch (Exception e) {
			return List.of();
		}
	}

}
