package org.example.reader.impl;

import org.example.reader.PointReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsolePointReader implements PointReader {
    private static final Logger LOGGER = LogManager.getLogger(ConsolePointReader.class);


    @Override
    public List<String> read() {
        List<String> lines = new ArrayList<>();
        LOGGER.info("Enter lines (type 'exit' to finish):");
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    LOGGER.info("Exit command received.");
                    break;
                }
                lines.add(line);
                LOGGER.debug("Added line: {}", line);
            }
        }
        LOGGER.info("Input finished. Total lines received: {}.", lines.size());
        return lines;
    }

    @Override
    public void setSource(String source) {
        LOGGER.warn("The setSource method is not supported for ConsolePointReader.");
    }
}
