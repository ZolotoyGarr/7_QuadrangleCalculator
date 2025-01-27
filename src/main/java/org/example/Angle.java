package org.example;

public class Angle {
    private final Vector vector1;
    private final Vector vector2;
    private final double value;

    public Angle(Vector vector1, Vector vector2) {
        this.vector1 = vector1;
        this.vector2 = vector2;
        value = getValue(vector1, vector2);
    }
    //Думаю, что сделать приватный метод логично и правильно. То, что вынести расчеты эти из конструктора - это и так понятно
    //что норм. Тут я имею в виду, что value переменная по любому нужна, так как оставлять в экземпляре Vector только 2 вектора
    //это бредово, получится как недо угол какой-то неполноценный, в котором значение угла получается только через ВекторКальк.
    //(это к вопросу, что в классе логика какая-то вместо калькулятора делается, но здесь это необходимо, я думаю)

    private double getValue(Vector vector1, Vector vector2) {
        VectorCalculator vectorCalculator = new VectorCalculator();
        double dotProduct = vectorCalculator.dotProduct(vector1, vector2);
        double magnitude1 = vectorCalculator.magnitude(vector1);
        double magnitude2 = vectorCalculator.magnitude(vector2);
        double cosTheta = dotProduct / (magnitude1 * magnitude2);
        return Math.toDegrees(Math.acos(cosTheta));
    }

    @Override
    public String toString() {
        return "Angle{" +
                "vector1=" + vector1 +
                ", vector2=" + vector2 +
                '}';
    }

    public Vector getVector1() {
        return vector1;
    }

    public Vector getVector2() {
        return vector2;
    }

    public double getValue() {
        return value;
    }

}