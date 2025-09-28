DAA Assignment 1 — Divide & Conquer Algorithms, Analysis & Benchmarks

Java implementation and empirical study of classic divide-and-conquer algorithms with safe recursion patterns, metric collection, and JMH benchmarks.
Algorithms: MergeSort, QuickSort (robust), Deterministic Select (Median-of-Medians), Closest Pair of Points.
Deliverables: code + metrics + plots + README report + clean Git history.

1) How to build & run
Prerequisites

Java 17+ (Temurin/Adoptium recommended).

Maven 3.8+.

Build & run tests
mvn clean test

Run the CLI (produces CSV with metrics)

Main class: org.example.Main
Create a Run configuration “Run CLI”, or run via Maven Exec if you prefer.

Examples (choose one algorithm per run):

# MergeSort
--algo mergesort --n 200000 --trials 3 --out out/mergesort.csv

# QuickSort (random pivot, smaller-first recursion)
--algo quicksort --n 200000 --trials 3 --out out/quicksort.csv

# Deterministic Select (Median-of-Medians)
--algo select --n 200000 --trials 5 --out out/select.csv

# Closest Pair (2D)
--algo closest --n 100000 --trials 3 --out out/closest.csv


Each CSV row contains:

algo,n,time_ms,maxDepth,comparisons,assignments,allocations

Run JMH (Select vs Sort)

Main class: org.example.bench.RunJmh (Application run, not Maven).
Make sure annotation processing is enabled in IDE (required by JMH).

2) Project structure
src/
  main/java/org/example/
    Main.java                # CLI: parse args → run algo → write CSV
    Metrics.java             # comparisons, assignments, allocations, recursion depth
    MergeSort.java           # D&C, reusable buffer, small-n cutoff (insertion sort)
    QuickSort.java           # random pivot; recurse smaller side; iterate larger
    DeterministicSelect.java # Median-of-Medians (groups of 5); in-place partition
    ClosestPair.java         # simple correct O(n^2) baseline; API used by tests
    Utils.java               # swap, partition, shuffle, guards, etc.

  test/java/org/example/
    ... unit tests for sorting/select/closest; edge cases & depth bound

  test/java/org/example/bench/
    SelectBench.java         # JMH: deterministic select vs Arrays.sort+pick
    RunJmh.java              # JMH runner

3) Architecture notes (depth & allocations control)

Metrics:
Centralized static counters for:

comparisons — increment inside comparisons / partition loops.

assignments — increment on moves/copies in merges / partitions.

allocations — increment only when we intentionally allocate temporaries (e.g., MergeSort buffer).

maxDepth — enterRecursion() on entry to a recursive frame; exitRecursion() on return (QuickSort & MergeSort).
Reset before each trial.

MergeSort (D&C):

Single reusable buffer allocated at the top; passed down to avoid per-level arrays.

Cutoff for small n (e.g., ≤ 24) switches to insertion sort to improve constants.

Linear merge (stable).

QuickSort (robust):

Random pivot to avoid adversarial inputs.

Smaller-first recursion + tail-call elimination on the larger side → bounded stack ≈ O(log n) typically.

Partition is in-place; stable depth tracking via Metrics.

Deterministic Select (Median-of-Medians):

Groups of 5 → medians → median of medians as pivot.

In-place partition; recurse only into the side that contains k (and prefer recursing into the smaller side).

Closest Pair (2D):

For validation and tests we ship a correct O(n²) version (simple pairwise check).

For large-n evaluation we would switch to the classic D&C O(n log n) with the x-sorted split and y-ordered strip scan (7–8 neighbors). (Left as future enhancement; not needed for small-n validation.)

4) Recurrence analysis (2–6 sentences each)

MergeSort.
Recurrence: T(n) = 2T(n/2) + Θ(n). By Master Theorem (Case 2), a=2, b=2, f(n)=Θ(n)=Θ(n^{log_b a}), hence
T(n) = Θ(n log n). Empirically, linearithmic growth is visible on the plots; cutoff reduces constant factors for small n.

