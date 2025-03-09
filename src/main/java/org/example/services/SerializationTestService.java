package org.example.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import javiki.course.serialization.serializer.BinarySerializer;
import javiki.course.serialization.serializer.JsonSerializer;
import javiki.course.serialization.serializer.XmlSerializer;
import javiki.course.serialization.serializer.StateManagerMapper;
import javiki.course.serialization.StateType;
import javiki.course.serialization.manager.ApplicationStateManager;
import javiki.course.serialization.manager.StateManager;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.subscriber.impl.AutoCadRecorderPool;
import org.example.subscriber.impl.AutoCADRecorder;
import javiki.course.serialization.Statable;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class SerializationTestService {
    private static final Quadrangle testQuadrangle = new Quadrangle(List.of(
            new Point(0, 0),
            new Point(4, 0),
            new Point(4, 3),
            new Point(0, 3)
    ));

    private final StateManager stateManager;
    private final AutoCadRecorderPool recorderPool;
    private AutoCADRecorder autoCADRecorder;

    public SerializationTestService(StateManager stateManager) {
        this.stateManager = stateManager;
        this.recorderPool = new AutoCadRecorderPool((ApplicationStateManager) stateManager, new QuadrangleCalculator());
        this.autoCADRecorder = recorderPool.createAutoCadRecorder();
    }

    public static void main(String[] args) {
        String stateFolderPath = "C:\\Users\\BountyHunter\\IdeaProjects\\7_Quadrangle\\state";
        Paths.get(stateFolderPath).toFile().mkdirs();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        StateManager stateManager = chooseStateManager(stateFolderPath);
        SerializationTestService service = new SerializationTestService(stateManager);

        // ✅ Регистрация состояния AutoCADRecorder
        JavaType stringType = objectMapper.constructType(String.class);
        JavaType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, QuadrangleParameters.class);
        JavaType mapType = objectMapper.getTypeFactory()
                .constructMapType(Map.class, stringType, listType);

        StateType.register(new StateType(
                mapType,
                "AUTOCAD_RECORDER",
                "AutoCADRecorder description"
        ));

        // ✅ Регистрация маппера для состояния
        StateManagerMapper.registerState(
                StateType.get("AUTOCAD_RECORDER"),
                (objectValue) -> AutoCADRecorder.createInstanceFromState(
                        new QuadrangleCalculator(), (Map<String, List<QuadrangleParameters>>) objectValue
                )
        );

        service.testAutoCADRecorderSerialization();
        service.testAutoCADRecorderDeserialization();
    }

    private static StateManager chooseStateManager(String stateFolderPath) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите формат хранения данных: ");
            System.out.println("1 - JSON");
            System.out.println("2 - XML");
            System.out.println("3 - Binary");
            System.out.print("Введите число (1, 2 или 3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("✅ Выбран формат JSON.");
                    return new ApplicationStateManager(stateFolderPath, new JsonSerializer());
                case "2":
                    System.out.println("✅ Выбран формат XML.");
                    return new ApplicationStateManager(stateFolderPath, new XmlSerializer());
                case "3":
                    System.out.println("✅ Выбран формат Binary.");
                    return new ApplicationStateManager(stateFolderPath, new BinarySerializer());
                default:
                    System.out.println("❌ Ошибка: выберите 1, 2 или 3.");
            }
        }
    }

    public void testAutoCADRecorderSerialization() {
        System.out.println("\n🔹 Начинаем тест сохранения состояния...");

        autoCADRecorder.update(testQuadrangle);
        stateManager.saveGlobalState();

        System.out.println("✅ Состояние AutoCADRecorder успешно сохранено.");
    }

    public void testAutoCADRecorderDeserialization() {
        System.out.println("\n🔹 Начинаем тест загрузки состояния...");

        stateManager.loadGlobalState();

        Optional<StateType> autocadRecorderState = Optional.ofNullable(StateType.get("AUTOCAD_RECORDER"));
        if (autocadRecorderState.isEmpty()) {
            System.out.println("❌ Ошибка: Состояние AutoCADRecorder не найдено!");
            return;
        }

        Map<StateType, List<Statable<?>>> loadedStates = stateManager.download();
        if (loadedStates.isEmpty() || !loadedStates.containsKey(autocadRecorderState.get())) {
            System.out.println("❌ Ошибка: Загруженное состояние пустое!");
            return;
        }

        List<Statable<?>> stateList = loadedStates.get(autocadRecorderState.get());
        if (stateList == null || stateList.isEmpty()) {
            System.out.println("❌ Ошибка: Загруженный список состояний пуст!");
            return;
        }

        Statable<?> firstState = stateList.get(0);
        if (!(firstState instanceof AutoCADRecorder)) {
            System.out.println("❌ Ошибка: Неверный тип состояния!");
            return;
        }

        autoCADRecorder = recorderPool.createAutoCadRecorder(((AutoCADRecorder) firstState).retrieveState());

        Map<String, List<QuadrangleParameters>> subscriptions = autoCADRecorder.retrieveState();
        System.out.println("📜 Загруженное состояние:");
        subscriptions.forEach((uuid, params) -> {
            System.out.println("🔹 Quadrangle ID: " + uuid);
            params.forEach(System.out::println);
        });

        System.out.println("✅ Состояние AutoCADRecorder успешно загружено.");
    }

    public AutoCADRecorder getAutoCADRecorder() {
        return autoCADRecorder;
    }
}
