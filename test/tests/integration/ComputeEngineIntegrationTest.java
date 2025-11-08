package tests.integration;

import api.compute.ComputeEngineAPI;
import impl.ComputeEngineImpl;
import api.user.UserComputeAPI;
import impl.UserComputeImpl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Input: [1, 10, 25]
 * Expected: "1", "1,2,5,10", "1,5,25"
 *
 * NOTE: This test will FAIL as of now, since ComputeEngineImpl.factors() isnt actually implemented yet.
 */
public class ComputeEngineIntegrationTest {

	@Test
	void integrate_1_10_25() {
		
		//Sets up fake config, storage, and engine for the test
		InMemoryIOConfig cfg = new InMemoryIOConfig(List.of(1, 10, 25));
		InMemoryStorageComputeAPI store = new InMemoryStorageComputeAPI(cfg);
		ComputeEngineAPI engine = new ComputeEngineImpl();
		UserComputeAPI user = new UserComputeImpl(store, engine);

		var factors = user.computeFactors("input", "output", ",");
		
		//What we expect once the engine is implemented
		List<String> expected = List.of("1","1,2,5,10","1,5,25");
		assertEquals(expected, cfg.output());
	}
}