package org.example.repository.specification.impl;

import org.example.model.Quadrangle;
import org.example.repository.specification.Specification;

import java.util.UUID;

public class IdSpecification implements Specification<Quadrangle> {
    private final UUID id;

    private IdSpecification(UUID id) {
        this.id = id;
    }

    public static Specification<Quadrangle> create(UUID id) {
        if (id == null) {
            throw new NullPointerException("ID cannot be null");
        }
        return new IdSpecification(id);
    }

    @Override
    public boolean specified(Quadrangle quadrangle) {
        return quadrangle.getId().equals(id);
    }
}
