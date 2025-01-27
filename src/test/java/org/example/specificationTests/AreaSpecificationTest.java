package org.example.specificationTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;
import org.example.repository.specification.factory.SpecificationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class AreaSpecificationTest {

    private QuadrangleCalculator mockCalculator;
    private SpecificationFactory mockSpecificationFactory;

    @BeforeEach
    void setUp() {
        mockCalculator = mock(QuadrangleCalculator.class);
        mockSpecificationFactory = new SpecificationFactory(mockCalculator);
    }

    @Test
    void testSpecifiedWithinRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);

        // Using the factory to create the specification
        Specification<Quadrangle> specification = mockSpecificationFactory.createAreaSpecification(10.0, 15.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the area range.");
        verify(mockCalculator, times(1)).calculateArea(quadrangle); // Ensure the method was called once
    }

    @Test
    void testSpecifiedBelowRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 1), new Point(0, 1)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(2.0);

        // Using the factory to create the specification
        Specification<Quadrangle> specification = mockSpecificationFactory.createAreaSpecification(5.0, 10.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle to have an area below the range.");
        verify(mockCalculator, times(1)).calculateArea(quadrangle);
    }

    @Test
    void testSpecifiedAboveRange() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(6, 0), new Point(6, 5), new Point(0, 5)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(30.0);

        // Using the factory to create the specification
        Specification<Quadrangle> specification = mockSpecificationFactory.createAreaSpecification(10.0, 25.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the quadrangle to have an area above the range.");
        verify(mockCalculator, times(1)).calculateArea(quadrangle);
    }

    @Test
    void testSpecifiedOnLowerBound() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 2), new Point(0, 2)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(8.0);

        // Using the factory to create the specification
        Specification<Quadrangle> specification = mockSpecificationFactory.createAreaSpecification(8.0, 15.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the lower bound of the area range.");
        verify(mockCalculator, times(1)).calculateArea(quadrangle);
    }

    @Test
    void testSpecifiedOnUpperBound() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(5, 0), new Point(5, 4), new Point(0, 4)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(20.0);

        // Using the factory to create the specification
        Specification<Quadrangle> specification = mockSpecificationFactory.createAreaSpecification(10.0, 20.0);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the quadrangle to satisfy the upper bound of the area range.");
        verify(mockCalculator, times(1)).calculateArea(quadrangle);
    }
}
