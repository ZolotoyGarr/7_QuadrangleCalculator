package org.example.parser.impl;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.validator.impl.QuadrangleValidator;

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
