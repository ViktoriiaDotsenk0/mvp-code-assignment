package com.skai.mvpassignment.util;

import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

class CSVParserImplTest {

    @Test
    public void testParse() throws FileNotFoundException {
        // Given
        CSVParserImpl<BasketballPlayerStats> parser = new CSVParserImpl<>();
        File basketballGameFile = new File("src/test/resources/test-tournament/basketball-game1.csv");
        Class<BasketballPlayerStats> type = BasketballPlayerStats.class;

        // When
        List<BasketballPlayerStats> result = parser.parse(basketballGameFile, type);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.size());
    }
}