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
		if (inputSource == null || inputSource.isBlank()) {
			return List.of();
		}

		try {
			String content = Files.readString(Path.of(inputSource));
			return SPLIT.splitAsStream(content.trim())
					.filter(s -> !s.isEmpty())       
					.map(Integer::parseInt)
					.collect(Collectors.toList());
		} catch (IOException e) {
			return List.of();
		}
	}

	@Override
	public void writeOutput(List<Integer> numbers, String outputSource, String delimiter) {
		if (outputSource == null || outputSource.isBlank()) {
			throw new IllegalArgumentException("outputSource cannot be null/blank");
		}

		List<Integer> safeNumbers = (numbers == null) ? List.of() : numbers;
		String d = (delimiter == null || delimiter.isEmpty()) ? "," : delimiter;

		try {
			String output = safeNumbers.stream()
					.map(Object::toString)
					.collect(Collectors.joining(d));

			// Append so each input produces exactly one output line
			Files.writeString(
					Path.of(outputSource),
					output,
					java.nio.file.StandardOpenOption.CREATE,
					java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
					);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write output file: " + outputSource, e);
		}
	}
}