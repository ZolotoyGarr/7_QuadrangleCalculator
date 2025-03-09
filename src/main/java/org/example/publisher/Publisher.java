package org.example.publisher;

import org.example.subscriber.Subscriber;

public interface Publisher<T> {
    void addSubscriber(Subscriber<T> Subscriber);
    void removeSubscriber(Subscriber<T> Subscriber);
    void notifySubscribers();
}
