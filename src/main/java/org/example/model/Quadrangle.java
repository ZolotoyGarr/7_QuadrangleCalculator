package org.example.model;

import org.example.repository.specification.impl.Identifiable;

import java.util.List;
import java.util.UUID;

public class Quadrangle implements Identifiable {
    private final UUID id;
    private final List<Point> points;
    private final List<Vector> vectors;

    public Quadrangle(List<Point> points) {
        this.id = IdGenerator.generateId();
        this.points = points;
        this.vectors = createVectors(points);
    }

    public Quadrangle(UUID id, List<Point> points) {
        this.id = id;
        this.points = points;
        this.vectors = createVectors(points);
    }

    private List<Vector> createVectors(List<Point> points) {
        return List.of(
                new Vector(points.get(0), points.get(1)),
                new Vector(points.get(1), points.get(2)),
                new Vector(points.get(2), points.get(3)),
                new Vector(points.get(3), points.get(0)));
    }

    public Point getP1() {
        return points.get(0);
    }

    public Point getP2() {
        return points.get(1);
    }

    public Point getP3() {
        return points.get(2);
    }

    public Point getP4() {
        return points.get(3);
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Quadrangle{" +
                "id=" + id +
                ", points=" + points +
                ", vectors=" + vectors +
                '}';
    }
}
