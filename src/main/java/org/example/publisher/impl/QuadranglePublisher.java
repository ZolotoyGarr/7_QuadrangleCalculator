package org.example.publisher.impl;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.publisher.Publisher;
import org.example.subscriber.Subscriber;

import java.util.*;

public class QuadranglePublisher extends Quadrangle implements Publisher<Quadrangle> {
    private final Set<Subscriber<Quadrangle>> subscribers = new HashSet<>();

    public QuadranglePublisher(UUID id, List<Point> points) {
        super(id, points);
    }

    @Override
    public void addSubscriber(Subscriber<Quadrangle> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber<Quadrangle> subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        Iterator<Subscriber<Quadrangle>> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
            Subscriber<Quadrangle> subscriber = iterator.next();
            subscriber.update(this);
        }
    }

    public void updatePoints(List<Point> newPoints) {
        setPoints(newPoints);
        notifySubscribers();
    }
}
