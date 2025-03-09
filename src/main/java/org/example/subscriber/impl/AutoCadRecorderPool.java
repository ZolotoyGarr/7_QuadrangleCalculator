package org.example.subscriber.impl;


import org.example.calculators.QuadrangleCalculator;
import org.example.model.QuadrangleParameters;
import javiki.course.serialization.manager.ApplicationStateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutoCadRecorderPool {
    private final ApplicationStateManager applicationStateManager;
    private final QuadrangleCalculator calculator;

    private final List<AutoCADRecorder> autoCadRecorders = new ArrayList<>();

    public AutoCadRecorderPool(ApplicationStateManager stateManager, QuadrangleCalculator calculator) {
        this.applicationStateManager = stateManager;
        this.calculator = calculator;
    }

    public AutoCADRecorder createAutoCadRecorder() {
        AutoCADRecorder newRecorder = AutoCADRecorder.createInstance(calculator);
        applicationStateManager.register(newRecorder);
        autoCadRecorders.add(newRecorder);
        return newRecorder;
    }

    public AutoCADRecorder createAutoCadRecorder(Map<String, List<QuadrangleParameters>> subs) {
        AutoCADRecorder newRecorder = AutoCADRecorder.createInstanceFromState(calculator, subs);
        applicationStateManager.register(newRecorder);
        autoCadRecorders.add(newRecorder);
        return newRecorder;
    }

    public List<AutoCADRecorder> getAutoCadRecorders() {
        return autoCadRecorders;
    }
}