package org.example.model;

public class Vector {
    private final Point start;
    private final Point end;

    public Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }


    @Override
    public String toString() {
        return "Vector{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}