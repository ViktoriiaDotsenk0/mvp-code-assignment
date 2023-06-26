package com.skai.mvpassignment.service.impl.basketball;

import com.skai.mvpassignment.model.PlayerData;
import com.skai.mvpassignment.model.statistics.BasketballPlayerStats;
import com.skai.mvpassignment.service.AwardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BasketballScoreCounterTest {

    @Mock
    private AwardService<BasketballPlayerStats> awardService;
    @InjectMocks
    private BasketballScoreCounter underTest;

    @Test
    public void testCountPlayersScore() {
        // Given
        File gameFile = Mockito.mock(File.class);
        BasketballPlayerStats playerStats1 = BasketballPlayerStats.builder()
                .playerData(PlayerData.builder()
                        .ratingPoints(0).build())
                .scoredPoints(1)
                .assists(1)
                .rebounds(1)
                .build();
        BasketballPlayerStats playerStats2 = BasketballPlayerStats.builder()
                .playerData(PlayerData.builder()
                        .ratingPoints(0).build())
                .scoredPoints(2)
                .assists(2)
                .rebounds(2)
                .build();
        List<BasketballPlayerStats> gamePlayers = Arrays.asList(playerStats1, playerStats2);
        Mockito.when(awardService.getPlayersWithBonuses(gameFile)).thenReturn(gamePlayers);

        underTest.setScoredPointsCoefficient(2);
        underTest.setReboundsCoefficient(1);
        underTest.setAssistsCoefficient(1);


        // When
        List<PlayerData> result = underTest.countPlayersScore(gameFile);

        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(4, result.get(0).getRatingPoints());
        Assertions.assertEquals(8, result.get(1).getRatingPoints());
    }
}
