package org.example.repository.comparator.impl;

import org.example.model.Quadrangle;
import org.example.repository.comparator.QuadrangleComparator;

public class FirstPointXComparator implements QuadrangleComparator {
    @Override
    public int compare(Quadrangle o1, Quadrangle o2) {
        return Double.compare(o1.getP1().getX(), o2.getP1().getX());
    }
}
