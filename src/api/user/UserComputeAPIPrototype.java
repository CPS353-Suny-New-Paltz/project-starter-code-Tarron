package api.user;

import java.util.List;
import java.util.ArrayList;
import project.annotations.NetworkAPIPrototype;

public final class UserComputeAPIPrototype {
	
	@NetworkAPIPrototype
	public List<Integer> prototype(UserComputeAPI user) {
		
		String inputSource = null;
		String outputSource = null;
		String delimiter = ";";
		
		//Takes the inputs, computes the GCD, returns results
		List<Integer> results = user.computeGCD(inputSource, outputSource, delimiter);
		
		//Return if there's a result. No result returns a empty list
		return results != null ? results : new ArrayList<>();
	}
	
}
