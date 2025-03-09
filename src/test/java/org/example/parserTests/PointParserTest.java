package org.example.parserTests;

import org.example.model.Point;
import org.example.parser.PointParser;
import org.example.validator.impl.PointValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointParserTest {

    private final PointValidator validator = new PointValidator();
    private final PointParser parser = new PointParser(validator);

    @Test
    void testParseValidPoint() {
        // given
        String validInput = "1.0,2.0";

        // when
        Point point = parser.parsePoint(validInput);

        // then
        Assertions.assertNotNull(point, "Expected a valid point object.");
        Assertions.assertEquals(1.0, point.getX(), "Expected X coordinate to be 1.0.");
        Assertions.assertEquals(2.0, point.getY(), "Expected Y coordinate to be 2.0.");
    }

    @Test
    void testParseInvalidPointNonNumeric() {
        // given
        String invalidInput = "1.0,abc";

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parsePoint(invalidInput),
                "Expected an IllegalArgumentException for non-numeric input.");
    }

    @Test
    void testParseInvalidPointInsufficientSize() {
        // given
        String invalidInput = "1.0";

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parsePoint(invalidInput),
                "Expected an IllegalArgumentException for insufficient input size.");
    }

    @Test
    void testParseInvalidPointExcessiveSize() {
        // given
        String invalidInput = "1.0,2.0,3.0";

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parsePoint(invalidInput),
                "Expected an IllegalArgumentException for excessive input size.");
    }

    @Test
    void testParseInvalidPointEmptyInput() {
        // given
        String emptyInput = "";

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parsePoint(emptyInput),
                "Expected an IllegalArgumentException for empty input.");
    }

    @Test
    void testParseInvalidPointOnlyDelimiters() {
        // given
        String invalidInput = ",";

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> parser.parsePoint(invalidInput),
                "Expected an IllegalArgumentException for input with only delimiters.");
    }

    @Test
    void testParsePointWithWhitespace() {
        // given
        String validInput = " 1.0 , 2.0 ";

        // when
        Point point = parser.parsePoint(validInput.trim());

        // then
        Assertions.assertNotNull(point, "Expected a valid point object.");
        Assertions.assertEquals(1.0, point.getX(), "Expected X coordinate to be 1.0.");
        Assertions.assertEquals(2.0, point.getY(), "Expected Y coordinate to be 2.0.");
    }
}
