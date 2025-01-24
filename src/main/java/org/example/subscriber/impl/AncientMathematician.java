package org.example.subscriber.impl;

import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.subscriber.Subscriber;


public class AncientMathematician implements Subscriber<Quadrangle> {
    private final QuadrangleCalculator calculator;
    private final QuadrangleParameters parameters;
    private Papyrus papyrus;

    public AncientMathematician(QuadrangleCalculator calculator, QuadrangleParameters parameters, Papyrus papyrus) {
        this.calculator = calculator;
        this.parameters = parameters;
        this.papyrus = papyrus;
    }

    @Override
    public void update(Quadrangle publisher) {
        QuadrangleParameters currentParams = new QuadrangleParameters(
                calculator,
                calculator.calculateArea(publisher),
                calculator.calculatePerimeter(publisher),
                calculator.findQuadrangleType(publisher),
                calculator.isConvex(publisher)
        );
        papyrus.write(currentParams.toString());
    }

//    Optional<QuadrangleParameters> getQuadrangleParameters(int id) {
//
//    }
//
//    void multiplyQuadrangleParameters(int id, int multiplier) {
//
//    }
}
