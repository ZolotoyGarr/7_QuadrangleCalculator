package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculators.AnglesCalculator;
import org.example.calculators.PointsCalculator;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.VectorCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.parser.QuadrangleParser;
import org.example.validator.impl.QuadrangleValidator;

import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // Example points for testing
        List<Point> validPoints = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        );

        List<Point> duplicatePoints = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 0),
                new Point(0, 3)
        );

        List<Point> insufficientPoints = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3)
        );

        // Initialize components
        QuadrangleValidator validator = new QuadrangleValidator();
        QuadrangleParser parser = new QuadrangleParser(validator);
        QuadrangleCalculator calculator = new QuadrangleCalculator(new PointsCalculator(), new AnglesCalculator(new VectorCalculator()));

        // Simulate valid input
        try {
            LOGGER.info("Simulating valid input...");
            Quadrangle validQuadrangle = parser.parseQuadrangle(validPoints);
            LOGGER.info("Parsed Quadrangle: {}", validQuadrangle);

            double perimeter = calculator.calculatePerimeter(validQuadrangle);
            double area = calculator.calculateArea(validQuadrangle);

            LOGGER.info("Calculated Perimeter: {}", perimeter);
            LOGGER.info("Calculated Area: {}", area);
        } catch (Exception e) {
            LOGGER.error("Error during valid input simulation", e);
        }

        // Simulate duplicate points
        try {
            LOGGER.info("Simulating duplicate points input...");
            parser.parseQuadrangle(duplicatePoints);
        } catch (Exception e) {
            LOGGER.error("Expected error for duplicate points", e);
        }

        // Simulate insufficient points
        try {
            LOGGER.info("Simulating insufficient points input...");
            parser.parseQuadrangle(insufficientPoints);
        } catch (Exception e) {
            LOGGER.error("Expected error for insufficient points", e);
        }
    }
}
