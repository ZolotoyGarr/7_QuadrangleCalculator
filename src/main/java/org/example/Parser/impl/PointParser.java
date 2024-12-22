package org.example.Parser.impl;

import org.example.Point;
import org.example.Validator.impl.PointValidator;

import java.util.Arrays;
import java.util.List;

public class PointParser {
    public Point parsePoint(String lineWithPoints) {
        List<String> stringPoint = Arrays.asList(lineWithPoints.split(","));
        PointValidator pointValidator = new PointValidator();
        try {
            // Проверка наличия запятой и правильности формата
            if (stringPoint.size() != 2) {
                throw new IllegalArgumentException("Invalid input: The input string must contain exactly one comma separating two numbers.");
            }
            if (pointValidator.validate(stringPoint)) {
                double x = Double.parseDouble(stringPoint.get(0));
                double y = Double.parseDouble(stringPoint.get(1));
                return new Point(x, y);
            } else {
                throw new IllegalArgumentException("Invalid input: The input string does not contain exactly 2 valid double numbers.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null;
    }
}