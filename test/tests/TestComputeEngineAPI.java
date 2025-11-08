package tests;

import api.compute.ComputeEngineAPI;
import impl.ComputeEngineImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Tests the real ComputeEngineImpl.
 * 
 * This verifies that the factorization logic works correctly.
 */
public class TestComputeEngineAPI {

	@Test
	void smoke() {	

		ComputeEngineAPI engine = new ComputeEngineImpl();

		//Call the REAL method
		List<Integer> result = engine.factors(12);

		assertEquals(List.of(1, 2, 3, 4, 6, 12), result);
	}
}