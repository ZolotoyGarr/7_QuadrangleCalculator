package org.example.calculatorTests;

import org.example.calculators.VectorCalculator;
import org.example.model.Point;
import org.example.model.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorCalculatorTest {

    private final VectorCalculator vectorCalculator = new VectorCalculator();

    // given
    private static final Point START1 = new Point(0, 0);
    private static final Point END1 = new Point(3, 4);
    private static final Vector VECTOR1 = new Vector(START1, END1);

    private static final Point START2 = new Point(0, 0);
    private static final Point END2 = new Point(4, 0);
    private static final Vector VECTOR2 = new Vector(START2, END2);

    private static final Point START3 = new Point(0, 0);
    private static final Point END3 = new Point(0, 3);
    private static final Vector VECTOR3 = new Vector(START3, END3);

    @Test
    void testGetX() {
        // when
        double dx = vectorCalculator.getX(VECTOR1);

        // then
        double expected = 3.0; // END1.x - START1.x
        Assertions.assertEquals(expected, dx, 1e-9);
    }

    @Test
    void testGetY() {
        // when
        double dy = vectorCalculator.getY(VECTOR1);

        // then
        double expected = 4.0; // END1.y - START1.y
        Assertions.assertEquals(expected, dy, 1e-9);
    }

    @Test
    void testMagnitude() {
        // when
        double magnitude = vectorCalculator.magnitude(VECTOR1);

        // then
        double expected = 5.0; // sqrt(3^2 + 4^2)
        Assertions.assertEquals(expected, magnitude, 1e-9);
    }

    @Test
    void testDotProduct() {
        // when
        double dotProduct = vectorCalculator.dotProduct(VECTOR2, VECTOR3);

        // then
        double expected = 0.0; // Orthogonal vectors have dot product of 0
        Assertions.assertEquals(expected, dotProduct, 1e-9);
    }

    @Test
    void testCalculateAngle() {
        // when
        double angle = vectorCalculator.calculateAngle(VECTOR2, VECTOR3);

        // then
        double expected = 90.0; // Vectors are orthogonal
        Assertions.assertEquals(expected, angle, 1e-9);
    }

    @Test
    void testCalculateVectorProduct() {
        // when
        double vectorProduct = vectorCalculator.calculateVectorProduct(VECTOR2, VECTOR3);

        // then
        double expected = 12.0; // (4 * 3) - (0 * 0)
        Assertions.assertEquals(expected, vectorProduct, 1e-9);
    }
}
