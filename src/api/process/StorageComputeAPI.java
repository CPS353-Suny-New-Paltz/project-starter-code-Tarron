package api.process;

import project.annotations.ProcessAPI;
import java.util.List;

@ProcessAPI
public interface StorageComputeAPI {

	//Reads input numbers from data storage system
	List<Integer> readInput(String inputSource);

	//Writes output number to data storage system, with delimiter
	int writeOutput(List<Integer> numbers, String delimiter);
}
