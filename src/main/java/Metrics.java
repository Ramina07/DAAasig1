public class Metrics {
    private static int comparisons = 0;
    private static int assignments = 0;
    private static int recursionDepth = 0;

    public static void incrementComparisons() {
        comparisons++;
    }

    public static void incrementAssignments() {
        assignments++;
    }

    public static void incrementRecursionDepth() {
        recursionDepth++;
    }

    public static void reset() {
        comparisons = 0;
        assignments = 0;
        recursionDepth = 0;
    }

    public static int getComparisons() {
        return comparisons;
    }

    public static int getAssignments() {
        return assignments;
    }

    public static int getRecursionDepth() {
        return recursionDepth;
    }
}


