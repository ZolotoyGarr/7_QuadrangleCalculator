package org.example.repository.impl;

import org.example.exceptions.RepositoryDataException;
import org.example.model.Quadrangle;
import org.example.repository.QuadrangleRepository;
import org.example.repository.specification.Specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class QuadrangleRepositoryInMemory implements QuadrangleRepository {
    private final List<Quadrangle> storage = new ArrayList<>();

    @Override
    public void add(Quadrangle quadrangle) throws RepositoryDataException {
        if (storage.contains(quadrangle)) {
            throw new RepositoryDataException("Quadrangle already exists in the repository.");
        }
        storage.add(quadrangle);
    }

    @Override
    public void remove(Quadrangle quadrangle) throws RepositoryDataException {
        if (!storage.remove(quadrangle)) {
            throw new RepositoryDataException("Quadrangle not found in the repository.");
        }
    }

    @Override
    public void update(Quadrangle quadrangle) throws RepositoryDataException {
        int index = findIndexById(quadrangle.getId());
        if (index == -1) {
            throw new RepositoryDataException("Quadrangle with ID " + quadrangle.getId() + " not found.");
        }
        storage.set(index, quadrangle);
    }

    @Override
    public List<Quadrangle> query(Specification<Quadrangle> specification) {
        List<Quadrangle> result = new ArrayList<>();
        for (Quadrangle quadrangle : storage) {
            if (specification.specified(quadrangle)) {
                result.add(quadrangle);
            }
        }
        return result;
    }

    @Override
    public List<Quadrangle> query(Specification<Quadrangle> specification, Comparator<Quadrangle> comparator) {
        List<Quadrangle> result = this.query(specification);
        result.sort(comparator);
        return result;
    }

    private int findIndexById(UUID id) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
