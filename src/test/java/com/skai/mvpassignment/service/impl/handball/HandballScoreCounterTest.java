package com.skai.mvpassignment.service.impl.handball;

import com.skai.mvpassignment.model.statistics.HandballPlayerStats;
import com.skai.mvpassignment.model.PlayerData;
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
public class HandballScoreCounterTest {

    @Mock
    private AwardService<HandballPlayerStats> awardService;
    @InjectMocks
    private HandballScoreCounter underTest;


    @Test
    public void testCountPlayersScore() {
        // Given
        File gameFile = Mockito.mock(File.class);
        HandballPlayerStats playerStats1 = HandballPlayerStats.builder()
                .playerData(PlayerData.builder()
                        .ratingPoints(0).build())
                .goalsMade(2)
                .goalsReceived(1)
                .build();
        HandballPlayerStats playerStats2 = HandballPlayerStats.builder()
                .playerData(PlayerData.builder()
                        .ratingPoints(0).build())
                .goalsMade(3)
                .goalsReceived(4)
                .build();
        List<HandballPlayerStats> gamePlayers = Arrays.asList(playerStats1, playerStats2);
        Mockito.when(awardService.getPlayersWithBonuses(gameFile)).thenReturn(gamePlayers);

        underTest.setGoalsMadeCoefficient(5);
        underTest.setGoalsReceivedCoefficient(-1);


        // When
        List<PlayerData> result = underTest.countPlayersScore(gameFile);

        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(9, result.get(0).getRatingPoints());
        Assertions.assertEquals(11, result.get(1).getRatingPoints());
    }
}
