package org.example.parserTests;

import org.example.exceptions.InputFormatException;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.parser.QuadrangleParser;
import org.example.validator.impl.QuadrangleValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QuadrangleParserTest {

    private final QuadrangleValidator validator = new QuadrangleValidator();
    private final QuadrangleParser parser = new QuadrangleParser(validator);

    @Test
    void testParseQuadrangleValidInput() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3),
                new Point(0, 3)
        );

        // when
        Quadrangle quadrangle = parser.parseQuadrangle(points);

        // then
        Assertions.assertNotNull(quadrangle, "Expected a valid quadrangle to be parsed.");
        Assertions.assertEquals(4, quadrangle.getPoints().size(), "Expected the quadrangle to have 4 points.");
        Assertions.assertEquals(points, quadrangle.getPoints(), "Expected the parsed points to match the input points.");
    }

    @Test
    void testParseQuadrangleDuplicatePoints() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 0), // Duplicate point
                new Point(0, 3)
        );

        // when & then
       Assertions.assertThrows(InputFormatException.class, () -> parser.parseQuadrangle(points),
                "Expected a RuntimeException due to duplicate points.");
    }

    @Test
    void testParseQuadrangleInsufficientPoints() {
        // given
        List<Point> points = List.of(
                new Point(0, 0),
                new Point(4, 0),
                new Point(4, 3)
        ); // Only 3 points provided

        // when & then
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> parser.parseQuadrangle(points),
                "Expected a RuntimeException due to insufficient points.");
    }

    @Test
    void testParseQuadrangleEmptyInput() {
        // given
        List<Point> points = List.of(); // Empty input

        // when & then
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> parser.parseQuadrangle(points),
                "Expected a RuntimeException due to empty input.");
    }
}
