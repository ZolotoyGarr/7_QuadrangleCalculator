package org.example.subscriber;

public interface Subscriber<T> {
    void update(T publisher);
}
