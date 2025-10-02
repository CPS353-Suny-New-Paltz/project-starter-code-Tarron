package tests.integration;

import api.process.StorageComputeAPI;
import java.util.List;
import java.util.stream.Collectors;

//Fake StorageComputeAPI that uses in-memory config for tests
public final class InMemoryStorageComputeAPI implements StorageComputeAPI {
	private final InMemoryIOConfig cfg;

	//Hooks up the config
	public InMemoryStorageComputeAPI(InMemoryIOConfig cfg) {
		this.cfg;
	}

	//Reads input numbers from config
	@Override
	public List<Integer> readInput() {
		return cfg.input();
	}

	//Writes numbers as a string into config output
	@Override
	public void writeOutput(List<Integer> numbers, String delimiter) {
		String d = (delimiter == null) ? "," : delimiter;
		List<Integer> vals = (numbers == null) ? List.of() : numbers;
		String line = vals.stream().map(String::valueOf).collect(Collectors.joining(d));
		cfg.output().add(line);
	}
}
