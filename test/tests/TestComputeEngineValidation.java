package tests;

import api.compute.ComputeEngineAPI;
import impl.ComputeEngineImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestComputeEngineValidation {
	@Test
	void factors_throws_on_invalid_input() {
		ComputeEngineAPI engine = new ComputeEngineImpl();

		//n <= 0 should trigger validation
		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> engine.factors(0));

		assertEquals("n must be positive", ex.getMessage());
	}
}
