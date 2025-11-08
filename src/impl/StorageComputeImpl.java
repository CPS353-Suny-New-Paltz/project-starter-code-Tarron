package impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Pattern;

import api.process.StorageComputeAPI;

public class StorageComputeImpl implements StorageComputeAPI {
	
	private static final Pattern SPLIT = Pattern.compile("[,\\s]+");


	@Override
	public List<Integer> readInput(String inputSource) {
		try {
            String content = Files.readString(Path.of(inputSource));
            return SPLIT.splitAsStream(content.trim())
                        .filter(s -> !s.isEmpty())       // defensive
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input: " + inputSource, e);
        }
	}

	@Override
	public int writeOutput(List<Integer> numbers, String delimiter) {
		try {
			//Convert each integer to a string, then join them together with the given delimiter.
			//Ex: numbers = [2, 3, 5, 7], delimiter = ","  ->  "2,3,5,7"
			String output = numbers.stream()
					.map(Object::toString)
					.collect(Collectors.joining(delimiter));

			//Write the output string to a file
			Files.writeString(Path.of("output.txt"), output);

			//Return the number of integers written, as confirmation.
			return numbers.size();

		} catch (IOException e) {
			//If something goes wrong while writing, throw a runtime exception
			throw new RuntimeException("Failed to write output file", e);
		}
	}
}