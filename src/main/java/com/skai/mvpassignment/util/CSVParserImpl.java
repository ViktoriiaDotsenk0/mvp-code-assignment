package com.skai.mvpassignment.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CSVParserImpl<T> implements CsvParser<T> {

    public List<T> parse(File file, Class<? extends T> type) throws FileNotFoundException {
        return new CsvToBeanBuilder<T>(new FileReader(file.getPath()))
                .withType(type)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse();
    }

}
