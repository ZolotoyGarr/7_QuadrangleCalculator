package org.example.reader;

import java.util.List;

public interface PointReader {
    void setSource(String source);
    List<String> read();
}
