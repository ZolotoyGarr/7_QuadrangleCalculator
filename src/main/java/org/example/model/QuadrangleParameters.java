package org.example.model;

import org.example.calculators.AnglesCalculator;
import org.example.calculators.PointsCalculator;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.VectorCalculator;

public class QuadrangleParameters {
    private final QuadrangleCalculator calculator;
    private final double area;
    private final double perimeter;
    private final QuadrangleType type;
    private final boolean isConvex;

    public QuadrangleParameters(QuadrangleCalculator quadrangleCalculator, Quadrangle quadrangle) {
        this.calculator = quadrangleCalculator;
        this.area = quadrangleCalculator.calculateArea(quadrangle);
        this.perimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        this.type = quadrangleCalculator.findQuadrangleType(quadrangle);
        this.isConvex = quadrangleCalculator.isConvex(quadrangle);
    }

    public QuadrangleParameters(QuadrangleCalculator calculator, double area, double perimeter, QuadrangleType type, boolean isConvex) {
        this.calculator = calculator;
        this.area = area;
        this.perimeter = perimeter;
        this.type = type;
        this.isConvex = isConvex;
    }

    public double getArea() {
        return area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public QuadrangleType getType() {
        return type;
    }

    public boolean isConvex() {
        return isConvex;
    }

    @Override
    public String toString() {
        return "QuadrangleParameters{" +
                "area=" + area +
                ", perimeter=" + perimeter +
                ", type=" + type +
                ", isConvex=" + isConvex +
                '}';
    }
}
