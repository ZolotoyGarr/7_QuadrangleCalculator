package org.example.repository.specification;

import org.example.repository.specification.impl.Identifiable;

public interface Specification<T extends Identifiable> {
    boolean specified(T object);
}
