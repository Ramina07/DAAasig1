package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    public void testQuickSort() {
        int[] input = {5, 2, 9, 1, 5, 6};
        int[] expected = {1, 2, 5, 5, 6, 9};
        QuickSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testQuickSortEmptyArray() {
        int[] input = {};
        int[] expected = {};
        QuickSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testQuickSortSingleElement() {
        int[] input = {7};
        int[] expected = {7};
        QuickSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testQuickSortNegativeNumbers() {
        int[] input = {-3, -1, -4, -2, -5};
        int[] expected = {-5, -4, -3, -2, -1};
        QuickSort.sort(input);
        assertArrayEquals(expected, input);
    }
}

