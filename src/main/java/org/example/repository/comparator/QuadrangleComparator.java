package org.example.repository.comparator;

import org.example.model.Quadrangle;
import java.util.Comparator;

public interface QuadrangleComparator extends Comparator<Quadrangle> {
    @Override
    int compare(Quadrangle o1, Quadrangle o2);
}
