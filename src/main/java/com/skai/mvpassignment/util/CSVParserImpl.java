package com.skai.mvpassignment.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.skai.mvpassignment.exceptions.ErrorLogger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CSVParserImpl<T> implements CsvParser<T> {

    public List<T> parse(File file, Class<T> type) throws FileNotFoundException {
        return new CsvToBeanBuilder<T>(new FileReader(file.getPath()))
                .withType(type)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse();
    }

}
