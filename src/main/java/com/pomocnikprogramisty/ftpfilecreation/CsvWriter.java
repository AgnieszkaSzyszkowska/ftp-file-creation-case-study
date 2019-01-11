package com.pomocnikprogramisty.ftpfilecreation;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
class CsvWriter {

    InputStream writeToCsv(final List<Entry> list) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Entry.generateHeader().getBytes());
        for (final Entry line : list) {
            baos.write(line.generateRow().getBytes());
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
