package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    @Test
    public void testMergeSort() {
        int[] input = {5, 2, 9, 1, 5, 6};
        int[] expected = {1, 2, 5, 5, 6, 9};
        MergeSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testMergeSortEmptyArray() {
        int[] input = {};
        int[] expected = {};
        MergeSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testMergeSortSingleElement() {
        int[] input = {7};
        int[] expected = {7};
        MergeSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testMergeSortNegativeNumbers() {
        int[] input = {-3, -1, -4, -2, -5};
        int[] expected = {-5, -4, -3, -2, -1};
        MergeSort.sort(input);
        assertArrayEquals(expected, input);
    }
}
