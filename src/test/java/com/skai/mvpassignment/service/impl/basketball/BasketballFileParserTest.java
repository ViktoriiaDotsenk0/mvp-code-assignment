package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.service.PlayersStatsValidator;
import com.skai.mvpassignment.util.CsvParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketballFileParserTest {
    @InjectMocks
    private BasketballFileParser underTest;
    @Mock
    private CsvParser<BasketballPlayerStats> csvParser;
    @Mock
    private PlayersStatsValidator playersStatsValidator;


    @Test
    public void testGetTeamsStats() throws FileNotFoundException {
        // Given
        File file = Mockito.mock(File.class);
        List<BasketballPlayerStats> playerStatsList = getPlayersStatsList();
        when(csvParser.parse(file, BasketballPlayerStats.class)).thenReturn(playerStatsList);

        // When
        Map<String, List<BasketballPlayerStats>> result = underTest.getTeamsStats(file);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("Team A"));
        Assertions.assertTrue(result.containsKey("Team B"));
        Assertions.assertEquals(2, result.get("Team A").size());
        Assertions.assertEquals(1, result.get("Team B").size());
    }

    @Test
    public void testWithValidationException() throws FileNotFoundException, ValidationException {
        //Given
        BasketballPlayerStats basketballPlayerStats = Mockito.mock(BasketballPlayerStats.class);
        File file = Mockito.mock(File.class);
        List<BasketballPlayerStats> statsList  = List.of(basketballPlayerStats);
        when(csvParser.parse(file, BasketballPlayerStats.class)).thenReturn(statsList);

        Mockito.doThrow(new ValidationException("Validation failed."))
                .when(playersStatsValidator)
                .validate(statsList,null);

        try (MockedStatic<ErrorLogger> mockedErrorLogger = Mockito.mockStatic(ErrorLogger.class)) {
            mockedErrorLogger.when(() -> ErrorLogger.logAndExit(Mockito.anyString())).thenAnswer((Answer<Void>) invocation -> {
                throw new RuntimeException((String) invocation.getArgument(0));
            });

            // Then
            Assertions.assertThrows(RuntimeException.class, () -> underTest.getTeamsStats(file));
        }

    }

    private List<BasketballPlayerStats> getPlayersStatsList() {
        BasketballPlayerStats player1 = new BasketballPlayerStats("name1", "nick1", "12", "Team A", 10, 12, 8, new PlayerData());
        BasketballPlayerStats player2 = new BasketballPlayerStats("name2", "nick2", "13", "Team B", 12, 15, 9, new PlayerData());
        BasketballPlayerStats player3 = new BasketballPlayerStats("name3", "nick3", "14", "Team A", 5, 12, 10, new PlayerData());
        return Arrays.asList(player1, player2, player3);
    }
}
