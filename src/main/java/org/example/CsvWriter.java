package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class CsvWriter implements Closeable, Flushable {
    private final BufferedWriter w;

    public CsvWriter(Path path) throws IOException {
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        this.w = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
    }

    public void header(String... cols) throws IOException {
        writeRow((Object[]) cols);
    }

    public void writeRow(Object... vals) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++) {
            if (i > 0) sb.append(',');
            String s = String.valueOf(vals[i]).replace("\"", "\"\"");
            if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
                sb.append('"').append(s).append('"');
            } else sb.append(s);
        }
        sb.append('\n');
        w.write(sb.toString());
    }

    @Override public void flush() throws IOException { w.flush(); }
    @Override public void close() throws IOException { w.close(); }
}
