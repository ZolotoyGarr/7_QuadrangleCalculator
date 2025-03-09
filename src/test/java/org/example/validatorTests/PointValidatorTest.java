package org.example.validatorTests;

import org.example.validator.impl.PointValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PointValidatorTest {

    private final PointValidator validator = new PointValidator();

    @Test
    void testValidateValidInput() {
        // given
        List<String> validPoint = List.of("1.0", "2.0");

        // when
        boolean isValid = validator.validate(validPoint);

        // then
        Assertions.assertTrue(isValid, "Expected the point to be valid.");
    }

    @Test
    void testValidateInvalidInputNonNumeric() {
        // given
        List<String> invalidPoint = List.of("1.0", "abc");

        // when
        boolean isValid = validator.validate(invalidPoint);

        // then
        Assertions.assertFalse(isValid, "Expected the point to be invalid due to non-numeric input.");
    }

    @Test
    void testValidateInvalidInputSizeLess() {
        // given
        List<String> invalidPoint = List.of("1.0");

        // when
        boolean isValid = validator.validate(invalidPoint);

        // then
        Assertions.assertFalse(isValid, "Expected the point to be invalid due to insufficient size.");
    }

    @Test
    void testValidateInvalidInputSizeMore() {
        // given
        List<String> invalidPoint = List.of("1.0", "2.0", "3.0");

        // when
        boolean isValid = validator.validate(invalidPoint);

        // then
        Assertions.assertFalse(isValid, "Expected the point to be invalid due to excessive size.");
    }

    @Test
    void testValidateInvalidInputEmpty() {
        // given
        List<String> emptyPoint = List.of();

        // when
        boolean isValid = validator.validate(emptyPoint);

        // then
        Assertions.assertFalse(isValid, "Expected the point to be invalid due to being empty.");
    }
}
