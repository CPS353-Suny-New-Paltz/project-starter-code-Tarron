package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.compute.ComputeEngineAPI;

/**
 * Bottleneck identification:
 * I measured performance using the benchmark.PerformanceBenchmarkTest I created.
 * Timing used System.nanoTime() after warm up and the median of multiple samples.
 * The original implementation consistently measured about 165-172 ms.
 * This told me that the CPU bound work was dominated by repeated divisor checks 
 * in ComputeEngineImpl.factors(n).
 *   
 * Optimization:
 * Keep the same sqrt(n) scanning approach and output format, but reduce work by
 * handling d=1 and d=2 explicitly, then scanning only odd d values up to sqrt(n).
 */
public class ComputeEngineImplFast implements ComputeEngineAPI {

    @Override
    public List<Integer> factors(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (n == 1) {
            return List.of(1);
        }

        List<Integer> small = new ArrayList<>();
        List<Integer> large = new ArrayList<>();

        small.add(1);
        large.add(n);

        if (n % 2 == 0) {
            small.add(2);
            int q = n / 2;
            if (q != 2) {
                large.add(q);
            }
        }

        for (int d = 3; (long) d * d <= n; d += 2) {
            if (n % d == 0) {
                small.add(d);
                int q = n / d;
                if (q != d) {
                    large.add(q);
                }
            }
        }

        Collections.reverse(large);
        small.addAll(large);
        return small;
    }
}

