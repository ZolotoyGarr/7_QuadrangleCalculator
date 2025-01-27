package org.example.subscriber.impl;

import org.example.calculators.AnglesCalculator;
import org.example.calculators.PointsCalculator;
import org.example.calculators.QuadrangleCalculator;
import org.example.calculators.VectorCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.subscriber.Subscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AutoCADRecorder implements Subscriber<Quadrangle> {
    private static AutoCADRecorder INSTANCE;
    private final Map<UUID, QuadrangleParameters> subscriptions = new HashMap<>();
    private final QuadrangleCalculator calculator;

    public AutoCADRecorder(QuadrangleCalculator quadrangleCalculator) {
        this.calculator = quadrangleCalculator;
    }

    public static AutoCADRecorder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AutoCADRecorder(new QuadrangleCalculator(new PointsCalculator(), new AnglesCalculator(new VectorCalculator())));
        }
        return INSTANCE;
    }

    @Override
    public void update(Quadrangle quadrangle) {
        QuadrangleParameters params = new QuadrangleParameters(
                calculator,
                calculator.calculateArea(quadrangle),
                calculator.calculatePerimeter(quadrangle),
                calculator.findQuadrangleType(quadrangle),
                calculator.isConvex(quadrangle)
        );
        subscriptions.put(quadrangle.getId(), params);
    }

    public Optional<QuadrangleParameters> getQuadrangleParameters(UUID id) {
        return Optional.ofNullable(subscriptions.get(id));
    }

    public boolean isQuadranglePublisher(UUID id) {
        return subscriptions.containsKey(id);
    }

    public void updateQuadrangleNumericParameters(UUID id, double perimeter, double area) {
        if (id == null || perimeter == 0 || area == 0) {
            throw new IllegalArgumentException("Wrong argument received");
        }
        if (!subscriptions.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with the given ID");
        }
        QuadrangleParameters currentParams = subscriptions.get(id);
        QuadrangleParameters newParams = new QuadrangleParameters(calculator, area, perimeter, currentParams.getType(), currentParams.isConvex());
        subscriptions.put(id, newParams);
    }
}
