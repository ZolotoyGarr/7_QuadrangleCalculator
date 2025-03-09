package org.example.calculatorTests;

import org.example.calculators.AnglesCalculator;
import org.example.calculators.VectorCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AnglesCalculatorTest {
    // given
    private static final Point P1 = new Point(0, 0);
    private static final Point P2 = new Point(4, 0);
    private static final Point P3 = new Point(4, 3);
    private static final Point P4 = new Point(0, 3);

    private static final Quadrangle RECTANGLE = new Quadrangle(List.of(P1, P2, P3, P4));
    private static final VectorCalculator VECTOR_CALCULATOR = new VectorCalculator();
    private static final AnglesCalculator ANGLES_CALCULATOR = new AnglesCalculator(VECTOR_CALCULATOR);

    @Test
    void testCalculateAngles() {
        // when
        List<Double> angles = ANGLES_CALCULATOR.calculateAngles(RECTANGLE);

        // then
        Assertions.assertEquals(4, angles.size());
        angles.forEach(angle -> Assertions.assertEquals(90.0, angle, 1e-9));
    }

    @Test
    void testIsAnglesRightTrue() {
        // when
        List<Double> angles = ANGLES_CALCULATOR.calculateAngles(RECTANGLE);
        boolean result = ANGLES_CALCULATOR.isAnglesRight(angles);

        // then
        Assertions.assertTrue(result);
    }

    @Test
    void testIsAnglesRightFalse() {
        // given
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 0);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(0, 3);
        Quadrangle nonRectangle = new Quadrangle(List.of(p1, p2, p3, p4));

        // when
        List<Double> angles = ANGLES_CALCULATOR.calculateAngles(nonRectangle);
        boolean result = ANGLES_CALCULATOR.isAnglesRight(angles);

        // then
        Assertions.assertFalse(result);
    }

    @Test
    void testIsConvexTrue() {
        // when
        boolean result = ANGLES_CALCULATOR.isConvex(RECTANGLE);

        // then
        Assertions.assertTrue(result);
    }

    @Test
    void testIsConvexFalse() {
        // given
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 0);
        Point p3 = new Point(2, -1); // Нарушает выпуклость
        Point p4 = new Point(0, 3);
        Quadrangle concaveQuadrangle = new Quadrangle(List.of(p1, p2, p3, p4));

        // when
        boolean result = ANGLES_CALCULATOR.isConvex(concaveQuadrangle);

        // then
        Assertions.assertFalse(result);
    }

}
