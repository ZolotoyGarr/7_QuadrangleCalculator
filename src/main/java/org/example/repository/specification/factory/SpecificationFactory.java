package org.example.repository.specification.factory;

import org.example.model.Quadrangle;
import org.example.calculators.QuadrangleCalculator;
import org.example.repository.specification.Specification;
import org.example.repository.specification.impl.*;

import java.util.UUID;

public class SpecificationFactory {

    private final QuadrangleCalculator calculator;

    public SpecificationFactory(QuadrangleCalculator calculator) {
        this.calculator = calculator;
    }

    public Specification<Quadrangle> createMaxDistanceSpecification(double threshold) {
        return MaxDistanceSpecification.create(calculator, threshold);
    }

    public Specification<Quadrangle> createAreaSpecification(double minArea, double maxArea) {
        return AreaSpecification.create(minArea, maxArea, calculator);
    }

    public Specification<Quadrangle> createPerimeterSpecification(double minPerimeter, double maxPerimeter) {
        return PerimeterSpecification.create(minPerimeter, maxPerimeter, calculator);
    }

    public Specification<Quadrangle> createIdSpecification(UUID id) {
        return IdSpecification.create(id);
    }
}
