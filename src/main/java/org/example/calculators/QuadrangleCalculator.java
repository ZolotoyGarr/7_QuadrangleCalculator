package org.example.calculators;

import org.example.exceptions.InputFormatException;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleType;
import org.example.model.Vector;

import java.util.List;

public class QuadrangleCalculator {
    private final PointsCalculator pointsCalculator;
    private final AnglesCalculator anglesCalculator;

    public QuadrangleCalculator(PointsCalculator pointsCalculator, AnglesCalculator anglesCalculator) {
        this.pointsCalculator = pointsCalculator;
        this.anglesCalculator = anglesCalculator;
    }

    public double calculatePerimeter(Quadrangle quadrangle) {
        double perimeter = 0;
        List<Point> points = quadrangle.getPoints();
        for (int i = 0; i < points.size(); i++) {
            Point current = points.get(i);
            Point next = points.get((i + 1) % points.size());
            perimeter += pointsCalculator.calculateDistanceBetweenPoints(current, next);
        }
        return perimeter;
    }


    public double calculateArea(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();
        int n = points.size();
        double area = 0.0;
        for (int i = 0; i < n; i++) {
            Point current = points.get(i);
            Point next = points.get((i + 1) % n);
            area += current.getX() * next.getY() - next.getX() * current.getY();
        }
        return Math.abs(area) / 2.0;
    }

    public double calculateDistanceFromOrigin(Quadrangle quadrangle) {
        double maxDistance = 0;
        for (Point point : quadrangle.getPoints()) {
            double distance = Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        return maxDistance;
    }

    public boolean isQuadrangle(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            Point p3 = points.get((i + 2) % points.size());
            if (pointsCalculator.isCollinearPoints(p1, p2, p3)) {
                return false;
            }
        }
        return true;
    }

    public QuadrangleType findQuadrangleType(Quadrangle quadrangle) {
        if (!isQuadrangle(quadrangle)) {
            throw new InputFormatException("Input is not a quadrangle");
        }
        List<Double> angles = anglesCalculator.calculateAngles(quadrangle);
        boolean allRightAngles = anglesCalculator.isAnglesRight(angles);
        boolean allSidesEqual = areAllSidesEqual(quadrangle);
        boolean oppositeSidesEqual = areOppositeSidesEqual(quadrangle);
        if (allRightAngles && allSidesEqual) {
            return QuadrangleType.SQUARE;
        }
        if (allSidesEqual) {
            return QuadrangleType.RHOMBUS;
        }
        if (allRightAngles && oppositeSidesEqual) {
            return QuadrangleType.RECTANGLE;
        }
        if (isParallelogram(quadrangle)) {
            return QuadrangleType.PARALLELOGRAM;
        }
        if (isTrapezoid(quadrangle)) {
            return QuadrangleType.TRAPEZOID;
        }
        return QuadrangleType.NO_TYPE;
    }

    public boolean isConvex(Quadrangle quadrangle) {
        return anglesCalculator.isConvex(quadrangle);
    }

    private boolean areAllSidesEqual(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();
        double sideLength = pointsCalculator.calculateDistanceBetweenPoints(points.get(0), points.get(1));
        for (int i = 1; i < points.size(); i++) {
            double currentLength = pointsCalculator.calculateDistanceBetweenPoints(points.get(i), points.get((i + 1) % points.size()));
            if (Math.abs(currentLength - sideLength) > 1e-9) {
                return false;
            }
        }
        return true;
    }

    private boolean areOppositeSidesEqual(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();
        double side1 = pointsCalculator.calculateDistanceBetweenPoints(points.get(0), points.get(1));
        double side2 = pointsCalculator.calculateDistanceBetweenPoints(points.get(1), points.get(2));
        double side3 = pointsCalculator.calculateDistanceBetweenPoints(points.get(2), points.get(3));
        double side4 = pointsCalculator.calculateDistanceBetweenPoints(points.get(3), points.get(0));
        return Math.abs(side1 - side3) < 1e-9 && Math.abs(side2 - side4) < 1e-9;
    }

    private boolean isParallelogram(Quadrangle quadrangle) {
        return areOppositeSidesEqual(quadrangle) && !anglesCalculator.isAnglesRight(anglesCalculator.calculateAngles(quadrangle));
    }

    private boolean isTrapezoid(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();

        // Создаем векторы для сторон
        Vector v1 = new Vector(points.get(0), points.get(1));
        Vector v2 = new Vector(points.get(2), points.get(3));
        Vector v3 = new Vector(points.get(1), points.get(2));
        Vector v4 = new Vector(points.get(3), points.get(0));

        // Проверяем параллельность противоположных сторон
        boolean firstPairParallel = isParallel(v1, v2);
        boolean secondPairParallel = isParallel(v3, v4);

        // Трапеция имеет хотя бы одну пару параллельных сторон
        return firstPairParallel || secondPairParallel;
    }

    private boolean isParallel(Vector v1, Vector v2) {
        // Вычисляем координаты векторов
        double dx1 = v1.getEnd().getX() - v1.getStart().getX();
        double dy1 = v1.getEnd().getY() - v1.getStart().getY();

        double dx2 = v2.getEnd().getX() - v2.getStart().getX();
        double dy2 = v2.getEnd().getY() - v2.getStart().getY();

        // Проверяем параллельность через векторное произведение
        return Math.abs(dx1 * dy2 - dy1 * dx2) < 1e-6;
    }


}
