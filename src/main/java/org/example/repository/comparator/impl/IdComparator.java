package org.example.repository.comparator.impl;

import org.example.model.Quadrangle;
import org.example.repository.comparator.QuadrangleComparator;

public class IdComparator implements QuadrangleComparator {
    @Override
    public int compare(Quadrangle o1, Quadrangle o2) {
        return o1.getId().toString().compareTo(o2.getId().toString());
    }
}
