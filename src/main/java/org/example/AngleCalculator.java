package org.example;

public class AngleCalculator implements Calculator {
    // Метод для вычисления значения угла в градусах между двумя векторами
    public double getValue(Vector vector1, Vector vector2) {
        VectorCalculator vectorCalculator = new VectorCalculator();
        double dotProduct = vectorCalculator.dotProduct(vector1, vector2);
        double magnitude1 = vectorCalculator.magnitude(vector1);
        double magnitude2 = vectorCalculator.magnitude(vector2);
        double cosTheta = dotProduct / (magnitude1 * magnitude2);
        return Math.toDegrees(Math.acos(cosTheta));
    }
}
