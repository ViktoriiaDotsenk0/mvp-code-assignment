package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.service.BonusService;
import com.skai.mvpassignment.service.FileParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class BasketballAwardServiceTest {

    @InjectMocks
    private BasketballAwardService underTest;

    @Mock
    private FileParser<BasketballPlayerStats> fileParser;

    @Mock
    private BonusService bonusService;


    @Test
    public void testWithValidGameFile() {
        // Given
        File gameFile = Mockito.mock(File.class);
        Map<String, List<BasketballPlayerStats>> teamsPlayerStats = new HashMap<>();
        List<BasketballPlayerStats> teamAStats = new ArrayList<>();
        teamAStats.add(new BasketballPlayerStats("name1", "nick1", "12", "Team A", 1, 2, 3, null));
        teamAStats.add(new BasketballPlayerStats("name2", "nick2", "13", "Team A", 3, 2, 1, null));
        teamsPlayerStats.put("Team A", teamAStats);

        List<BasketballPlayerStats> teamBStats = new ArrayList<>();
        teamBStats.add(new BasketballPlayerStats("name3", "nick3", "14", "Team B", 3, 2, 3, null));
        teamBStats.add(new BasketballPlayerStats("name4", "nick4", "15", "Team B", 3, 2, 1, null));
        teamsPlayerStats.put("Team B", teamBStats);

        Mockito.when(fileParser.getTeamsStats(gameFile)).thenReturn(teamsPlayerStats);

        // When
        List<BasketballPlayerStats> result = underTest.getPlayersWithBonuses(gameFile);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
        Mockito.verify(bonusService).addWinnersBonuses(teamBStats);
    }

    @Test
    public void testWithTheSameScore() {
        // Given
        File gameFile = Mockito.mock(File.class);
        Map<String, List<BasketballPlayerStats>> teamsPlayerStats = new HashMap<>();
        List<BasketballPlayerStats> teamAStats = new ArrayList<>();
        teamAStats.add(new BasketballPlayerStats("name1", "nick1", "12", "Team A", 1, 2, 3, null));
        teamAStats.add(new BasketballPlayerStats("name2", "nick2", "13", "Team A", 3, 2, 1, null));
        teamsPlayerStats.put("Team A", teamAStats);

        List<BasketballPlayerStats> teamBStats = new ArrayList<>();
        teamBStats.add(new BasketballPlayerStats("name3", "nick3", "14", "Team B", 1, 2, 3, null));
        teamBStats.add(new BasketballPlayerStats("name4", "nick4", "15", "Team B", 3, 2, 1, null));
        teamsPlayerStats.put("Team B", teamBStats);

        Mockito.when(fileParser.getTeamsStats(gameFile)).thenReturn(teamsPlayerStats);

        try (MockedStatic<ErrorLogger> mockedErrorLogger = Mockito.mockStatic(ErrorLogger.class)) {
            mockedErrorLogger.when(() -> ErrorLogger.logAndExit(Mockito.anyString())).thenAnswer((Answer<Void>) invocation -> {
                throw new RuntimeException((String) invocation.getArgument(0));
            });

            // Then
            Assertions.assertThrows(RuntimeException.class, () -> underTest.getPlayersWithBonuses(gameFile));
        }
    }
}
