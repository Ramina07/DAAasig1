package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    private static final Random RNG = new Random(42);

    @Test
    void selectsFixedArrayAllK() {
        int[] src = {9, 1, 5, 7, 3, 2, 8, 6, 4};
        int[] sorted = src.clone();
        Arrays.sort(sorted);
        for (int k = 0; k < src.length; k++) {
            int got = DeterministicSelect.select(src.clone(), k);
            assertEquals(sorted[k], got, "k=" + k);
        }
    }

    @Test
    void randomTrialsAgainstSort() {
        for (int t = 0; t < 100; t++) {
            int n = 1 + RNG.nextInt(200);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = RNG.nextInt(10_000) - 5_000;

            int[] b = a.clone();
            Arrays.sort(b);
            int k = RNG.nextInt(n);

            int got = DeterministicSelect.select(a, k);
            assertEquals(b[k], got, "trial=" + t + ", k=" + k);
        }
    }
}
