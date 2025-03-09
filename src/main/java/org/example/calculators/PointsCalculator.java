package org.example.calculators;

import org.example.model.Point;
import org.example.model.Vector;

public class PointsCalculator implements Calculator {

    //Расстояние между двумя точками
    public double calculateDistanceBetweenPoints(Point firstPoint, Point secondPoint) {
        //тут надо вектор в конструктор переносить?
        Vector vector = new Vector(firstPoint, secondPoint);
        VectorCalculator vc = new VectorCalculator();
        return vc.magnitude(vector);
    }

    public boolean isCollinearPoints(Point firstPoint, Point secondPoint, Point thirdPoint) {
        // Вектор первого и второго отрезка
        double dx1 = secondPoint.getX() - firstPoint.getX();
        double dy1 = secondPoint.getY() - firstPoint.getY();
        double dx2 = thirdPoint.getX() - secondPoint.getX();
        double dy2 = thirdPoint.getY() - secondPoint.getY();
        // Проверяем условие коллинеарности:dx1 * dy2 == dx2 * dy1
        return dx1 * dy2 == dx2 * dy1;
    }

}
