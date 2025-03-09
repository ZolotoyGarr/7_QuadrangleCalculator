package org.example.subscriber.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.QuadrangleParameters;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoCADRecorderDeserializer extends JsonDeserializer<AutoCADRecorder> {
    @Override
    public AutoCADRecorder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Создаем экземпляр калькулятора
        QuadrangleCalculator calculator = new QuadrangleCalculator();

        // Десериализуем `subs`
        Map<String, List<QuadrangleParameters>> subs = new HashMap<>();
        JsonNode subsNode = node.get("subscriptions");

        if (subsNode != null && subsNode.isObject()) {
            subs = jsonParser.getCodec().treeToValue(subsNode, Map.class);
        }

        // ✅ Вызываем `createInstanceFromState()`
        return AutoCADRecorder.createInstanceFromState(calculator, subs);
    }
}
