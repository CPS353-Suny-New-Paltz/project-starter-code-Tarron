package api.user;

import project.annotations.NetworkAPI;
import java.util.List;

@NetworkAPI
public interface UserComputeAPI {
	
	/**
	 * Ask the ComputeEngine to calculate the factor from an input source
	 * and writes the result to an output source
	 */
	List<Integer> computeFactors(String inputSource, 
							 String outputSource,
							 String delimiter);
}
