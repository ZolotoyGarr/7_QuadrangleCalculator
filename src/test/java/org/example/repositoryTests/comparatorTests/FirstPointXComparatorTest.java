package org.example.repositoryTests.comparatorTests;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.comparator.impl.FirstPointXComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FirstPointXComparatorTest {

    private final FirstPointXComparator comparator = new FirstPointXComparator();

    @Test
    void testCompareFirstPointX() {
        // given
        Quadrangle quadrangle1 = new Quadrangle(List.of(
                new Point(1, 0), new Point(2, 2), new Point(3, 3), new Point(4, 4)
        ));
        Quadrangle quadrangle2 = new Quadrangle(List.of(
                new Point(3, 0), new Point(2, 2), new Point(1, 3), new Point(0, 4)
        ));
        Quadrangle quadrangle3 = new Quadrangle(List.of(
                new Point(1, 0), new Point(2, 2), new Point(3, 3), new Point(4, 4)
        ));

        // when
        int result1 = comparator.compare(quadrangle1, quadrangle2);
        int result2 = comparator.compare(quadrangle2, quadrangle1);
        int result3 = comparator.compare(quadrangle1, quadrangle3);

        // then
        Assertions.assertTrue(result1 < 0); // quadrangle1's P1.x < quadrangle2's P1.x
        Assertions.assertTrue(result2 > 0); // quadrangle2's P1.x > quadrangle1's P1.x
        Assertions.assertEquals(0, result3); // quadrangle1's P1.x == quadrangle3's P1.x
    }
}
