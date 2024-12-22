package org.example.Reader.impl;

import org.example.Reader.Reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePointReader implements Reader {
    private String fileName;

    @Override
    public void setSource(String source) {
        this.fileName = source;
    }

    @Override
    public List<String> read() {
        List<String> lines = new ArrayList<>();
        if (fileName == null) {
            throw new IllegalStateException("File name is not set (use setSource)");
        }
        //Copilot предлагал еще
        // try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
        // BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
