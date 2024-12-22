package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorTest {
    //given
    private static final Point START1 = new Point(0, 0);
    private static final Point END1 = new Point(1, 2);
    private static final Point START2 = new Point(0, 0);
    private static final Point END2 = new Point(3, 1);

    private static final Vector VECTOR1 = new Vector(START1, END1);
    private static final Vector VECTOR2 = new Vector(START2, END2);

    VectorCalculator vectorCalculator = new VectorCalculator();
    @Test
    void testGetX() {
        //when
        double realValue = vectorCalculator.getX(VECTOR1);
        //then
        final double expectedValue = 1.0;
        Assertions.assertEquals(expectedValue, realValue);
    }
    @Test
    void testGetY() {
        //when
        final double realValue = vectorCalculator.getY(VECTOR1);
        //then
        final double expectedValue = 2.0;
        Assertions.assertEquals(expectedValue, realValue);
    }
    @Test
    void testMagnitude() {
        //when
        final double realValue = vectorCalculator.magnitude(VECTOR1);
        //then
        final double expectedValue = 2.236067977;
        Assertions.assertEquals(expectedValue, realValue, 1e-9);
    }
    @Test
    void testDotProduct() {
        //when
        final double realValue = vectorCalculator.dotProduct(VECTOR1, VECTOR2);
        //then
        final double expectedValue = 5.0;
        Assertions.assertEquals(expectedValue, realValue);
    }
}
