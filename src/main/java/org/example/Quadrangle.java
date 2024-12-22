package org.example;

import java.util.List;

public class Quadrangle {
    private final List<Point> points;
    private final List<Vector> vectors;
    private final List<Angle> angles;

    public Quadrangle(List<Point> points) {
        if (points.size() != 4) {
            throw new IllegalArgumentException("A quadrangle must have exactly 4 points.");
        }
        this.points = points;
        this.vectors = createVectors(points);
        this.angles = createAngles(vectors);
    }

    private List<Vector> createVectors(List<Point> points) {
        return List.of(
                new Vector(points.get(0), points.get(1)),
                new Vector(points.get(1), points.get(2)),
                new Vector(points.get(2), points.get(3)),
                new Vector(points.get(3), points.get(0)));
    }

    private List<Angle> createAngles(List<Vector> vectors) {
        return List.of(
                new Angle(vectors.get(0), vectors.get(1)),
                new Angle(vectors.get(1), vectors.get(2)),
                new Angle(vectors.get(2), vectors.get(3)),
                new Angle(vectors.get(3), vectors.get(0)));
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public List<Angle> getAngles() {
        return angles;
    }

    @Override
    public String toString() {
        return "org.example.Quadrangle{" + "points=" + points + ", vectors=" + vectors + ", angles=" + angles + '}';
    }
}
