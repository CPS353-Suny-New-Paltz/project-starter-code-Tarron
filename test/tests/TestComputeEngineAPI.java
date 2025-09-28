package tests;

import api.compute.ComputeEngineAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestComputeEngineAPI {

	@Test
	void smoke() {
		 //Creates a fake object that behaves like ComputeEngineAPI
		 ComputeEngineAPI engine = mock(ComputeEngineAPI.class);
		 
		 //If I ask for factors(12), returns the list [2, 2, 3]
		 when(engine.factors(12)).thenReturn(List.of(2, 2, 3));
		 
		 //We call our fake object
		 List<Integer> result = engine.factors(12);

		 //Checks if returned list matches expected result 
		 assertEquals(List.of(2, 2, 3), result);
		 
		 //Just checks to make sure the method was called with the input 12
		 verify(engine).factors(12);
	}
}
