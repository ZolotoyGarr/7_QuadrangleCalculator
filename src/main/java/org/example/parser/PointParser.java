package org.example.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Point;
import org.example.validator.impl.PointValidator;

import java.util.Arrays;
import java.util.List;

public class PointParser {
    private static final Logger LOGGER = LogManager.getLogger(PointParser.class);

    private final PointValidator pointValidator;


    public PointParser(PointValidator pointValidator) {
        this.pointValidator = pointValidator;
    }

    public Point parsePoint(String lineWithPoints) throws IllegalArgumentException {
        List<String> stringPoint = Arrays.asList(lineWithPoints.split(","));
        try {
            if (pointValidator.validate(stringPoint)) {
                double x = Double.parseDouble(stringPoint.get(0));
                double y = Double.parseDouble(stringPoint.get(1));
                Point point = new Point(x, y);
                LOGGER.debug("Successfully parsed point: {}", point);
                return point;
            } else {
                throw new IllegalArgumentException("Invalid input: The input string does not contain exactly 2 valid double numbers.");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Number format error while parsing the line: {}", lineWithPoints, e);
            throw new IllegalArgumentException("Invalid input: Unable to parse numbers.", e);
        }
    }
}
