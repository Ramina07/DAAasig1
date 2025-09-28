package org.example.bench;

import org.example.DeterministicSelect;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Сравнение: O(n) deterministic select (MoM5) vs Arrays.sort + pick k.
 * Запускаем на массивах двух размеров.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
public class SelectBench {

    @Param({"10000", "100000"})
    public int n;

    private int[] base;
    private int k;
    private Random rng;

    @Setup(Level.Trial)
    public void setup() {
        rng = new Random(123);
        base = new int[n];
        for (int i = 0; i < n; i++) {
            base[i] = rng.nextInt(1_000_000) - 500_000;
        }
        k = n / 2; // медиана
    }

    @Benchmark
    public void deterministicSelect(Blackhole bh) {
        int[] a = base.clone();
        int v = DeterministicSelect.select(a, k);
        bh.consume(v);
    }

    @Benchmark
    public void sortThenPick(Blackhole bh) {
        int[] a = base.clone();
        Arrays.sort(a);
        int v = a[k];
        bh.consume(v);
    }
}

