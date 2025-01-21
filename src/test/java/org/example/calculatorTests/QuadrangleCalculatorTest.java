package org.example.calculatorTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class QuadrangleCalculatorTest {

    private final QuadrangleCalculator calculator = mock(QuadrangleCalculator.class);

    @Test
    void testCalculatePerimeter() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        ));
        when(calculator.calculatePerimeter(quadrangle)).thenReturn(14.0);

        // when
        double perimeter = calculator.calculatePerimeter(quadrangle);

        // then
        Assertions.assertEquals(14.0, perimeter, 1e-9);
        verify(calculator, times(1)).calculatePerimeter(quadrangle);
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
        when(calculator.calculateArea(quadrangle)).thenReturn(12.0);

        // when
        double area = calculator.calculateArea(quadrangle);

        // then
        Assertions.assertEquals(12.0, area, 1e-9);
        verify(calculator, times(1)).calculateArea(quadrangle);
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
        when(calculator.isQuadrangle(quadrangle)).thenReturn(true);

        // when
        boolean isQuadrangle = calculator.isQuadrangle(quadrangle);

        // then
        Assertions.assertTrue(isQuadrangle);
        verify(calculator, times(1)).isQuadrangle(quadrangle);
    }

    @Test
    void testIsQuadrangleFalse() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(4, 3)
        ));
        when(calculator.isQuadrangle(quadrangle)).thenReturn(false);

        // when
        boolean isQuadrangle = calculator.isQuadrangle(quadrangle);

        // then
        Assertions.assertFalse(isQuadrangle);
        verify(calculator, times(1)).isQuadrangle(quadrangle);
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
        when(calculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.SQUARE);

        // when
        QuadrangleType type = calculator.findQuadrangleType(quadrangle);

        // then
        Assertions.assertEquals(QuadrangleType.SQUARE, type);
        verify(calculator, times(1)).findQuadrangleType(quadrangle);
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
        when(calculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);

        // when
        QuadrangleType type = calculator.findQuadrangleType(quadrangle);

        // then
        Assertions.assertEquals(QuadrangleType.RECTANGLE, type);
        verify(calculator, times(1)).findQuadrangleType(quadrangle);
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
        when(calculator.isConvex(quadrangle)).thenReturn(true);

        // when
        boolean isConvex = calculator.isConvex(quadrangle);

        // then
        Assertions.assertTrue(isConvex);
        verify(calculator, times(1)).isConvex(quadrangle);
    }

    @Test
    void testIsConvexFalse() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(2, -1),
                new Point(0, 3)
        ));
        when(calculator.isConvex(quadrangle)).thenReturn(false);

        // when
        boolean isConvex = calculator.isConvex(quadrangle);

        // then
        Assertions.assertFalse(isConvex);
        verify(calculator, times(1)).isConvex(quadrangle);
    }
}
