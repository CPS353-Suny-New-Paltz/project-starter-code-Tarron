package impl;

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
	private static final int MAX_THREADS = 4;
	//newFixedThreadPool to limit concurrency
	private final ExecutorService executor;

	public MultithreadedNetworkAPI() {
		StorageComputeAPI storage = new StorageComputeImpl();
		ComputeEngineAPI engine = new ComputeEngineImpl();
		this.delegate = new UserComputeImpl(storage, engine);
		this.executor = Executors.newFixedThreadPool(MAX_THREADS);
	}

	public List<Integer> computeFactors(String inputSource, String outputSource, String delimiter) {
	    Callable<List<Integer>> task =
	            () -> delegate.computeFactors(inputSource, outputSource, delimiter);

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
		executor.shutdown();
	}
}
