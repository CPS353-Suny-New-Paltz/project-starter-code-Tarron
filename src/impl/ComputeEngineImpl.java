package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.compute.ComputeEngineAPI;

/*
 * Empty implementation, for now.
 * Later, it will call the factorization algorithm.
 */
public class ComputeEngineImpl implements ComputeEngineAPI {

	@Override
	 public List<Integer> factors(int n) {
	    if (n <= 0) {
	    	throw new IllegalArgumentException("n must be positive");
	    }
	    if (n == 1) {
	    	return List.of(1);
	    }

	    List<Integer> small = new ArrayList<>();
	    List<Integer> large = new ArrayList<>();

	    //Scan up to sqrt(n)
	    for (int d = 1; (long) d * d <= n; d++) {
	      if (n % d == 0) {
	        small.add(d);
	        int q = n / d;
	        if (q != d) {
	        	large.add(q); //Avoids duplicate when d*d == n
	        }
	      }
	    }

	    //Builds ascending list: small (already ascending) + reversed(large)
	    Collections.reverse(large);
	    small.addAll(large);
	    return small;
	  }
}
