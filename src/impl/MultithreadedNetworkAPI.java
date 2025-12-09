package impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import api.compute.ComputeEngineAPI;
import api.process.StorageComputeAPI;
import api.user.UserComputeAPI;

public class MultithreadedNetworkAPI implements UserComputeAPI {

	private final UserComputeAPI delegate;
	//Upper bound on concurrent conceptual API call
	private static final int maxThreads = 4;
	//newFixedThreadPool to limit concurrency
	private final ExecutorService executor;

	public MultithreadedNetworkAPI() {
		StorageComputeAPI storage = new StorageComputeImpl();
		ComputeEngineAPI engine = new ComputeEngineImpl();
		this.delegate = new UserComputeImpl(storage, engine);
		this.executor = Executors.newFixedThreadPool(maxThreads);
	}


	public List<Integer> computeFactors(String inputSource,
			String outputSource,
			String delimiter) {

		Callable<List<Integer>> task = () -> {
			System.out.println("Network API WRITE to: " + outputSource);

			List<Integer> factors = delegate.computeFactors(inputSource, "output.txt", delimiter);

			String effectiveDelimiter =
					(delimiter == null || delimiter.isEmpty()) ? ";" : delimiter;

			String output = factors.stream()
					.map(Object::toString)
					.collect(java.util.stream.Collectors.joining(effectiveDelimiter));

			try {
				Files.writeString(Path.of(outputSource), output);
			} catch (IOException e) {
				throw new RuntimeException("Failed to write output file: " + outputSource, e);
			}

			return factors;
		};
		try {
			return executor.submit(task).get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Interrupted while computing factors", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Error during computeFactors", e.getCause());
		}
	}

	public void shutdown() {
	}
}
