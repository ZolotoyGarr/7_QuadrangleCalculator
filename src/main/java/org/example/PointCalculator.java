package org.example;

public class PointCalculator implements Calculator {
    //Расстояние между двумя точками
    double calculateDistanceBetweenPoints(Point firstPoint, Point secondPoint) {
        Vector vector = new Vector(firstPoint, secondPoint);
        VectorCalculator vc = new VectorCalculator();
        return vc.magnitude(vector);
    }
}
