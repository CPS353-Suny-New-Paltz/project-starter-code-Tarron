package tests.integration;

import java.util.ArrayList;
import java.util.List;

//Simple in-memory input/output storage for testing
public final class InMemoryIOConfig {
	private final List<Integer> input;
	private final List<String> output;
	
	//Store input list
	public InMemoryIOConfig(List<Integer> input){
		this.input = new ArrayList<>(input == null ? List.of() : input);
		this.output = new ArrayList<>();
	}
	
	//Get test input data
	public List<Integer> input(){
		return input;
	}
	
	//Get collected test output data
	public List<String> output(){
		return output;
	}
}
