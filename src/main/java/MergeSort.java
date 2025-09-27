public class MergeSort {

    // Основной метод для сортировки
    public static void sort(int[] arr) {
        if (arr.length < 2) {
            return;  // Если массив содержит меньше двух элементов, он уже отсортирован
        }

        // Разделяем массив на две части
        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        System.arraycopy(arr, 0, left, 0, mid);   // Копируем левую часть
        System.arraycopy(arr, mid, right, 0, arr.length - mid); // Копируем правую часть

        // Рекурсивно сортируем обе части
        sort(left);
        sort(right);

        // Сливаем отсортированные части
        merge(arr, left, right);
    }

    // Метод для слияния двух отсортированных массивов
    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Слияние элементов до тех пор, пока не обработаем один из массивов
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        // Копируем оставшиеся элементы из левого массива (если они есть)
        while (i < left.length) {
            arr[k++] = left[i++];
        }

        // Копируем оставшиеся элементы из правого массива (если они есть)
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }
}
