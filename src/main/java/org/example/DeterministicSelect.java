package org.example;

import java.util.Arrays;

/**
 * Deterministic Select: Median-of-Medians (groups of 5).
 * k — 0-based: 0 -> минимум, n-1 -> максимум.
 * Работает in-place, рекурсирует только в нужную сторону
 * (и предпочитает меньший подотрезок).
 */
public final class DeterministicSelect {
    private static final int GROUP = 5;

    private DeterministicSelect() {}

    /** Возвращает k-й по порядку статистики элемент массива a (0-based). */
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k);
    }

    private static int select(int[] a, int lo, int hi, int k) {
        while (true) {
            if (lo == hi) return a[lo];

            int pivotIdx = choosePivotMedianOfMedians(a, lo, hi);

            // переносим pivot в конец и считаем присваивания
            Utils.swap(a, pivotIdx, hi);
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();

            int p = Utils.partition(a, lo, hi); // pivot в позиции p
            int leftSize = p - lo;

            if (k == leftSize) return a[p];
            if (k < leftSize) {
                // ищем слева — оставляем меньший диапазон
                hi = p - 1;
            } else {
                // ищем справа — сдвигаем k относительно правой части
                k -= (leftSize + 1);
                lo = p + 1;
            }
        }
    }

    /** Выбирает индекс pivot по схеме median-of-medians. */
    private static int choosePivotMedianOfMedians(int[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= GROUP) {
            Arrays.sort(a, lo, hi + 1);
            return lo + n / 2;
        }

        // разбиваем на группы по 5, сортируем их и сносим медианы в начало
        int numGroups = (n + GROUP - 1) / GROUP;
        for (int g = 0; g < numGroups; g++) {
            int gLo = lo + g * GROUP;
            int gHi = Math.min(gLo + GROUP - 1, hi);
            Arrays.sort(a, gLo, gHi + 1);
            int med = gLo + (gHi - gLo) / 2;

            Utils.swap(a, lo + g, med);
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();
        }

        // теперь медианы лежат в [lo..lo+numGroups-1]; найдём их медиану (по индексу)
        int medIndexAmongMedians = lo + numGroups / 2;
        return selectIndexWithinRange(a, lo, lo + numGroups - 1, medIndexAmongMedians);
    }

    /** Возвращает ИНДЕКС k-го (по индексу target) элемента в диапазоне [lo..hi]. */
    private static int selectIndexWithinRange(int[] a, int lo, int hi, int target) {
        int k = target - lo; // 0-based внутри подмассива
        while (true) {
            if (lo == hi) return lo;

            int pivotIdx = choosePivotMedianOfMedians(a, lo, hi);
            Utils.swap(a, pivotIdx, hi);
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();
            Metrics.incrementAssignments();

            int p = Utils.partition(a, lo, hi);
            int leftSize = p - lo;

            if (k == leftSize) return p;
            if (k < leftSize) {
                hi = p - 1;
            } else {
                k -= (leftSize + 1);
                lo = p + 1;
            }
        }
    }
}
