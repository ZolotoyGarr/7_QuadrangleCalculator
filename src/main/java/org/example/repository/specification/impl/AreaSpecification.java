package org.example.repository.specification.impl;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;

public class AreaSpecification implements Specification<Quadrangle> {
    private final double minArea;
    private final double maxArea;
    private final QuadrangleCalculator calculator;

    private AreaSpecification(double minArea, double maxArea, QuadrangleCalculator calculator) {
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.calculator = calculator;
    }

    public static Specification<Quadrangle> create(double minArea, double maxArea, QuadrangleCalculator calculator) {
        return new AreaSpecification(minArea, maxArea, calculator);
    }

    @Override
    public boolean specified(Quadrangle quadrangle) {
        double area = calculator.calculateArea(quadrangle);
        return area >= minArea && area <= maxArea;
    }
}
