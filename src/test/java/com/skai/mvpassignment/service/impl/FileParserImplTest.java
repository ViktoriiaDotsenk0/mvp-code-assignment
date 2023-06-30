package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.PlayerStats;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import com.skai.mvpassignment.util.CsvParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileParserImplTest {
    @InjectMocks
    private FileParserImpl underTest;
    @Mock
    private CsvParser<BasketballPlayerStats> mockCsvParser;
    @Mock
    private PlayersStatsValidator mockValidator;

    @Test
    public void testGetTeamsStats() throws FileNotFoundException, ValidationException {
        // given
        File gameFile = Mockito.mock(File.class);
        List<BasketballPlayerStats> playerStatsList = List.of(
                BasketballPlayerStats.builder().teamName("Team A").build(),
                BasketballPlayerStats.builder().teamName("Team A").build(),
                BasketballPlayerStats.builder().teamName("Team B").build(),
                BasketballPlayerStats.builder().teamName("Team B").build()
        );

        Mockito.when(mockCsvParser.parse(gameFile, BasketballPlayerStats.class)).thenReturn(playerStatsList);

        // when
        Map<String, List<PlayerStats>> teamsStats = underTest.getTeamsStats(gameFile, BasketballPlayerStats.class);

        // then
        assertEquals(2, teamsStats.size());
        assertEquals(2, teamsStats.get("Team A").size());
        assertEquals(2, teamsStats.get("Team B").size());
        Mockito.verify(mockCsvParser, Mockito.times(1)).parse(gameFile, BasketballPlayerStats.class);
        Mockito.verify(mockValidator, Mockito.times(1)).validate(playerStatsList,null);
    }

    @Test
    public void testGetTeamsStatsWithInvalidFile() throws FileNotFoundException {
        // given
        File gameFile = new File("test.csv");
        RuntimeException exception = new RuntimeException("Invalid file");

        Mockito.when(mockCsvParser.parse(gameFile, BasketballPlayerStats.class)).thenThrow(exception);

        // then
        RuntimeException thrownException = assertThrows(RuntimeException.class, () ->
                underTest.getTeamsStats(gameFile, BasketballPlayerStats.class));
        assertEquals(exception.getMessage(), thrownException.getMessage());
        Mockito.verify(mockCsvParser, Mockito.times(1)).parse(gameFile, BasketballPlayerStats.class);
        Mockito.verifyNoInteractions(mockValidator);
    }


}