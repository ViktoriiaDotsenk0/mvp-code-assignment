package com.skai.mvpassignment.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface CsvParser<T> {
    List<T> parse(File file, Class<? extends T> type) throws FileNotFoundException;
}
