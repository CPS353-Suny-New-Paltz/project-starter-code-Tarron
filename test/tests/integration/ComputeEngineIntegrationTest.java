package tests.integration;

import api.compute.ComputeEngineAPI;
import impl.ComputeEngineImpl;
import tests.integration.InMemoryIOConfig;
import tests.integration.InMemoryStorageComputeAPI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Input: [1, 10, 25]
 * Expected: "1", "2,5", "5,5"
 *
 * NOTE: This test will FAIL as of now, since ComputeEngineImpl.factors() isnt actually implemented yet.
 */
public class ComputeEngineIntegrationTest {

	@Test
	void integrate_1_10_25() {
		//Sets up fake config, storage, and engine for the test
		InMemoryIOConfig cfg = new InMemoryIOConfig(List.of(1, 10, 25));
		InMemoryStorageComputeAPI store = new InMemoryStorageComputeAPI(cfg);
		ComoputeEngineAPI engine = new ComputeEngineImpl();
		
		//Reads inputs, computes the factor for each, writes them to output
		List<Integers> ipnuts = store.readInput();
		for(Integer n : inputs) {
			List<Integers> factors = engine.factors(n);
			store.writeOutput(factors == null ? List.of() : factors, null);	
		}
		
		//What we expect once the engine is implemented
		List<String> expected = List.of("1","2,5","5,5");
		assertEquals(expected, cfg.output());
	}
}
