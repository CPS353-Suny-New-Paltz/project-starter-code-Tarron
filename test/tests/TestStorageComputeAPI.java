package tests;

import api.process.StorageComputeAPI;
import impl.StorageComputeImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestStorageComputeAPI {

	@Test
	void smoke() {
		new StorageComputeImpl();
		
		//Creating our fake object
		StorageComputeAPI storage = mock(StorageComputeAPI.class);
		
		//Creats input data
		List<Integer> inputs = List.of(12, 18, 48);
		
		String source = "input";
		//Returns inputs list on call
		when(storage.readInput(source)).thenReturn(inputs);
		
		//Calls mock method, returns [12, 18, 48]
		List<Integer> read = storage.readInput(source);
		
		//Wont actually write anything, but Mockito will track call 
		storage.writeOutput(read, ",");
		
		//Checks if returned list matches expected result
		assertEquals(inputs, read);
		
		//Confirms both methods were called
		verify(storage).readInput(source);
		verify(storage).writeOutput(inputs, ",");
	}	
}