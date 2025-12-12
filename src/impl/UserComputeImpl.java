package impl;

import java.util.ArrayList;
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
	            //Read all input integers from storage
	            List<Integer> inputs = storage.readInput(inputSource);

	            //We must produce exactly ONE output per input
	            List<Integer> outputs = new ArrayList<>();

	            for (int n : inputs) {
	                List<Integer> factors = engine.factors(n);

	                //Choose exactly one output value per input
	                //Using the largest factor is a stable choice
	                int oneOutput;
	                if (factors == null || factors.isEmpty()) {
	                    oneOutput = 0;
	                } else {
	                    oneOutput = factors.get(factors.size() - 1);
	                }

	                outputs.add(oneOutput);
	            }

	            //Write one comma separated line containing exactly outputs.size() entries
	            storage.writeOutput(outputs, outputSource, effectiveDelimiter);

	            //Return the outputs list
	            return outputs;

	        } catch (Exception e) {
	            return List.of();
	        }
	    }
}
