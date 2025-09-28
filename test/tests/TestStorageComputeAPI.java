package tests;

import api.process.StorageComputeAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestStorageComputeAPI {

	@Test
	void smoke () {
		//Creating our fake object
		StorageComputeAPI storage = mock(StorageComputeAPI.class);
		
		//Creats input data
		List<Integer> inputs = List.of(12, 18, 48);
		
		//Returns inputs list on call
		when(storage.readInput()).thenReturn(inputs);
		
		//Calls mock method, returns [12, 18, 48]
		List<Integer> read = storage.readInput();
		
		//Wont actually write anything, but Mockito will track call 
		storage.writeOutput(read, ",");
		
		//Checks if returned list matches expected result
		assertEquals(inputs, read);
		
		//Confirms both methods were called
		verify(storage).readInput();
		verify(storage).writeOutput(inputs, ",");
	}	
}
