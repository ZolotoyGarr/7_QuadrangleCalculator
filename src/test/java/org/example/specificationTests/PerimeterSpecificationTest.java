package org.example.specificationTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;
import org.example.repository.specification.impl.PerimeterSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class PerimeterSpecificationTest {

    private final QuadrangleCalculator calculator = mock(QuadrangleCalculator.class);

    @Test
    void testSpecifiedWithinRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
        when(calculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        Specification<Quadrangle> specification = PerimeterSpecification.create(10.0, 15.0, calculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the perimeter range.");
        verify(calculator, times(1)).calculatePerimeter(quadrangle);
    }

    @Test
    void testSpecifiedBelowRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));
        when(calculator.calculatePerimeter(quadrangle)).thenReturn(8.0);
        Specification<Quadrangle> specification = PerimeterSpecification.create(10.0, 15.0, calculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle to have a perimeter below the range.");
        verify(calculator, times(1)).calculatePerimeter(quadrangle);
    }

    @Test
    void testSpecifiedAboveRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(6, 0), new Point(6, 5), new Point(0, 5)
        ));
        when(calculator.calculatePerimeter(quadrangle)).thenReturn(22.0);
        Specification<Quadrangle> specification = PerimeterSpecification.create(10.0, 20.0, calculator);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle to have a perimeter above the range.");
        verify(calculator, times(1)).calculatePerimeter(quadrangle);
    }


    @Test
    void testCalculatorInteraction() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
        when(calculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        Specification<Quadrangle> specification = PerimeterSpecification.create(10.0, 15.0, calculator);

        // when
        specification.specified(quadrangle);

        // then
        verify(calculator, atLeastOnce()).calculatePerimeter(quadrangle);
    }
}
