package tests;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;
import impl.UserComputeImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * TestUserComputeAPI verifies that the UserComputeImpl class
 * correctly integrates StorageComputeAPI and ComputeEngineAPI.
 * 
 * It uses Mockito mocks so we can test UserComputeImpl
 * without running the real storage or compute systems.
 */
public class TestUserComputeAPI {
	
	@Test
	void smoke() {
		//Mock dependencies
		StorageComputeAPI storage = Mockito.mock(StorageComputeAPI.class);
	    ComputeEngineAPI engine = Mockito.mock(ComputeEngineAPI.class);
		
		//Creates a REAL instance of the class, but injects mocked dependencies
		UserComputeAPI user = new UserComputeImpl(storage, engine);
		
		//When readInput("input.txt") is called, fake it so it returns a list with [12]
		when(storage.readInput("input.txt")).thenReturn(List.of(12));
		
		//When engine.factors(12) is called, fake it so it returns [1, 2, 3, 4, 6, 12]
	    when(engine.factors(12)).thenReturn(List.of(1, 2, 3, 4, 6, 12));
		
	    //Calls the method we are testing
	    List<Integer> result = user.computeFactors("input.txt", "output.txt", ",");
	    
	    //Makes sure result isn't null, and that it equals the expected factors list
	    assertNotNull(result);
		assertEquals(List.of(12), result);		
	}
}
 
 