package org.example.serializationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractStateSerializerTest {
//
//    private AbstractStateSerializer serializer;
//    private ObjectMapper objectMapper;
//
//    @TempDir
//    Path tempDir;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//        serializer = new JsonSerializer(); // Используем JsonSerializer как конкретную реализацию
//    }
//
//    @Test
//    void testSerializeAndSave() throws IOException {
//        // Подготовка мок-объекта с явным указанием типа
//        Statable<Map<String, String>> mockStatable = mock(Statable.class);
//        when(mockStatable.retrieveState()).thenReturn(Map.of("key", "value"));
//
//        Path testFile = tempDir.resolve("test.json");
//
//        // Тестируем метод
//        serializer.serializeAndSave(testFile, List.of(mockStatable));
//
//        // Проверяем, что файл создан и содержит корректные данные
//        assertTrue(Files.exists(testFile));
//        String content = Files.readString(testFile);
//        assertEquals("[{\"key\":\"value\"}]", content);
//    }
//
//
//    @Test
//    void testDownloadAndDeserialize() throws IOException {
//        Path testFile = tempDir.resolve("test.json");
//        Files.writeString(testFile, "[{\"key\":\"value\"}]");
//
//        StateName stateName = StateName.AUTOCAD_RECORDER;
//
//        // Добавляем маппер в StateManagerMapper (чтобы тест не зависел от основной мапы)
//        StateManagerMapper.MAPPERS = Map.of(
//                stateName, obj -> mock(Statable.class) // Используем мок-объект
//        );
//
//        List<Statable<?>> result = serializer.downloadAndDeserialize(testFile, stateName);
//
//        // Проверяем, что список не пуст
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void testDownloadAndDeserialize_InvalidJson() {
//        Path testFile = tempDir.resolve("invalid.json");
//
//        assertThrows(IOException.class, () -> {
//            serializer.downloadAndDeserialize(testFile, StateName.AUTOCAD_RECORDER);
//        });
//    }
//
//    @Test
//    void testDownloadAndDeserialize_MissingMapper() throws IOException {
//        Path testFile = tempDir.resolve("test.json");
//        Files.writeString(testFile, "[{\"key\":\"value\"}]");
//
//        StateName unknownState = StateName.QUADRANGLE_PUBLISHER;
//
//        // Проверяем, что выбрасывается исключение, если нет маппера
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            serializer.downloadAndDeserialize(testFile, unknownState);
//        });
//
//        assertEquals("Ошибка: Не найден маппер для " + unknownState, exception.getMessage());
//    }
}
