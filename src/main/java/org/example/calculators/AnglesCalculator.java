package org.example.calculators;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.Vector;

import java.util.ArrayList;
import java.util.List;

public class AnglesCalculator {
    private final VectorCalculator vectorCalculator;

    public AnglesCalculator(VectorCalculator vectorCalculator) {
        this.vectorCalculator = vectorCalculator;
    }

    // Метод для расчета углов в четырехугольнике
    public List<Double> calculateAngles(Quadrangle quadrangle) {
        List<Double> angles = new ArrayList<>();
        // Получаем точки четырехугольника
        Point p1 = quadrangle.getP1();
        Point p2 = quadrangle.getP2();
        Point p3 = quadrangle.getP3();
        Point p4 = quadrangle.getP4();
        // Рассчитываем углы
        angles.add(vectorCalculator.calculateAngle(new Vector(p1, p2), new Vector(p1, p4)));
        angles.add(vectorCalculator.calculateAngle(new Vector(p2, p1), new Vector(p2, p3)));
        angles.add(vectorCalculator.calculateAngle(new Vector(p3, p2), new Vector(p3, p4)));
        angles.add(vectorCalculator.calculateAngle(new Vector(p4, p1), new Vector(p4, p3)));
        return angles;
    }

    // Метод для проверки, являются ли все углы прямыми (90 градусов)
    public boolean isAnglesRight(List<Double> angles) {
        for (Double angle : angles) {
            // Учитываем небольшую погрешность
            if (Math.abs(angle - 90.0) > 1e-9) {
                return false;
            }
        }
        return true;
    }

    // Метод для проверки, является ли четырехугольник выпуклым
    public boolean isConvex(Quadrangle quadrangle) {
        List<Point> points = quadrangle.getPoints();
        int n = points.size();
        boolean hasPositive = false;
        boolean hasNegative = false;

        VectorCalculator vectorCalculator = new VectorCalculator();

        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n);
            Point p3 = points.get((i + 2) % n);

            Vector v1 = new Vector(p1, p2);
            Vector v2 = new Vector(p2, p3);

            double crossProduct = vectorCalculator.calculateVectorProduct(v1, v2);
            if (crossProduct > 0) {
                hasPositive = true;
            } else if (crossProduct < 0) {
                hasNegative = true;
            }

            // Если есть одновременно положительные и отрицательные направления, фигура невыпуклая
            if (hasPositive && hasNegative) {
                return false;
            }
        }
        return true;
    }

}
