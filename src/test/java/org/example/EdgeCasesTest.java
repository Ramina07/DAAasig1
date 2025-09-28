package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesTest {

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    public void testSingleElement() {
        int[] arr = {1};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1}, arr);
    }

    @Test
    public void testDuplicates() {
        int[] arr = {3, 3, 3, 3};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{3, 3, 3, 3}, arr);
    }

    @Test
    public void testGeneralCase() {
        int[] arr = {4, 3, 2, 1};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4}, arr);
    }

    @Test
    public void testLargeArray() {
        int[] arr = new int[10000];
        for (int i = 0; i < 10000; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        MergeSort.sort(arr);  // Просто проверяем, не выбрасывается ли исключение
    }

    @Test
    public void testInvalidIndex() {
        int[] arr = {1, 2, 3};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            arr[5] = 100;  // Индекс выходит за пределы массива
        });
    }
}

