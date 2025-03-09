package org.example.subscriberTests;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.model.QuadrangleType;
import org.example.subscriber.impl.AncientMathematician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AncientMathematicianTest {

    private Papyrus mockPapyrus;
    private QuadrangleCalculator mockCalculator;
    private AncientMathematician ancientMathematician;
    private UUID quadrangleId;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.US); // Устанавливаем локаль с точкой в качестве разделителя
        mockPapyrus = mock(Papyrus.class);
        mockCalculator = mock(QuadrangleCalculator.class);
        ancientMathematician = new AncientMathematician(mockCalculator, mockPapyrus);
        quadrangleId = UUID.randomUUID();
    }


    @Test
    void testUpdateAddsNewVersionToPapyrus() {
        Quadrangle quadrangle = new Quadrangle(quadrangleId, List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);
        when(mockPapyrus.read()).thenReturn("");

        ancientMathematician.update(quadrangle);

        verify(mockPapyrus, times(1)).write(
                String.format(Locale.US, "{Quadrangle id=%s v1; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId)
        );
    }


    @Test
    void testUpdateAppendsNewVersion() {
        // given
        Quadrangle quadrangle = new Quadrangle(quadrangleId, List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 3), new Point(0, 3)
        ));
        when(mockCalculator.calculateArea(quadrangle)).thenReturn(12.0);
        when(mockCalculator.calculatePerimeter(quadrangle)).thenReturn(14.0);
        when(mockCalculator.findQuadrangleType(quadrangle)).thenReturn(QuadrangleType.RECTANGLE);
        when(mockCalculator.isConvex(quadrangle)).thenReturn(true);
        when(mockPapyrus.read()).thenReturn(
                String.format("{Quadrangle id=%s v1; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId)
        );

        // when
        ancientMathematician.update(quadrangle);

        // then
        verify(mockPapyrus, times(1)).write(
                String.format("{Quadrangle id=%s v2; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId)
        );
    }

    @Test
    void testGetQuadrangleParametersReturnsCorrectData() {
        // given
        when(mockPapyrus.read()).thenReturn(
                String.format("{Quadrangle id=%s v1; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId)
        );

        // when
        Optional<QuadrangleParameters> optionalParams = ancientMathematician.getQuadrangleParameters(quadrangleId);

        // then
        assertTrue(optionalParams.isPresent());
        QuadrangleParameters params = optionalParams.get();
        assertEquals(QuadrangleType.RECTANGLE, params.getType());
        assertEquals(12.0, params.getArea());
        assertEquals(14.0, params.getPerimeter());
        assertTrue(params.isConvex());
    }

    @Test
    void testGetQuadrangleParametersReturnsEmptyIfNotFound() {
        // given
        when(mockPapyrus.read()).thenReturn("");

        // when
        Optional<QuadrangleParameters> optionalParams = ancientMathematician.getQuadrangleParameters(quadrangleId);

        // then
        assertFalse(optionalParams.isPresent());
    }

    @Test
    void testMultiplyQuadrangleParametersUpdatesPapyrus() {
        when(mockPapyrus.read()).thenReturn(
                String.format(Locale.US, "{Quadrangle id=%s v1; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId)
        );

        ancientMathematician.multiplyQuadrangleParameters(quadrangleId, 2);

        verify(mockPapyrus, times(1)).write(
                String.format(Locale.US, "{Quadrangle id=%s v2; type=RECTANGLE, area=24.00, perimeter=28.00, isConvex=true}\n", quadrangleId)
        );
    }


    @Test
    void testMultiplyQuadrangleParametersLogsWarningIfNotFound() {
        // given
        when(mockPapyrus.read()).thenReturn("");

        // when
        ancientMathematician.multiplyQuadrangleParameters(quadrangleId, 2);

        // then
        verify(mockPapyrus, never()).write(anyString());
    }

    @Test
    void testExtractCurrentVersionReturnsCorrectVersion() {
        // given
        when(mockPapyrus.read()).thenReturn(
                String.format("{Quadrangle id=%s v1; type=RECTANGLE, area=12.00, perimeter=14.00, isConvex=true}\n", quadrangleId) +
                        String.format("{Quadrangle id=%s v2; type=RECTANGLE, area=24.00, perimeter=28.00, isConvex=true}\n", quadrangleId)
        );

        // when
        int version = ancientMathematician.extractCurrentVersion(quadrangleId);

        // then
        assertEquals(2, version);
    }

    @Test
    void testExtractCurrentVersionReturnsZeroIfNotFound() {
        // given
        when(mockPapyrus.read()).thenReturn("");

        // when
        int version = ancientMathematician.extractCurrentVersion(quadrangleId);

        // then
        assertEquals(0, version);
    }
}
