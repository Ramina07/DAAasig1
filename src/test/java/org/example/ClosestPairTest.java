package org.example;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    private static final Random RNG = new Random(123);
    private static final double EPS = 1e-9;

    @Test
    void smallFixed() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0,0),
                new ClosestPair.Point(3,4),
                new ClosestPair.Point(1,1),
                new ClosestPair.Point(2,2)
        };
        double d = ClosestPair.closestDistance(pts);
        assertEquals(Math.sqrt(2), d, EPS);
    }

    @Test
    void manyRandomVsBrute() {
        for (int t = 0; t < 50; t++) {
            int n = 10 + RNG.nextInt(100);
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(
                        RNG.nextDouble()*1000 - 500,
                        RNG.nextDouble()*1000 - 500
                );
            }
            double fast = ClosestPair.closestDistance(pts);
            double slow = ClosestPair.brute(pts);
            assertEquals(slow, fast, EPS);
        }
    }
}

