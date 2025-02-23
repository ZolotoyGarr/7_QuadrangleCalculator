package org.example.subscriber.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.QuadrangleType;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import javiki.course.serialization.Statable;
import javiki.course.serialization.StateType;
import org.example.subscriber.Subscriber;

import java.util.*;

public class AutoCADRecorder implements Subscriber<Quadrangle>, Statable<Map<String, List<QuadrangleParameters>>> {

    private static final Logger LOGGER = LogManager.getLogger(AutoCADRecorder.class);
    private final QuadrangleCalculator quadrangleCalculator;
    private final Map<String, List<QuadrangleParameters>> subs;

    private AutoCADRecorder(QuadrangleCalculator quadrangleCalculator,
                            Map<String, List<QuadrangleParameters>> subs) {
        this.quadrangleCalculator = quadrangleCalculator;
        this.subs = subs;
    }

    public static AutoCADRecorder createInstance(QuadrangleCalculator quadrangleCalculator) {
        return new AutoCADRecorder(quadrangleCalculator, new HashMap<>());
    }

    public static AutoCADRecorder createInstanceFromState(QuadrangleCalculator quadrangleCalculator,
                                                          Map<String, List<QuadrangleParameters>> subs) {
        return new AutoCADRecorder(quadrangleCalculator, subs);
    }

    public Optional<List<QuadrangleParameters>> getQuadrangleParameters(String id) {
        return Optional.ofNullable(subs.get(id));
    }

    public boolean isQuadranglePublisher(String id) {
        return subs.containsKey(id);
    }

    public void updateQuadrangleNumericParameters(String id, double perimeter, double area) {
        if (id == null || perimeter == 0 || area == 0) {
            throw new IllegalArgumentException("Wrong argument received");
        }
        if (!subs.containsKey(id)) {
            throw new IllegalArgumentException("No quadrangle found with this given ID");
        }
        List<QuadrangleParameters> currentParameters = subs.get(id);
        for (int i = 0; i < currentParameters.size(); i++) {
            QuadrangleParameters oldParameters = currentParameters.get(i);
            // Логирование текущих параметров
            LOGGER.info("Current parameters: id={}, area={}, perimeter={}",
                    id, oldParameters.getArea(), oldParameters.getPerimeter());
            // Создание обновленного объекта
            QuadrangleParameters newParameters = new QuadrangleParameters(
                    area, perimeter, oldParameters.getType(), oldParameters.isConvex()
            );
            // Логирование обновленных параметров
            LOGGER.info("Updating parameters: id={}, new area={}, new perimeter={}",
                    id, area, perimeter);
            // Заменяем старый объект новым
            currentParameters.set(i, newParameters);
        }
        // Обновляем лист в мапе (если он уже есть, этого делать не нужно)
        subs.put(id, currentParameters);
    }

    @Override
    public void update(Quadrangle quadrangle) {
        String id = quadrangle.getId();
        if (!subs.containsKey(id)) {
            LOGGER.info("New quadrangle detected, adding to subs: id={}", id);
            subs.put(id, new ArrayList<>()); // Создаем пустой список параметров
        }
        List<QuadrangleParameters> currentParameters = subs.get(id);
        double newArea = quadrangleCalculator.calculateArea(quadrangle);
        double newPerimeter = quadrangleCalculator.calculatePerimeter(quadrangle);
        QuadrangleType newType = quadrangleCalculator.findQuadrangleType(quadrangle);
        boolean newIsConvex = quadrangleCalculator.isConvex(quadrangle);
        LOGGER.info("Updating parameters: id={}, area={}, perimeter={}, type={}, isConvex={}",
                id, newArea, newPerimeter, newType, newIsConvex);

        QuadrangleParameters newParameters = new QuadrangleParameters(
                newArea, newPerimeter, newType, newIsConvex
        );
        if (!currentParameters.isEmpty()) {
            // Обновляем последний элемент, если он уже существует
            currentParameters.set(currentParameters.size() - 1, newParameters);
        } else {
            // Если данных еще нет, просто добавляем
            currentParameters.add(newParameters);
        }
        // Обновляем `subs` с новым списком
        subs.put(id, currentParameters);
    }

    public Map<String, List<QuadrangleParameters>> retrieveState() {
        Map<String, List<QuadrangleParameters>> copy = new HashMap<>();
        subs.forEach((key, value) -> copy.put(key, new ArrayList<>(value)));
        return copy;
    }

    @Override
    public void uploadState(Map<String, List<QuadrangleParameters>> state) {
        subs.clear();
        state.forEach((key, value) -> subs.put(key, new ArrayList<>(value)));
    }

    @Override
    public Class<Map<String, List<QuadrangleParameters>>> stateClass() {
        return (Class<Map<String, List<QuadrangleParameters>>>) (Class<?>) Map.class;
    }

    @Override
    public StateType stateName() {
        return StateType.fromFolderName("AUTOCAD_RECORDER");
    }
}
