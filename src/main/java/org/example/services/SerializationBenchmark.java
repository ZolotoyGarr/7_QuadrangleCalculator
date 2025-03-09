package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import javiki.course.serialization.serializer.StateManagerMapper;
import javiki.course.serialization.compressor.GzipCompressor;
import javiki.course.serialization.compressor.ZstdCompressor;
import javiki.course.serialization.serializer.BinarySerializer;
import javiki.course.serialization.serializer.JsonSerializer;
import javiki.course.serialization.serializer.XmlSerializer;
import org.example.calculators.QuadrangleCalculator;
import org.example.model.Point;
import org.example.model.Quadrangle;
import org.example.model.QuadrangleParameters;
import org.example.subscriber.impl.AutoCADRecorder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SerializationBenchmark {
    private static final JsonSerializer jsonSerializer = new JsonSerializer();
    private static final XmlSerializer xmlSerializer = new XmlSerializer();
    private static final BinarySerializer binarySerializer = new BinarySerializer();
    private static final GzipCompressor gzipCompressor = new GzipCompressor();
    private static final ZstdCompressor zstdCompressor = new ZstdCompressor();
    private static final Path OUTPUT_DIR = Paths.get("benchmark_results");

    public static void main(String[] args) throws Exception {
        Files.createDirectories(OUTPUT_DIR);

        // ✅ Регистрируем AutoCADRecorder как состояние
        registerAutoCADRecorderState();

        // Создаем AutoCADRecorder с 100,000 тестовыми данными
        QuadrangleCalculator calculator = new QuadrangleCalculator();
        Map<String, List<QuadrangleParameters>> testData = generateTestData(calculator, 100_000);
        AutoCADRecorder recorder = AutoCADRecorder.createInstanceFromState(calculator, testData);

        runBenchmark("JSON", jsonSerializer, recorder);
        runBenchmark("XML", xmlSerializer, recorder);
        runBenchmark("Binary", binarySerializer, recorder);
    }

    /**
     * ✅ Регистрирует `AutoCADRecorder` в `StateManagerMapper`
     */
    private static void registerAutoCADRecorderState() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType autoCadRecorderType = objectMapper.constructType(AutoCADRecorder.class);

        StateManagerMapper.registerState(
                "AUTOCAD_RECORDER",
                autoCadRecorderType,
                obj -> {
                    if (obj instanceof AutoCADRecorder) {
                        return (AutoCADRecorder) obj;
                    }
                    throw new IllegalArgumentException("Ошибка маппинга: объект не является AutoCADRecorder");
                }
        );
    }

    /**
     * Генерирует 100,000 тестовых данных с валидными Quadrangle.
     */
    private static Map<String, List<QuadrangleParameters>> generateTestData(QuadrangleCalculator calculator, int size) {
        return IntStream.range(0, size)
                .boxed()
                .collect(Collectors.toMap(
                        i -> "uuid_" + UUID.randomUUID(),
                        i -> List.of(new QuadrangleParameters(calculator, createRandomQuadrangle()))
                ));
    }

    /**
     * Создает случайный четырехугольник для тестов.
     */
    private static Quadrangle createRandomQuadrangle() {
        Random rand = new Random();
        return new Quadrangle(
                "quad_" + UUID.randomUUID(),
                List.of(
                        new Point(rand.nextDouble() * 100, rand.nextDouble() * 100),
                        new Point(rand.nextDouble() * 100, rand.nextDouble() * 100),
                        new Point(rand.nextDouble() * 100, rand.nextDouble() * 100),
                        new Point(rand.nextDouble() * 100, rand.nextDouble() * 100)
                )
        );
    }

    private static void runBenchmark(String format, Object serializer, AutoCADRecorder recorder) throws Exception {
        System.out.println("\n===== " + format + " Benchmark =====");

        Path serializedFile = OUTPUT_DIR.resolve("test" + getFileExtension(serializer));
        Path gzipFile = serializedFile.resolveSibling(serializedFile.getFileName() + ".gz");
        Path zstdFile = serializedFile.resolveSibling(serializedFile.getFileName() + ".zstd");

        // Сериализация
        long start = System.nanoTime();
        if (serializer instanceof JsonSerializer json) json.serializeAndSave(serializedFile, List.of(recorder));
        if (serializer instanceof XmlSerializer xml) xml.serializeAndSave(serializedFile, List.of(recorder));
        if (serializer instanceof BinarySerializer bin) bin.serializeAndSave(serializedFile, List.of(recorder));
        long serializationTime = System.nanoTime() - start;

        // Получение размера сериализованного файла
        long originalSize = Files.size(serializedFile);

        // Десериализация
        start = System.nanoTime();
        if (serializer instanceof JsonSerializer json) json.downloadAndDeserialize(serializedFile, recorder.stateName());
        if (serializer instanceof XmlSerializer xml) xml.downloadAndDeserialize(serializedFile, recorder.stateName());
        if (serializer instanceof BinarySerializer bin) bin.downloadAndDeserialize(serializedFile, recorder.stateName());
        long deserializationTime = System.nanoTime() - start;

        // Компрессия Gzip
        start = System.nanoTime();
        gzipCompressor.compressFile(serializedFile, gzipFile);
        long gzipCompressionTime = System.nanoTime() - start;
        long gzipSize = Files.size(gzipFile);

        // Компрессия Zstd
        start = System.nanoTime();
        zstdCompressor.compressFile(serializedFile, zstdFile);
        long zstdCompressionTime = System.nanoTime() - start;
        long zstdSize = Files.size(zstdFile);

        // Десериализация из Gzip
        start = System.nanoTime();
        gzipCompressor.decompressFile(gzipFile, serializedFile);
        long gzipDecompressionTime = System.nanoTime() - start;

        // Десериализация из Zstd
        start = System.nanoTime();
        zstdCompressor.decompressFile(zstdFile, serializedFile);
        long zstdDecompressionTime = System.nanoTime() - start;

        // Вывод результатов
        System.out.printf("Сериализация: %d мс\n", serializationTime / 1_000_000);
        System.out.printf("Размер файла: %.2f MB\n", originalSize / (1024.0 * 1024.0));
        System.out.printf("Десериализация: %d мс\n", deserializationTime / 1_000_000);
        System.out.printf("Компрессия (Gzip): %d мс\n", gzipCompressionTime / 1_000_000);
        System.out.printf("Размер (Gzip): %.2f MB (%.2f%% от оригинала)\n", gzipSize / (1024.0 * 1024.0), (gzipSize * 100.0) / originalSize);
        System.out.printf("Компрессия (Zstd): %d мс\n", zstdCompressionTime / 1_000_000);
        System.out.printf("Размер (Zstd): %.2f MB (%.2f%% от оригинала)\n", zstdSize / (1024.0 * 1024.0), (zstdSize * 100.0) / originalSize);
        System.out.printf("Декомпрессия (Gzip): %d мс\n", gzipDecompressionTime / 1_000_000);
        System.out.printf("Декомпрессия (Zstd): %d мс\n", zstdDecompressionTime / 1_000_000);
        System.out.println("=================================");
    }

    private static String getFileExtension(Object serializer) {
        if (serializer instanceof JsonSerializer) return ".json";
        if (serializer instanceof XmlSerializer) return ".xml";
        if (serializer instanceof BinarySerializer) return ".bin";
        return "";
    }
}
