package org.example.Reader;

import java.util.List;

public interface Reader {
    void setSource(String source);
    List<String> read();
}
