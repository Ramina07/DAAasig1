package org.example;

import java.util.Random;

public final class QuickSort {
    private static final Random RNG = new Random();
    private static final int INSERTION_CUTOFF = 16;

    private QuickSort() {}

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        quicksort(a, 0, a.length - 1);
    }

    private static void quicksort(int[] a, int lo, int hi) {
        while (lo < hi) {
            if (hi - lo + 1 <= INSERTION_CUTOFF) {
                insertionSort(a, lo, hi);
                return;
            }

            int p = lo + RNG.nextInt(hi - lo + 1);
            // пивот в конец
            int tmp = a[p];                   Metrics.incrementAssignments();
            a[p] = a[hi];                     Metrics.incrementAssignments();
            a[hi] = tmp;                      Metrics.incrementAssignments();

            int q = Utils.partition(a, lo, hi);

            // recurse smaller-first, larger tail-iterative
            Metrics.incrementRecursionDepth();
            if (q - 1 - lo < hi - (q + 1)) {
                if (lo < q - 1) quicksort(a, lo, q - 1);
                lo = q + 1;
            } else {
                if (q + 1 < hi) quicksort(a, q + 1, hi);
                hi = q - 1;
            }
        }
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];                   Metrics.incrementAssignments();
            int j = i - 1;
            while (j >= lo) {
                Metrics.incrementComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];         Metrics.incrementAssignments();
                    j--;
                } else break;
            }
            a[j + 1] = key;                   Metrics.incrementAssignments();
        }
    }
}
