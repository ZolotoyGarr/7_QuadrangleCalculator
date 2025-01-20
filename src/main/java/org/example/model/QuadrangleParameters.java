package org.example.model;

import org.example.calculators.AnglesCalculator;
import org.example.calculators.PointsCalculator;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.VectorCalculator;

public class QuadrangleParameters {
    private static final QuadrangleCalculator quadrangleCalculator = new QuadrangleCalculator(new PointsCalculator(), new AnglesCalculator(new VectorCalculator()));
    private final double area;
    private final double perimeter;
    private final QuadrangleType type;
    private final boolean isConvex;

    public QuadrangleParameters(Quadrangle quadrangle) {
        this.area = quadrangleCalculator.calculateArea(quadrangle);
        this.perimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        this.type = quadrangleCalculator.findQuadrangleType(quadrangle);
        this.isConvex = quadrangleCalculator.isConvex(quadrangle);
    }

    public QuadrangleParameters(double area, double perimeter, QuadrangleType type, boolean isConvex) {
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
