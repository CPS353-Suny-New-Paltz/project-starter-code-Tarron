# Computation Description
The system will compute the factors of a given postiive integer. 
Eaxmple:
Input: 48
Output: 1, 2, 3, 4, 6, 8, 12, 16, 24, 48

# System Design Diagram
![System Design Diagram](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Tarron/blob/main/System%20Diagram.png)

## Multi-Threading
The system uses multithreading in the network layer to handle multiple compute requests concurrently.
A fixed-size thread pool is used with a defined upper bound on the number of threads: 4

## Benchmark Results (Before vs After)
Benchmark results from a representative run:
- Original (ComputeEngineImpl): 164.7 ms
- Optimized (ComputeEngineImplFast): 81.4 ms
- Improvement: 50.6%

## Benchmark Test Link
test/benchmark/PerformanceBenchmarkTest.java
https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Tarron/blob/main/test/benchmark/PerformanceBenchmarkTest.java

## Issue and Fix
Issue: 
The original factor computation was slow because it checked every possible divisor 
from 1 up to sqrt(n) for each input, which lead to many unnecessary modulus operations.

Fix: 
A faster compute engine implementation was added that skips even divisors after handling 
1 and 2, reducing the number of checks while producing the same factor results.


