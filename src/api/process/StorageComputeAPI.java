package api.process;

import project.annotations.ProcessAPI;
import java.util.List;

@ProcessAPI
public interface StorageComputeAPI {

	List<Integer> readInput();
	
	void writeOutput(List<Integer> numbers, String delimiter);
}
