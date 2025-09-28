package org.example;

import java.util.Random;

public final class Utils {
    private static final Random RNG = new Random();

    private Utils() {}

    // --- Guards ---
    public static void requireArray(int[] a) {
        if (a == null) throw new IllegalArgumentException("array is null");
    }

    public static void requireRange(int lo, int hi, int length) {
        if (lo < 0 || hi >= length || lo > hi) {
            throw new IllegalArgumentException("bad range: [" + lo + "," + hi + "] for length " + length);
        }
    }

    // --- Swap ---
    public static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // --- Fisher–Yates shuffle (in-place) ---
    public static void shuffle(int[] a) {
        requireArray(a);
        for (int i = a.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    // --- Partition (Lomuto, pivot at hi) ---
    // Возвращает индекс пивота после разбиения. Счётчики Metrics — тут.
    public static int partition(int[] a, int lo, int hi) {
        requireArray(a);
        requireRange(lo, hi, a.length);

        int pivot = a[hi];
        Metrics.incrementAssignments(); // чтение pivot

        int i = lo;
        for (int j = lo; j < hi; j++) {
            Metrics.incrementComparisons();
            if (a[j] <= pivot) {
                // swap(a, i, j) + учёт присваиваний
                int t = a[i];                Metrics.incrementAssignments();
                a[i] = a[j];                 Metrics.incrementAssignments();
                a[j] = t;                    Metrics.incrementAssignments();
                i++;
            }
        }
        // swap(a, i, hi) + учёт присваиваний
        int t = a[i];                        Metrics.incrementAssignments();
        a[i] = a[hi];                        Metrics.incrementAssignments();
        a[hi] = t;                           Metrics.incrementAssignments();

        return i;
    }
}
