package org.example.subscriberTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.model.QuadrangleType;
import org.example.subscriber.impl.AutoCADRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutoCADRecorderTest {

    private AutoCADRecorder recorder;
    private QuadrangleCalculator mockCalculator;
    private UUID quadrangleId;
    private Quadrangle quadrangle;

    @BeforeEach
    void setUp() {
        mockCalculator = mock(QuadrangleCalculator.class);
        recorder = new AutoCADRecorder(mockCalculator);
        quadrangleId = UUID.randomUUID();
        quadrangle = new Quadrangle(quadrangleId, List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
    }

    @Test
    void testUpdateAddsQuadrangleParameters() {
        // given
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);

        // when
        recorder.update(quadrangle);

        // then
        Optional<QuadrangleParameters> params = recorder.getQuadrangleParameters(quadrangleId);
        assertTrue(params.isPresent());
        assertEquals(12.0, params.get().getArea());
        assertEquals(14.0, params.get().getPerimeter());
        assertEquals(QuadrangleType.RECTANGLE, params.get().getType());
        assertTrue(params.get().isConvex());
    }

    @Test
    void testGetQuadrangleParametersReturnsEmptyIfNotFound() {
        // when
        Optional<QuadrangleParameters> params = recorder.getQuadrangleParameters(UUID.randomUUID());

        // then
        assertFalse(params.isPresent());
    }

    @Test
    void testIsQuadranglePublisherReturnsTrueForExistingQuadrangle() {
        // given
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);
        recorder.update(quadrangle);

        // when
        boolean result = recorder.isQuadranglePublisher(quadrangleId);

        // then
        assertTrue(result);
    }

    @Test
    void testIsQuadranglePublisherReturnsFalseForNonExistingQuadrangle() {
        // when
        boolean result = recorder.isQuadranglePublisher(UUID.randomUUID());

        // then
        assertFalse(result);
    }

    @Test
    void testUpdateQuadrangleNumericParametersUpdatesValues() {
        // given
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);
        recorder.update(quadrangle);

        // when
        recorder.updateQuadrangleNumericParameters(quadrangleId, 28.0, 24.0);

        // then
        Optional<QuadrangleParameters> params = recorder.getQuadrangleParameters(quadrangleId);
        assertTrue(params.isPresent());
        assertEquals(28.0, params.get().getPerimeter());
        assertEquals(24.0, params.get().getArea());
    }

    @Test
    void testUpdateQuadrangleNumericParametersThrowsExceptionForInvalidId() {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                recorder.updateQuadrangleNumericParameters(UUID.randomUUID(), 28.0, 24.0)
        );

        // then
        assertEquals("No quadrangle found with the given ID", exception.getMessage());
    }

    @Test
    void testUpdateQuadrangleNumericParametersThrowsExceptionForInvalidArguments() {
        // when & then
        assertThrows(IllegalArgumentException.class, () ->
                recorder.updateQuadrangleNumericParameters(null, 28.0, 24.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                recorder.updateQuadrangleNumericParameters(quadrangleId, 0, 24.0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                recorder.updateQuadrangleNumericParameters(quadrangleId, 28.0, 0)
        );
    }
}