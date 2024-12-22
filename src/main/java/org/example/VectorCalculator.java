package org.example;

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
}
