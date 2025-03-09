package org.example.calculatorTests;

import org.example.calculators.PointsCalculator;
import org.example.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointsCalculatorTest {
    private final PointsCalculator pointsCalculator = new PointsCalculator();

    @Test
    void testCalculateDistanceBetweenPoints() {
        // given
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);

        // when
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p2);

        // then
        double expected = 5.0; // Расстояние по теореме Пифагора: sqrt(3^2 + 4^2) = 5
        Assertions.assertEquals(expected, distance, 1e-9);
    }

    @Test
    void testCalculateDistanceBetweenPointsZeroDistance() {
        // given
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        // when
        double distance = pointsCalculator.calculateDistanceBetweenPoints(p1, p2);

        // then
        double expected = 0.0;
        Assertions.assertEquals(expected, distance, 1e-9);
    }

    @Test
    void testIsCollinearPointsTrue() {
        // given
        Point p1 = new Point(0, 0);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(4, 4);

        // when
        boolean isCollinear = pointsCalculator.isCollinearPoints(p1, p2, p3);

        // then
        Assertions.assertTrue(isCollinear);
    }

    @Test
    void testIsCollinearPointsFalse() {
        // given
        Point p1 = new Point(0, 0);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(4, 3);

        // when
        boolean isCollinear = pointsCalculator.isCollinearPoints(p1, p2, p3);

        // then
        Assertions.assertFalse(isCollinear);
    }
}
