package org.example.publisherTests;

import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.publisher.impl.QuadranglePublisher;
import org.example.subscriber.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class QuadranglePublisherTest {

    private QuadranglePublisher publisher;
    private Subscriber<Quadrangle> mockSubscriber; // Изменён тип на Subscriber<Quadrangle>

    @BeforeEach
    void setUp() {
        publisher = new QuadranglePublisher(
                UUID.randomUUID(),
                List.of(
                        new Point(0, 0),
                        new Point(4, 0),
                        new Point(4, 3),
                        new Point(0, 3)
                )
        );
        mockSubscriber = mock(Subscriber.class); // Создание mock объекта
    }

    @Test
    void testAddSubscriber() {
        // when
        publisher.addSubscriber(mockSubscriber);

        // then
        publisher.notifySubscribers();
        verify(mockSubscriber, times(1)).update(publisher);
    }

    @Test
    void testRemoveSubscriber() {
        // given
        publisher.addSubscriber(mockSubscriber);

        // when
        publisher.removeSubscriber(mockSubscriber);

        // then
        publisher.notifySubscribers();
        verify(mockSubscriber, never()).update(publisher);
    }

    @Test
    void testNotifySubscribers() {
        // given
        Subscriber<Quadrangle> anotherMockSubscriber = mock(Subscriber.class); // Ещё один mock
        publisher.addSubscriber(mockSubscriber);
        publisher.addSubscriber(anotherMockSubscriber);

        // when
        publisher.notifySubscribers();

        // then
        verify(mockSubscriber, times(1)).update(publisher);
        verify(anotherMockSubscriber, times(1)).update(publisher);
    }

    @Test
    void testUpdatePointsAndNotifySubscribers() {
        // given
        publisher.addSubscriber(mockSubscriber);
        List<Point> newPoints = List.of(
                new Point(1, 1),
                new Point(5, 1),
                new Point(5, 4),
                new Point(1, 4)
        );

        // when
        publisher.updatePoints(newPoints);

        // then
        verify(mockSubscriber, times(1)).update(publisher);
    }

    @Test
    void testNoSubscribersNotification() {
        // when
        publisher.notifySubscribers();

        // then
        // Никаких исключений или вызовов update быть не должно
    }
}
