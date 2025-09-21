package api.process;

import project.annotations.ProcessAPIPrototype;
import java.util.ArrayList;
import java.util.List; 

public class StorageComputeAPIPrototype {

	@ProcessAPIPrototype
	public void prototype(StorageComputeAPI storage) {
		
		//Reads the input. If no input, add test value.
		List<Integer> input = (storage != null) ? storage.readInput() : null;
		
		int n = (input != null && !input.isEmpty()) ? input.get(0) : 48;
		
		//Assume we computed the factors (fake results).
		List<Integer> output = new ArrayList<>();
		output.add(1); output.add(2); output.add(3); output.add(4); output.add(6);
        output.add(8); output.add(12); output.add(16); output.add(24); output.add(48);
        
        //Send result back to storage WITH delimiter.
        storage.writeOutput(output, ";");
	}
}
