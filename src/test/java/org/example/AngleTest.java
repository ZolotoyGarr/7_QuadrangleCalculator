package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AngleTest {
    @Test
    void testGetValue() {
        final Point start1 = new Point(0, 0);
        final Point end1 = new Point(1, 0);
        final Point start2 = new Point(0, 0);
        final Point end2 = new Point(0, 1);

        final Vector vector1 = new Vector(start1, end1);
        final Vector vector2 = new Vector(start2, end2);

        // Создание угла между двумя векторами
        final Angle angle = new Angle(vector1, vector2);
        // Ожидаемое значение угла в градусах между двумя векторами (90 градусов)
        final double expectedValue = 90.0;
        // Проверка значения угла
        Assertions.assertEquals(expectedValue, angle.getValue(), 1e-9);
    }
}
