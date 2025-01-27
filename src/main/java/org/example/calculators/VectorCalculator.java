package org.example.calculators;

import org.example.model.Point;
import org.example.model.Vector;

public class VectorCalculator implements Calculator {
    public double getX(Vector vector) {
        return vector.getEnd().getX() - vector.getStart().getX();
    }

    public double getY(Vector vector) {
        return vector.getEnd().getY() - vector.getStart().getY();
    }

    // Метод для вычисления длины вектора
    public double magnitude(Vector vector) {
        double vectorX = getX(vector);
        double vectorY = getY(vector);
        return Math.sqrt(vectorX * vectorX + vectorY * vectorY);
    }

    // Метод для вычисления скалярного произведения двух векторов
    public double dotProduct(Vector vector1, Vector vector2) {
        return getX(vector1) * getX(vector2) + getY(vector1) * getY(vector2);
    }

    public double calculateAngle(Vector vector1, Vector vector2) {
        double dotProduct = dotProduct(vector1, vector2);
        double magnitude1 = magnitude(vector1);
        double magnitude2 = magnitude(vector2);
        double cosTheta = dotProduct / (magnitude1 * magnitude2);
        return Math.toDegrees(Math.acos(cosTheta));
    }
    public double calculateVectorProduct(Vector v1, Vector v2) {
        double dx1 = getX(v1);
        double dy1 = getY(v1);
        double dx2 = getX(v2);
        double dy2 = getY(v2);
        return dx1 * dy2 - dy1 * dx2;
    }
}