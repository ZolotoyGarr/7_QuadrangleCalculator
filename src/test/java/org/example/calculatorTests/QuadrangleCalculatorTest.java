package org.example.calculatorTests;

import org.example.calculators.AnglesCalculator;
import org.example.calculators.PointsCalculator;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.VectorCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuadrangleCalculatorTest {
    //todo: замокать
    private final QuadrangleCalculator calculator = new QuadrangleCalculator(new PointsCalculator(), new AnglesCalculator(new VectorCalculator()));

    @Test
    void testCalculatePerimeter() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        ));

        // when
        double perimeter = calculator.calculatePerimeter(quadrangle);

        // then
        double expected = 14.0; // Стороны: 4 + 3 + 4 + 3
        Assertions.assertEquals(expected, perimeter, 1e-9);
    }

    @Test
    void testCalculateArea() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        ));

        // when
        double area = calculator.calculateArea(quadrangle);

        // then
        double expected = 12.0; // Площадь прямоугольника: 4 * 3
        Assertions.assertEquals(expected, area, 1e-9);
    }

    @Test
    void testIsQuadrangleTrue() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        ));

        // when
        boolean isQuadrangle = calculator.isQuadrangle(quadrangle);

        // then
        Assertions.assertTrue(isQuadrangle);
    }

    @Test
    void testIsQuadrangleFalse() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(4, 3) // Повторяющиеся точки
        ));

        // when
        boolean isQuadrangle = calculator.isQuadrangle(quadrangle);

        // then
        Assertions.assertFalse(isQuadrangle);
    }

    @Test
    void testFindQuadrangleTypeSquare() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 4),
                new Point(0, 4)
        ));

        // when
        QuadrangleType type = calculator.findQuadrangleType(quadrangle);

        // then
        Assertions.assertEquals(QuadrangleType.SQUARE, type);
    }

    @Test
    void testFindQuadrangleTypeRectangle() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(6, 0),
                new Point(6, 3),
                new Point(0, 3)
        ));

        // when
        QuadrangleType type = calculator.findQuadrangleType(quadrangle);

        // then
        Assertions.assertEquals(QuadrangleType.RECTANGLE, type);
    }

    @Test
    void testIsConvexTrue() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        ));

        // when
        boolean isConvex = calculator.isConvex(quadrangle);

        // then
        Assertions.assertTrue(isConvex);
    }

    @Test
    void testIsConvexFalse() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(2, -1), // Вогнутая точка
                new Point(0, 3)
        ));

        // when
        boolean isConvex = calculator.isConvex(quadrangle);

        // then
        Assertions.assertFalse(isConvex);
    }
}
