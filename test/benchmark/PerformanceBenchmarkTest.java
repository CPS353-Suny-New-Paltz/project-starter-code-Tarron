package benchmark;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import api.compute.ComputeEngineAPI;
import api.user.UserComputeAPI;
import impl.ComputeEngineImpl;
import impl.ComputeEngineImplFast;
import impl.UserComputeImpl;
import tests.integration.InMemoryIOConfig;
import tests.integration.InMemoryStorageComputeAPI;

/**
 * I implemented and ran integration benchmark test (PerformanceBenchmarkTest). 
 * For the original implementation, the benchmark results showed a stable median 
 * runtime of about 172.226 ms, indicating that the workload is CPU bound.
 * 
 * I then came to the conclusion that the factor computation (ComputeEngineImpl.factors) 
 * was the bottleneck because the coordinator's primary repetitive CPU work is calling 
 * ComputeEngineAPI.factors(n) for each input.
 * 
 * Benchmark results from one run (before and after):
 * Original: 164.7 ms
 * Optimized: 81.4 ms
 * Improvement: 50.6%
 */
public class PerformanceBenchmarkTest {

	@Test
	void fast_version_is_at_least_10_percent_faster() {
		List<Integer> workload = buildWorkload();

		long originalMedianNs = measureMedianNs(workload, new ComputeEngineImpl());
		long fastMedianNs = measureMedianNs(workload, new ComputeEngineImplFast());

		System.out.println("Original median (ns): " + originalMedianNs);
		System.out.println("Fast median (ns):     " + fastMedianNs);

		long threshold = (long) (originalMedianNs * 0.90);
		assertTrue(fastMedianNs <= threshold, "Expected fast version to be at least 10% faster. "
				+ "Original=" + originalMedianNs + "ns, "
				+ "Fast=" + fastMedianNs + "ns, "
				+ "Threshold(90%)=" + threshold + "ns"
				);
	}

	private static long measureMedianNs(List<Integer> workload, ComputeEngineAPI engine) {
		//Use the EXISTING test infrastructure (deleted the new one)
		InMemoryIOConfig cfg = new InMemoryIOConfig(workload);
		InMemoryStorageComputeAPI storage = new InMemoryStorageComputeAPI(cfg);

		UserComputeAPI coordinator = new UserComputeImpl(storage, engine);

		//Warmup
		for (int i = 0; i < 5; i++) {
			coordinator.computeFactors("ignored", "ignored", ",");
		}

		long[] samples = new long[9];
		for (int i = 0; i < samples.length; i++) {
			long start = System.nanoTime();
			coordinator.computeFactors("ignored", "ignored", ",");
			long end = System.nanoTime();
			samples[i] = end - start;

			//Ensure something was written
			assertTrue(!cfg.output().isEmpty(), "Benchmark produced no output.");
			cfg.output().clear();
		}

		Arrays.sort(samples);
		return samples[samples.length / 2];
	}

	/**
	 * Workload chosen to stress factor finding.
	 * Uses many large composite numbers so ComputeEngineImpl.factors() does a lot of work.
	 */
	private static List<Integer> buildWorkload() {
		List<Integer> nums = new ArrayList<>();

		int[] seeds = {
				1_073_741_824,
				1_999_998_000,
				1_800_000_000,
				1_500_000_000,
				1_234_567_890,
				987_654_320,
				864_864_000
		};

		for (int r = 0; r < 120; r++) {
			for (int s : seeds) {
				nums.add(s);
			}
		}
		return nums;
	}
}
