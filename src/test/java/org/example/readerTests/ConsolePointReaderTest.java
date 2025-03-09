package org.example.readerTests;

import org.example.reader.impl.ConsolePointReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

public class ConsolePointReaderTest {

    @Test
    void testReadValidInput() {
        // given
        String simulatedInput = "1,1\n2,2\nexit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsolePointReader reader = new ConsolePointReader();

        // when
        List<String> lines = reader.read();

        // then
        Assertions.assertNotNull(lines);
        Assertions.assertEquals(2, lines.size());
        Assertions.assertEquals("1,1", lines.get(0));
        Assertions.assertEquals("2,2", lines.get(1));
    }

    @Test
    void testReadEmptyInput() {
        // given
        String simulatedInput = "exit\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsolePointReader reader = new ConsolePointReader();

        // when
        List<String> lines = reader.read();

        // then
        Assertions.assertNotNull(lines);
        Assertions.assertTrue(lines.isEmpty());
    }
}
