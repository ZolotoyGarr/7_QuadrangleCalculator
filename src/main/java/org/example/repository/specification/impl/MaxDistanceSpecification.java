package org.example.repository.specification.impl;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;

public class MaxDistanceSpecification implements Specification<Quadrangle> {
    private final QuadrangleCalculator calculator;
    private final double threshold;

    private MaxDistanceSpecification(QuadrangleCalculator calculator, double threshold) {
        this.calculator = calculator;
        this.threshold = threshold;
    }

    public static Specification<Quadrangle> create(QuadrangleCalculator calculator, double threshold) {
        return new MaxDistanceSpecification(calculator, threshold);
    }

    @Override
    public boolean specified(Quadrangle quadrangle) {
        double maxDistance = calculator.calculateDistanceFromOrigin(quadrangle);
        return maxDistance > threshold;
    }
}
