package tests;

import api.process.StorageComputeAPI;
import impl.StorageComputeImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the real StorageComputeImpl.
 * 
 * This ensures that reading and writing from the storage
 * behaves as expected with test input/output files.
 */
public class TestStorageComputeAPI {

	@Test
	void smoke() throws Exception {
		
		//Creates REAL storage object
		StorageComputeAPI storage = new StorageComputeImpl();
		
		//Reads, then verifies that inputs are not null
		List<Integer> inputs = storage.readInput("input.txt");
		assertNotNull(inputs);
		
		//Use a test-specific output file
        String outFile = "testOutput.txt";

        //Ensure clean slate so repeated test runs don’t append forever
        Files.deleteIfExists(Path.of(outFile));

        //Writes back to output file and confirms behavior
        storage.writeOutput(inputs, outFile, ",");
        assertEquals(inputs, storage.readInput(outFile));
	}	
}