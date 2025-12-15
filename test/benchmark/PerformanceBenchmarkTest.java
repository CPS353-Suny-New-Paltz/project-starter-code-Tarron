package benchmark;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import api.compute.ComputeEngineAPI;
import api.user.UserComputeAPI;
import impl.ComputeEngineImpl;
import impl.UserComputeImpl;

/**
 * I implemented and ran integration benchmark test (PerformanceBenchmarkTest). 
 * For the original implementation, the benchmark results showed a stable median 
 * runtime of about 165 ms, indicating that the workload is CPU-bound.
 * 
 * I then came to the conclusion that the factor computation (ComputeEngineImpl.factors) 
 * was the bottleneck because the coordinator's primary repetitive CPU work is calling 
 * ComputeEngineAPI.factors(n) for each input.
 */
public class PerformanceBenchmarkTest {

    @Test
    void benchmark_original_implementation_only() {
        List<Integer> workload = buildWorkload();

        InMemoryStorageForBenchmark storage = new InMemoryStorageForBenchmark(workload);
        ComputeEngineAPI engine = new ComputeEngineImpl();
        UserComputeAPI coordinator = new UserComputeImpl(storage, engine);

        //Warm-up
        for (int i = 0; i < 5; i++) {
            coordinator.computeFactors("ignored", "ignored", ",");
        }

        //Take multiple samples and use median
        long[] samples = new long[9];
        for (int i = 0; i < samples.length; i++) {
            long start = System.nanoTime();
            coordinator.computeFactors("ignored", "ignored", ",");
            long end = System.nanoTime();
            samples[i] = end - start;

            //Ensure output was produced
            assertTrue(storage.getOutputSize() > 0, "Benchmark produced no output; workload may be wrong.");
        }

        Arrays.sort(samples);
        long median = samples[samples.length / 2];

        System.out.println("Original median runtime (ns): " + median);
        System.out.println("Samples (ns): " + Arrays.toString(samples));

        assertTrue(median > 0, "Median runtime should be > 0");
    }

    /**
     * Workload chosen to stress factor-finding.
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
        
        //Repeat enough times to amplify signal
        for (int r = 0; r < 120; r++) {
            for (int s : seeds) {
                nums.add(s);
            }
        }
        return nums;
    }
}
