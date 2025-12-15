package benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.process.StorageComputeAPI;

public class InMemoryStorageForBenchmark implements StorageComputeAPI {

    private final List<Integer> inputs;
    private List<Integer> lastWritten = List.of();

    public InMemoryStorageForBenchmark(List<Integer> inputs) {
        this.inputs = (inputs == null) ? List.of() : new ArrayList<>(inputs);
    }

    @Override
    public List<Integer> readInput(String inputSource) {
        return Collections.unmodifiableList(inputs);
    }

    @Override
    public void writeOutput(List<Integer> numbers, String outputSource, String delimiter) {
        //Store results so the work
        this.lastWritten = (numbers == null) ? List.of() : new ArrayList<>(numbers);
    }

    public int getOutputSize() {
        return lastWritten.size();
    }
}
