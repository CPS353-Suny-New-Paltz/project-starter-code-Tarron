package tests;

import api.compute.ComputeEngineAPI;
import impl.ComputeEngineImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestComputeEngineImpl {
	
	 @Test
    void factors_of_1_is_1() {
        ComputeEngineAPI engine = new ComputeEngineImpl();

        List<Integer> result = engine.factors(1);

        assertNotNull(result);
        assertEquals(List.of(1), result);
    }

    @Test
    void factors_of_100() {
        ComputeEngineAPI engine = new ComputeEngineImpl();

        List<Integer> result = engine.factors(100);

        // 100 = 1, 2, 4, 5, 10, 20, 25, 50, 100
        assertEquals(List.of(1, 2, 4, 5, 10, 20, 25, 50, 100), result);
    }
}
