package org.example.serializationTest;

import org.example.calculators.QuadrangleCalculator;
import org.example.subscriber.impl.AutoCadRecorderPool;
import javiki.course.serialization.manager.ApplicationStateManager;
import org.example.subscriber.impl.AutoCADRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutoCadRecorderPoolTest {

    private ApplicationStateManager stateManagerMock;
    private QuadrangleCalculator calculatorMock;
    private AutoCadRecorderPool recorderPool;

    @BeforeEach
    void setUp() {
        stateManagerMock = mock(ApplicationStateManager.class);
        calculatorMock = mock(QuadrangleCalculator.class);
        recorderPool = new AutoCadRecorderPool(stateManagerMock, calculatorMock);
    }

    @Test
    void testCreateAutoCadRecorder() {
        AutoCADRecorder recorder = recorderPool.createAutoCadRecorder();

        assertNotNull(recorder, "Созданный AutoCADRecorder не должен быть null");
        verify(stateManagerMock, times(1)).register(recorder);
    }

    @Test
    void testStateManagerRegisterCalledOnce() {
        recorderPool.createAutoCadRecorder();
        verify(stateManagerMock, times(1)).register(any(AutoCADRecorder.class));
    }

    @Test
    void testCreateAutoCadRecorderMultipleTimes() {
        AutoCADRecorder recorder1 = recorderPool.createAutoCadRecorder();
        AutoCADRecorder recorder2 = recorderPool.createAutoCadRecorder();

        assertNotNull(recorder1, "Первый AutoCADRecorder не должен быть null");
        assertNotNull(recorder2, "Второй AutoCADRecorder не должен быть null");
        assertNotSame(recorder1, recorder2, "Каждый вызов createAutoCadRecorder должен возвращать новый экземпляр");

        verify(stateManagerMock, times(2)).register(any(AutoCADRecorder.class));
    }
}

