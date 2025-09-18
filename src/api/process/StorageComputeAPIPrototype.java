package api.process;

import project.annotations.ProcessAPIPrototype;
import java.util.ArrayList;
import java.util.List; 

public class StorageComputeAPIPrototype {

	@ProcessAPIPrototype
	public void prototype(StorageComputeAPI storage) {
		
		//Reads the input. If no input, add nums for testing
		List<Integer> input = storage.readInput();
		if (input == null) {
			input = new ArrayList<>();
			input.add(48);
			input.add(18);
		}
		
		//Assume we computed the GCD (48,18 -> 6)
		List<Integer> output = new ArrayList<>();
        output.add(6);
        
        //Send result back to storage
        storage.writeOutput(output, ";");
	}
}
