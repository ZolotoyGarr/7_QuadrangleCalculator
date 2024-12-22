package org.example.Parser.impl;

import org.example.Point;
import org.example.Quadrangle;
import org.example.Validator.impl.QuadrangleValidator;

import java.util.List;

public class QuadrangleParser {
    public Quadrangle parseQuadrangle(List<Point> points) {
        QuadrangleValidator quadrangleValidator = new QuadrangleValidator();
        if (quadrangleValidator.validate(points)) {
            return new Quadrangle(points);
        } else {
            throw new IllegalArgumentException("Invalid input: The input list does not form a valid quadrangle.");
        }
    }
}
