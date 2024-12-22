package org.example.Reader.impl;

import org.example.Reader.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsolePointReader implements Reader {
    @Override
    public List<String> read() {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        System.out.println("Введите строки (введите 'exit' для завершения):");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            lines.add(line);
        }
        return lines;
    }

    @Override
    public void setSource(String source) {

    }
}
