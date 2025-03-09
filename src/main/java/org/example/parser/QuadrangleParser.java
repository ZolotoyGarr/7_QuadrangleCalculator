package org.example.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.validator.impl.QuadrangleValidator;

import java.util.List;

public class QuadrangleParser {
    private static final Logger LOGGER = LogManager.getLogger(QuadrangleParser.class);

    private final QuadrangleValidator quadrangleValidator;

    public QuadrangleParser(QuadrangleValidator quadrangleValidator) {
        this.quadrangleValidator = quadrangleValidator;
    }

    public Quadrangle parseQuadrangle(List<Point> points) throws IllegalArgumentException {
        LOGGER.info("Starting to parse a quadrangle with points: {}", points);
        if (quadrangleValidator.validate(points)) {
            Quadrangle quadrangle = new Quadrangle(points);
            LOGGER.debug("Successfully parsed quadrangle: {}", quadrangle);
            return quadrangle;
        } else {
            throw new IllegalArgumentException("Invalid input: The input list does not form a valid quadrangle.");
        }
    }
}
