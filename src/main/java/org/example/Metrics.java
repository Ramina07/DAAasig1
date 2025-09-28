package org.example;

public final class Metrics {
    private static long comparisons = 0;
    private static long assignments = 0;

    // глубина рекурсии (текущая и максимум)
    private static int currentDepth = 0;
    private static int maxDepth = 0;

    private Metrics() {}

    // --- reset/getters ---
    public static void reset() {
        comparisons = 0;
        assignments = 0;
        currentDepth = 0;
        maxDepth = 0;
    }

    public static long getComparisons() { return comparisons; }
    public static long getAssignments() { return assignments; }
    public static int  getMaxDepth()    { return maxDepth; }

    // --- counters ---
    public static void incrementComparisons() { comparisons++; }
    public static void incrementAssignments() { assignments++; }

    // Можно использовать пакетные "добавления", если надо
    public static void addComparisons(long c) { comparisons += c; }
    public static void addAssignments(long a) { assignments += a; }

    // --- учёт глубины рекурсии ---
    // Для точного учёта вызывай pushDepth() перед входом в рекурсивный вызов
    // и popDepth() сразу после возврата.
    public static void pushDepth() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public static void popDepth() {
        if (currentDepth > 0) currentDepth--;
    }

    // Совместимость с уже написанным кодом:
    public static void incrementRecursionDepth() { pushDepth(); }
    public static void decrementRecursionDepth() { popDepth(); }
}



