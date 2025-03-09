package org.example.subscriber.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Papyrus;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.model.QuadrangleType;
import org.example.subscriber.Subscriber;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AncientMathematician implements Subscriber<Quadrangle> {
    private static final Logger LOGGER = LogManager.getLogger(AncientMathematician.class);
    private final QuadrangleCalculator calculator;
    private final Papyrus papyrus;

    public AncientMathematician(QuadrangleCalculator calculator, Papyrus papyrus) {
        this.calculator = calculator;
        this.papyrus = papyrus;
    }

    @Override
    public void update(Quadrangle quadrangle) {
        Locale.setDefault(Locale.US);
        UUID quadrangleId = quadrangle.getId();

        int latestVersion = extractCurrentVersion(quadrangleId);
        int newVersion = latestVersion + 1;

        QuadrangleParameters currentParams = new QuadrangleParameters(
                calculator,
                calculator.calculateArea(quadrangle),
                calculator.calculatePerimeter(quadrangle),
                calculator.findQuadrangleType(quadrangle),
                calculator.isConvex(quadrangle)
        );

        String newEntry = String.format(Locale.US,
                "{Quadrangle id=%s v%d; type=%s, area=%.2f, perimeter=%.2f, isConvex=%s}\n",
                quadrangleId,
                newVersion,
                currentParams.getType(),
                currentParams.getArea(),
                currentParams.getPerimeter(),
                currentParams.isConvex()
        );

        papyrus.write(newEntry);
        LOGGER.info("Added new version for Quadrangle with UUID {}: {}", quadrangleId, newEntry);
    }


    public Optional<QuadrangleParameters> getQuadrangleParameters(UUID uuid) {
        String regex = "\\{Quadrangle id=" + uuid + " v\\d+; type=(\\w+), area=([\\d.,]+), perimeter=([\\d.,]+), isConvex=(true|false)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(papyrus.read());

        if (matcher.find()) {
            // Заменяем запятые на точки
            double area = Double.parseDouble(matcher.group(2).replace(",", "."));
            double perimeter = Double.parseDouble(matcher.group(3).replace(",", "."));
            boolean isConvex = Boolean.parseBoolean(matcher.group(4));
            QuadrangleType type = QuadrangleType.valueOf(matcher.group(1));

            LOGGER.info("Found parameters for Quadrangle with UUID {}: type={}, area={}, perimeter={}, isConvex={}",
                    uuid, type, area, perimeter, isConvex);

            return Optional.of(new QuadrangleParameters(
                    calculator, area, perimeter, type, isConvex
            ));
        } else {
            LOGGER.warn("No parameters found for Quadrangle with UUID {}", uuid);
            return Optional.empty();
        }
    }


    public void multiplyQuadrangleParameters(UUID id, int multiplier) {
        if (id == null || multiplier <= 0) {
            throw new IllegalArgumentException("Invalid argument: id cannot be null, and multiplier must be greater than 0.");
        }

        Optional<QuadrangleParameters> optionalParams = getQuadrangleParameters(id);

        optionalParams.ifPresentOrElse(params -> {
            double newArea = params.getArea() * multiplier;
            double newPerimeter = params.getPerimeter() * multiplier;

            int currentVersion = extractCurrentVersion(id);

            String updatedEntry = String.format(Locale.US,
                    "{Quadrangle id=%s v%d; type=%s, area=%.2f, perimeter=%.2f, isConvex=%s}\n",
                    id,
                    currentVersion + 1,
                    params.getType(),
                    newArea,
                    newPerimeter,
                    params.isConvex()
            );

            papyrus.write(updatedEntry);
            LOGGER.info("Updated parameters for Quadrangle with UUID {} to version v{}: new area={}, new perimeter={}",
                    id, currentVersion + 1, newArea, newPerimeter);
        }, () -> LOGGER.warn("Quadrangle with UUID {} not found in papyrus. Multiplication not applied.", id));
    }


    public int extractCurrentVersion(UUID id) {
        // Регулярное выражение для поиска всех записей с заданным UUID
        String regex = "\\{Quadrangle id=" + id + " v(\\d+);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(papyrus.read());

        int maxVersion = 0;

        // Проходим по всем найденным версиям
        while (matcher.find()) {
            int version = Integer.parseInt(matcher.group(1));
            if (version > maxVersion) {
                maxVersion = version;
            }
        }

        return maxVersion; // Возвращаем максимальную найденную версию
    }
}
