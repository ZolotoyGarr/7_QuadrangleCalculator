package org.example.specificationTests;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;
import org.example.repository.specification.impl.IdSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.UUID;

public class IdSpecificationTest {

    @Test
    void testSpecifiedMatchingId() {
        // given
        UUID targetId = UUID.randomUUID();
        Quadrangle quadrangle = new Quadrangle(targetId, List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        Specification<Quadrangle> specification = IdSpecification.create(targetId);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertTrue(isSpecified, "Expected the specification to match the target ID.");
    }

    @Test
    void testSpecifiedNonMatchingId() {
        // given
        UUID targetId = UUID.randomUUID();
        UUID differentId = UUID.randomUUID();
        Quadrangle quadrangle = new Quadrangle(differentId, List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        Specification<Quadrangle> specification = IdSpecification.create(targetId);

        // when
        boolean isSpecified = specification.specified(quadrangle);

        // then
        Assertions.assertFalse(isSpecified, "Expected the specification not to match a different ID.");
    }

    @Test
    void testSpecifiedNullId() {
        // given
        Executable createWithNullId = () -> IdSpecification.create(null);

        // when & then
        Assertions.assertThrows(NullPointerException.class, createWithNullId,
                "Expected a NullPointerException when creating a specification with a null ID.");
    }

    @Test
    void testSpecifiedQuadrangleWithNullId() {
        // given
        Quadrangle quadrangle = new Quadrangle(null, List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        Specification<Quadrangle> specification = IdSpecification.create(UUID.randomUUID());

        // when & then
        Assertions.assertThrows(NullPointerException.class, () -> specification.specified(quadrangle),
                "Expected a NullPointerException when the Quadrangle's ID is null.");
    }
}
