package org.example;

public final class MergeSort {

    // Устанавливаем порог для использования insertion sort
    private static final int CUTOFF = 24; // на малых n лучше использовать insertion sort

    public static void sort(int[] arr) {
        if (arr.length < 2) {
            return;  // Если массив содержит меньше двух элементов, он уже отсортирован
        }

        // Разделяем массив на две части
        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        // Копируем элементы в левый и правый массивы
        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        // Рекурсивно сортируем обе части
        Metrics.incrementRecursionDepth();  // Увеличиваем глубину рекурсии
        sort(left);
        sort(right);

        // Сливаем отсортированные части
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Слияние элементов до тех пор, пока не обработаем один из массивов
        while (i < left.length && j < right.length) {
            Metrics.incrementComparisons();  // Увеличиваем количество сравнений
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
                Metrics.incrementAssignments();  // Увеличиваем количество присваиваний
            } else {
                arr[k++] = right[j++];
                Metrics.incrementAssignments();  // Увеличиваем количество присваиваний
            }
        }

        // Копируем оставшиеся элементы из левого массива (если они есть)
        while (i < left.length) {
            arr[k++] = left[i++];
            Metrics.incrementAssignments();  // Увеличиваем количество присваиваний
        }

        // Копируем оставшиеся элементы из правого массива (если они есть)
        while (j < right.length) {
            arr[k++] = right[j++];
            Metrics.incrementAssignments();  // Увеличиваем количество присваиваний
        }
    }

    // --- Insertion Sort (для малых массивов) ---
    public static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i], j = i - 1;
            while (j >= lo && a[j] > x) {
                a[j + 1] = a[j];
                Metrics.incrementAssignments();  // учёт присваиваний
                j--;
            }
            a[j + 1] = x;
            Metrics.incrementAssignments();  // учёт присваиваний
        }
    }
}

