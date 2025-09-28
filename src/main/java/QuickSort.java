public class QuickSort {

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1); // Сортировка меньшей части
            quickSort(arr, pi + 1, high); // Сортировка большей части
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            Metrics.incrementComparisons(); // Увеличиваем количество сравнений
            if (arr[j] <= pivot) {
                i++;
                // Меняем местами элементы
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                Metrics.incrementAssignments(); // Увеличиваем количество присваиваний
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        Metrics.incrementAssignments(); // Увеличиваем количество присваиваний
        return i + 1;
    }
}
