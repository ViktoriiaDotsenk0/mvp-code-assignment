package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.exceptions.ValidationException;
import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class HandballFileParserTest {

    @InjectMocks
    private HandballFileParser underTest;

    @Mock
    private CsvParser<HandballPlayerStats> csvParser;

    @Mock
    private PlayersStatsValidator playersStatsValidator;



    @Test
    public void testGetTeamsStats() throws FileNotFoundException {
        // Given
        File file = Mockito.mock(File.class);
        List<HandballPlayerStats> playerStatsList = getPlayersStatsList();
        when(csvParser.parse(any(File.class), any())).thenReturn(playerStatsList);

        // When
        Map<String, List<HandballPlayerStats>> result = underTest.getTeamsStats(file);

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
        HandballPlayerStats handballPlayerStats = Mockito.mock(HandballPlayerStats.class);
        File file = Mockito.mock(File.class);
        List<HandballPlayerStats> statsList  = List.of(handballPlayerStats);
        when(csvParser.parse(file, HandballPlayerStats.class)).thenReturn(statsList);

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

    private List<HandballPlayerStats> getPlayersStatsList(){
        HandballPlayerStats player1 = new HandballPlayerStats("name1","nick1", "12","Team A", 10, 12, new PlayerData());
        HandballPlayerStats player2 = new HandballPlayerStats("name2","nick2", "13","Team B", 12, 15, new PlayerData());
        HandballPlayerStats player3 = new HandballPlayerStats("name3","nick3", "14","Team A", 5, 12, new PlayerData());
        return Arrays.asList(player1, player2, player3);
    }
}
