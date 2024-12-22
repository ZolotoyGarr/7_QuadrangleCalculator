package org.example.Validator.impl;

import org.example.Exceptions.InputFormatException;
import org.example.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadrangleValidator {
    public boolean validate(List<Point> points) {
        if (points.size() != 4) {
            throw new InputFormatException("Wrong input format, 4 dots were expected");
        }
        Set<Point> uniquePoints = new HashSet<>(points);
        if (uniquePoints.size() != 4) {
            throw new InputFormatException("Wrong input format, identical points obtained");
        }
        return true;
    }
}
