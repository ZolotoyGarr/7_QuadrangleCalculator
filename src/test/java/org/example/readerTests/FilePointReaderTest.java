package org.example.readerTests;

import org.example.reader.impl.FilePointReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilePointReaderTest {

    @Test
    void testReadValidFile() throws IOException {
        // given
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("1,1\n");
            writer.write("2,2\n");
        }

        FilePointReader reader = new FilePointReader();
        reader.setSource(tempFile.getAbsolutePath());

        // when
        List<String> lines = reader.read();

        // then
        Assertions.assertNotNull(lines);
        Assertions.assertEquals(2, lines.size());
        Assertions.assertEquals("1,1", lines.get(0));
        Assertions.assertEquals("2,2", lines.get(1));
    }

    @Test
    void testReadFileNotFound() {
        // given
        FilePointReader reader = new FilePointReader();
        reader.setSource("nonexistentfile.txt");

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, reader::read);
    }

    @Test
    void testReadFileWithoutSettingSource() {
        // given
        FilePointReader reader = new FilePointReader();

        // when & then
        Assertions.assertThrows(IllegalStateException.class, reader::read);
    }

    @Test
    void testReadEmptyFile() throws IOException {
        // given
        File tempFile = File.createTempFile("empty", ".txt");
        tempFile.deleteOnExit();

        FilePointReader reader = new FilePointReader();
        reader.setSource(tempFile.getAbsolutePath());

        // when
        List<String> lines = reader.read();

        // then
        Assertions.assertNotNull(lines);
        Assertions.assertTrue(lines.isEmpty());
    }
}
