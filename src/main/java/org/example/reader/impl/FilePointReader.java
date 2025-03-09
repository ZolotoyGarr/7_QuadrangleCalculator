package org.example.reader.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.reader.PointReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Основные отличия log4j 2.x от log4j 1.x
//1. Поддержка плейсхолдеров {}
//log4j 2.x: В log4j 2.x можно использовать плейсхолдеры {} для форматирования строк. Это позволяет избегать явного вызова методов, таких как String.format() или конкатенации строк.
//
//java
//Копировать код
//logger.info("Source file set to: {}", source);
//logger.info("Successfully read {} lines from file: {}", lines.size(), fileName);
//Плейсхолдеры автоматически заменяются соответствующими значениями. Это делает код более читаемым и эффективным, так как лог-сообщения не форматируются, если текущий уровень логирования ниже заданного (ленивое форматирование).
//
//log4j 1.x: Плейсхолдеры {} не поддерживаются. Для форматирования строк нужно использовать конкатенацию:
//
//java
//Копировать код
//logger.info("Source file set to: " + source);
//logger.info("Successfully read " + lines.size() + " lines from file: " + fileName);
//2. Производительность
//log4j 2.x:
//
//Использует асинхронное логирование, что значительно повышает производительность в системах с высокой нагрузкой.
//Обеспечивает ленивое вычисление строк: строка не будет формироваться, если уровень логирования ниже текущего.
//log4j 1.x:
//
//Логирование синхронное, что может замедлять работу приложения при большом количестве логов.
//Форматирование строк выполняется всегда, даже если лог не будет записан (что увеличивает накладные расходы).
//3. Управление конфигурацией
//log4j 2.x:
//
//Поддерживает конфигурационные файлы в форматах XML, JSON, YAML, а также программное конфигурирование.
//Динамическая перезагрузка конфигурации: можно менять конфигурацию логгера в реальном времени без перезапуска приложения.
//Пример log4j2.xml:
//xml
//Копировать код
//<Configuration status="WARN">
//    <Appenders>
//        <Console name="Console" target="SYSTEM_OUT">
//            <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss}] [%p] [%c{1}] - %m%n" />
//        </Console>
//    </Appenders>
//    <Loggers>
//        <Root level="info">
//            <AppenderRef ref="Console" />
//        </Root>
//    </Loggers>
//</Configuration>
//log4j 1.x:
//
//Использует только файл log4j.properties для конфигурации.
//Не поддерживает динамическую перезагрузку конфигурации.
//4. Интеграция с другими библиотеками
//log4j 2.x:
//
//Поддерживает интеграцию с SLF4J через модуль log4j-slf4j-impl.
//Легко интегрируется с другими фреймворками и инструментами, такими как Spring, Hibernate и т.д.
//log4j 1.x:
//
//Ограниченная интеграция с другими библиотеками. Используется только в проектах, где старые версии логирования являются стандартом.
//5. Совместимость
//log4j 2.x:
//
//Поддерживает устаревшие библиотеки через мосты (например, log4j-1.2-api, slf4j-to-log4j2).
//log4j 1.x:
//
//Устаревшая библиотека, не поддерживается и не рекомендуется к использованию, так как не получает обновлений и исправлений уязвимостей.
//6. Безопасность
//log4j 2.x:
//
//Более безопасен и постоянно обновляется. Учитывает современные требования к безопасности.
//log4j 1.x:
//
//Устаревший и больше не поддерживается. Использование может быть небезопасным.
//Почему стоит выбрать log4j 2.x:
//Современная архитектура с акцентом на производительность.
//Удобство работы с плейсхолдерами {}.
//Гибкость конфигурации.
//Постоянные обновления и поддержка.
//Динамическая перезагрузка конфигурации и поддержка различных форматов конфигурации.
//Использование log4j 2.x делает приложение более производительным, читаемым и безопасным. Это основное отличие от устаревшего log4j 1.x.

public class FilePointReader implements PointReader {
    private static final Logger LOGGER = LogManager.getLogger(FilePointReader.class);
    private String fileName;
//
//    public FilePointReader(String fileName) {
//        setSource(fileName);
//    }

    @Override
    public void setSource(String source) {
        this.fileName = source;
        LOGGER.info("Source file set to: {}", source);
    }

    @Override
    public List<String> read() {
        List<String> lines = new ArrayList<>();
        if (fileName == null) {
            LOGGER.error("File name is not set. Use setSource() to specify the file.");
            throw new IllegalStateException("File name is not set (use setSource)");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            LOGGER.info("Successfully read {} lines from file: {}", lines.size(), fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: {}", fileName, e);
            throw new IllegalArgumentException("File not found: " + fileName, e);
        } catch (IOException e) {
            LOGGER.error("An I/O error occurred while reading the file: {}", fileName, e);
            throw new RuntimeException("I/O error occurred while reading the file: " + fileName, e);
        }
        return lines;
    }
}
