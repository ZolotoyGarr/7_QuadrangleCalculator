package org.example.repositoryTests;

import org.example.exceptions.RepositoryDataException;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.repository.impl.QuadrangleRepositoryInMemory;
import org.example.repository.specification.Specification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class RepositoryInMemoryTest {

    private QuadrangleRepositoryInMemory repository;

    @BeforeEach
    void setUp() {
        repository = new QuadrangleRepositoryInMemory();
    }

    @Test
    void testAddQuadrangle() throws RepositoryDataException {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));

        // when
        repository.add(quadrangle);

        // then
        Specification<Quadrangle> allQuadrangles = q -> true; // Лямбда для всех объектов
        List<Quadrangle> quadrangleList = repository.query(allQuadrangles);
        Assertions.assertTrue(quadrangleList.contains(quadrangle));
    }

    @Test
    void testAddDuplicateQuadrangle() throws RepositoryDataException {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        repository.add(quadrangle);

        // when & then
        Assertions.assertThrows(RepositoryDataException.class, () -> repository.add(quadrangle));
    }

    @Test
    void testRemoveQuadrangle() throws RepositoryDataException {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        repository.add(quadrangle);

        // when
        repository.remove(quadrangle);

        // then
        Specification<Quadrangle> allQuadrangles = q -> true; // Лямбда для всех объектов
        Assertions.assertFalse(repository.query(allQuadrangles).contains(quadrangle));
    }

    @Test
    void testRemoveNonExistentQuadrangle() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));

        // when & then
        Assertions.assertThrows(RepositoryDataException.class, () -> repository.remove(quadrangle));
    }

    @Test
    void testUpdateQuadrangle() throws RepositoryDataException {
        // given
        Quadrangle original = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        repository.add(original);

        Quadrangle updated = new Quadrangle(original.getId(), List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));

        // when
        repository.update(updated);

        // then
        Specification<Quadrangle> byId = q -> q.getId().equals(original.getId()); // Лямбда для фильтрации по ID
        List<Quadrangle> quadrangleList = repository.query(byId);
        Assertions.assertTrue(quadrangleList.contains(updated));
    }

    @Test
    void testUpdateNonExistentQuadrangle() {
        // given
        Quadrangle quadrangle = new Quadrangle(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));

        // when & then
        Assertions.assertThrows(RepositoryDataException.class, () -> repository.update(quadrangle));
    }

    @Test
    void testQuerySpecification() throws RepositoryDataException {
        // given
        Quadrangle quadrangle1 = new Quadrangle(List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        Quadrangle quadrangle2 = new Quadrangle(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));
        repository.add(quadrangle1);
        repository.add(quadrangle2);

        Specification<Quadrangle> byXCoordinate = q -> q.getPoints().get(1).getX() == 2; // Лямбда для фильтрации по X

        // when
        List<Quadrangle> result = repository.query(byXCoordinate);

        // then
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains(quadrangle2));
    }

    @Test
    void testQueryWithComparator() throws RepositoryDataException {
        // given
        UUID uuid1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID uuid2 = UUID.fromString("00000000-0000-0000-0000-000000000002");

        Quadrangle quadrangle1 = new Quadrangle(uuid1, List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)
        ));
        Quadrangle quadrangle2 = new Quadrangle(uuid2, List.of(
                new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
        ));
        repository.add(quadrangle1);
        repository.add(quadrangle2);

        Specification<Quadrangle> allQuadrangles = q -> true; // Лямбда для всех объектов
        Comparator<Quadrangle> sortById = Comparator.comparing(q -> q.getId().toString()); // Компаратор по UUID

        // when
        List<Quadrangle> result = repository.query(allQuadrangles, sortById);

        // then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(quadrangle1, result.get(0));
        Assertions.assertEquals(quadrangle2, result.get(1));
    }
}
