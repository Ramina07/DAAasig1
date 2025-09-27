public class Metrics {
    private static int comparisons = 0; // Количество сравнений
    private static int assignments = 0; // Количество присваиваний
    private static int recursionDepth = 0; // Глубина рекурсии

    // Увеличиваем количество сравнений
    public static void incrementComparisons() {
        comparisons++;
    }

    // Увеличиваем количество присваиваний
    public static void incrementAssignments() {
        assignments++;
    }

    // Увеличиваем глубину рекурсии
    public static void incrementRecursionDepth() {
        recursionDepth++;
    }

    // Сбрасываем все метрики
    public static void reset() {
        comparisons = 0;
        assignments = 0;
        recursionDepth = 0;
    }

    // Получаем количество сравнений
    public static int getComparisons() {
        return comparisons;
    }

    // Получаем количество присваиваний
    public static int getAssignments() {
        return assignments;
    }

    // Получаем глубину рекурсии
    public static int getRecursionDepth() {
        return recursionDepth;
    }
}

