package org.example.repositoryTests.comparatorTests;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.comparator.impl.IdComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IdComparatorTest {

    private final IdComparator comparator = new IdComparator();

    @Test
    void testCompareIds() {
        // given
        Quadrangle quadrangle1 = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        Quadrangle quadrangle2 = new Quadrangle(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));
        Quadrangle quadrangle3 = quadrangle1; // Same object to ensure UUIDs are identical

        // when
        int result1 = comparator.compare(quadrangle1, quadrangle2); // Compare quadrangle1's UUID to quadrangle2's UUID
        int result2 = comparator.compare(quadrangle2, quadrangle1); // Reverse order
        int result3 = comparator.compare(quadrangle1, quadrangle3); // Same UUIDs

        // then
        Assertions.assertTrue(result1 < 0 || result1 > 0, "Expected the comparison to indicate different UUIDs.");
        Assertions.assertTrue(result2 > 0 || result2 < 0, "Expected the reverse comparison to work correctly.");
        Assertions.assertEquals(0, result3, "Expected the same UUIDs to be equal.");
    }
}