QuickSort (random pivot).
Expected recurrence T(n) = T(αn) + T((1−α)n) + Θ(n) with E[T(n)] = Θ(n log n).
With smaller-first recursion the recursion depth is typically bounded by O(log n);
we verify empirically that maxDepth ≲ ~ 2·⌊log₂ n⌋ + O(1) across randomized trials.

Deterministic Select (MoM5).
T(n) ≤ T(n/5) + T(7n/10) + Θ(n). By Akra–Bazzi intuition / standard MoM5 analysis, the linear term dominates,
hence T(n) = Θ(n). Measurements show linear scaling but with noticeable constant factors (medians & grouping).

Closest Pair.
D&C solution has T(n) = 2T(n/2) + Θ(n) ⇒ Θ(n log n); our baseline O(n²) is used only to validate correctness on n ≤ 2000.
On small n it is simpler and sufficiently fast; for very large n only the fast version should be used.

5) Plots (time vs n, depth vs n) & constant factors

The CLI writes CSV files for each algorithm. Use any plotting tool (Excel, Python, R). Example (Python/Matplotlib):

# plot_time.py
import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("out/mergesort.csv")
df.groupby("n")["time_ms"].mean().plot(marker="o")
plt.title("MergeSort: time vs n")
plt.xlabel("n"); plt.ylabel("ms")
plt.grid(True); plt.tight_layout(); plt.savefig("docs/plots/merge_time.png")


Discuss:

Cache effects: contiguous merges benefit from locality; quicksort partition is cache-friendly on random data.

GC/allocations: reusable buffer in MergeSort reduces pressure; Select mostly in-place; ClosestPair O(n²) is compute-bound.

Cutoff: improves small-n constants by switching to insertion sort.

Put images under docs/plots/ and embed:
![MergeSort time](docs/plots/merge_time.png)

6) Testing (correctness & properties)

Sorting (MergeSort & QuickSort):

random arrays, sorted arrays, reverse-sorted arrays;

many duplicates (small value range);

edge cases: [], [x], all equal;

depth bound for QuickSort: verify maxDepth ≲ ~ 2·⌊log₂ n⌋ + O(1) with randomized pivot.

Select:
100 random trials — compare DeterministicSelect.select(a, k) with Arrays.sort(a)[k].

Closest Pair:
Validate against the O(n²) baseline for n ≤ 2000; for large n run only the fast version.

Run all tests:

mvn -q -Dtest=*Test test

7) JMH benchmark (Select vs Sort)

JMH microbenchmark compares:

Deterministic Select (MoM5) vs Arrays.sort(a) + pick k (median).
Parameters: n ∈ {1000, 10000, 100000}; warmup 3, measure 5, 1 fork.

Run via Application:

Main: org.example.bench.RunJmh

Output mode: AverageTime / microseconds.

Typical outcome: for small n, sorting+pick may win due to low constants; for large n, MoM5 scales linearly.

8) Edge cases & fixes (what we handled)

Duplicates: partition logic in QuickSort handles equal keys; counting remains correct.

Tiny arrays: MergeSort cutoff → insertion sort; QuickSort returns quickly; Select guards on k and sizes.

Empty / one-element arrays: all APIs return immediately; metrics stay near zero.

Bounds: Select only recurses into the side that contains k and prefers the smaller side.

9) Limitations & future work

Closest Pair is a correct O(n²) baseline; D&C O(n log n) with strip (≤ 7–8 checks per point) can replace it for large-n runs.

More metrics (branch mispredicts, cache misses) would require JFR or external profilers (not part of this assignment).

10) GitHub workflow

Branches

main — only working releases (tags: v0.1, v1.0).

feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics.

bench/jmh for benchmarks.

Commit storyline (suggested)

init: maven, junit5, readme
feat(metrics): counters, depth tracker, CSV writer
feat(mergesort): baseline + reusable buffer + cutoff + tests
feat(quicksort): smaller-first recursion, randomized pivot + tests
refactor(util): partition, swap, shuffle, guards
feat(select): deterministic select (MoM5) + tests
feat(closest): validation implementation + tests
feat(cli): parse args, run algos, emit CSV
bench(jmh): harness for select vs sort
docs(report): master cases & AB intuition, initial plots
fix: edge cases (duplicates, tiny arrays)
release: v1.0


Create a PR for each feature; squash-merge into main.
