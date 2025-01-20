package org.example.repository.specification.impl;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;

public class PerimeterSpecification implements Specification<Quadrangle> {
    private final double minPerimeter;
    private final double maxPerimeter;
    private final QuadrangleCalculator calculator;

    private PerimeterSpecification(double minPerimeter, double maxPerimeter, QuadrangleCalculator calculator) {
        this.minPerimeter = minPerimeter;
        this.maxPerimeter = maxPerimeter;
        this.calculator = calculator;
    }

    public static Specification<Quadrangle> create(double minPerimeter, double maxPerimeter, QuadrangleCalculator calculator) {
        return new PerimeterSpecification(minPerimeter, maxPerimeter, calculator);
    }

    @Override
    public boolean specified(Quadrangle quadrangle) {
        double perimeter = calculator.calculatePerimeter(quadrangle);
        return perimeter >= minPerimeter && perimeter <= maxPerimeter;
    }
}
