package org.example.validatorTests;

import org.example.exceptions.InputFormatException;
import org.example.model.Point;
import org.example.validator.impl.QuadrangleValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuadrangleValidatorTest {

    private final QuadrangleValidator validator = new QuadrangleValidator();

    @Test
    void testValidateValidInput() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        );

        // when
        boolean isValid = validator.validate(points);

        // then
        Assertions.assertTrue(isValid, "Expected the input points to form a valid quadrangle.");
    }

    @Test
    void testValidateWrongNumberOfPoints() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3)
        ); // Only 3 points provided

        // when & then
        Assertions.assertThrows(InputFormatException.class, () -> validator.validate(points),
                "Expected an exception due to incorrect number of points.");
    }

    @Test
    void testValidateDuplicatePoints() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 0) // Duplicate point
        );

        // when & then
        Assertions.assertThrows(InputFormatException.class, () -> validator.validate(points),
                "Expected an exception due to duplicate points.");
    }

    @Test
    void testValidateEmptyInput() {
        // given
        List<Point> points = List.of(); // No points provided

        // when & then
        Assertions.assertThrows(InputFormatException.class, () -> validator.validate(points),
                "Expected an exception due to empty input.");
    }
}
