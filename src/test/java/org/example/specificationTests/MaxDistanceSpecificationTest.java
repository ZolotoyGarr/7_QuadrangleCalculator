package org.example.specificationTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;
import org.example.repository.specification.impl.MaxDistanceSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class MaxDistanceSpecificationTest {

    QuadrangleCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = mock(QuadrangleCalculator.class);
    }

    @Test
    void testSpecifiedAboveThreshold() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10)
        ));
        when(calculator.calculateDistanceFromOrigin(quadrangle)).thenReturn(14.14);
        Specification<Quadrangle> specification = MaxDistanceSpecification.create(calculator, 10.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to have a max distance above the threshold.");
        verify(calculator, times(1)).calculateDistanceFromOrigin(quadrangle);
    }

    @Test
    void testSpecifiedBelowThreshold() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(5, 0), new Point(5, 5), new Point(0, 5)
        ));
        when(calculator.calculateDistanceFromOrigin(quadrangle)).thenReturn(7.07);
        Specification<Quadrangle> specification = MaxDistanceSpecification.create(calculator, 10.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle to have a max distance below the threshold.");
        verify(calculator, times(1)).calculateDistanceFromOrigin(quadrangle);
    }

    @Test
    void testSpecifiedOnThreshold() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(6, 0), new Point(6, 8), new Point(0, 8)
        ));
        when(calculator.calculateDistanceFromOrigin(quadrangle)).thenReturn(10.0);
        Specification<Quadrangle> specification = MaxDistanceSpecification.create(calculator, 10.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle not to exceed the threshold.");
        verify(calculator, times(1)).calculateDistanceFromOrigin(quadrangle);
    }
}
