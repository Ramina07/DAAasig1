package org.example;

import java.util.*;

public final class ClosestPair {

    // Вместо record — обычный класс (совместимо с Java 8+)
    public static final class Point {
        public final double x;
        public final double y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    private ClosestPair() {}

    /** Возвращает минимальную дистанцию между двумя точками. */
    public static double closestDistance(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] byX = pts.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[byX.length]; // буфер для merge по y
        return solve(byX, 0, byX.length, aux);
    }

    // [l, r) полуинтервал
    private static double solve(Point[] byX, int l, int r, Point[] aux) {
        int n = r - l;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = l; i < r; i++)
                for (int j = i + 1; j < r; j++)
                    best = Math.min(best, dist(byX[i], byX[j]));
            Arrays.sort(byX, l, r, Comparator.comparingDouble(p -> p.y));
            return best;
        }

        int mid = l + n / 2;
        double midX = byX[mid].x;

        double dl = solve(byX, l, mid, aux);
        double dr = solve(byX, mid, r, aux);
        double d = Math.min(dl, dr);

        // merge по y (как в mergesort), чтобы byX[l..r) стало отсортировано по y
        mergeByY(byX, l, mid, r, aux);

        // strip: точки с |x - midX| < d уже отсортированы по y
        int m = 0;
        for (int i = l; i < r; i++) {
            if (Math.abs(byX[i].x - midX) < d) aux[m++] = byX[i];
        }

        for (int i = 0; i < m; i++) {
            // проверяем до ~7 ближайших по y
            for (int j = i + 1; j < m && (aux[j].y - aux[i].y) < d; j++) {
                d = Math.min(d, dist(aux[i], aux[j]));
            }
        }
        return d;
    }

    private static void mergeByY(Point[] a, int l, int mid, int r, Point[] aux) {
        int i = l, j = mid, k = 0;
        while (i < mid && j < r) {
            if (a[i].y <= a[j].y) aux[k++] = a[i++];
            else aux[k++] = a[j++];
        }
        while (i < mid) aux[k++] = a[i++];
        while (j < r) aux[k++] = a[j++];
        for (int t = 0; t < k; t++) a[l + t] = aux[t];
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }

    /** Наивная O(n^2) — полезна для тестов на малых n. */
    public static double brute(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++)
            for (int j = i + 1; j < pts.length; j++)
                best = Math.min(best, dist(pts[i], pts[j]));
        return best;
    }
}

