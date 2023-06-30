package com.skai.mvpassignment.service.impl;

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
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceImplTest {

    @InjectMocks
    private TournamentServiceImpl underTest;
    @Mock
    private AwardService mockAwardService;


    @Test
    public void testWithEmptyGameFolder() {
        // Given
        File gameDir = Mockito.mock(File.class);
        Mockito.when(gameDir.listFiles()).thenReturn(new File[0]);

        // Then
        Assertions.assertThrows(RuntimeException.class, () -> underTest.getTournamentPlayers(gameDir));

    }

    @Test
    public void testWithValidGames() {
        // Given
        File gameDir = Mockito.mock(File.class);
        File gameFile1 = Mockito.mock(File.class);
        File gameFile2 = Mockito.mock(File.class);

        PlayerData player1 = PlayerData.builder().nick("nick1").build();
        player1.setRatingPoints(10);
        PlayerData player2 = PlayerData.builder().nick("nick2").build();
        player2.setRatingPoints(20);
        PlayerData player3 = PlayerData.builder().nick("nick1").build();
        player3.setRatingPoints(5);
        List<PlayerData> gamePlayers1 = List.of(player1);
        List<PlayerData> gamePlayers2 = List.of(player2, player3);

        Mockito.when(gameDir.listFiles()).thenReturn(new File[]{gameFile1, gameFile2});
        Mockito.when(mockAwardService.getPlayersWithBonuses(gameFile1)).thenReturn(gamePlayers1);
        Mockito.when(mockAwardService.getPlayersWithBonuses(gameFile2)).thenReturn(gamePlayers2);

        // When
        List<PlayerData> result = underTest.getTournamentPlayers(gameDir);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(player1));
        Assertions.assertTrue(result.contains(player2));
        Mockito.verify(mockAwardService, Mockito.times(2))
                .getPlayersWithBonuses(Mockito.any(File.class));
    }

    @Test
    public void testWithNullAndEmptyList() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.getMVP(null));
            Assertions.assertThrows(IllegalArgumentException.class, () -> underTest.getMVP(new ArrayList<>()));
    }

    @Test
    public void testWithValidPlayers() {
        // Given
        PlayerData player1 = PlayerData.builder().nick("nick1").ratingPoints(10).build();
        PlayerData player2 = PlayerData.builder().nick("nick2").ratingPoints(20).build();

        List<PlayerData> players = List.of(player1, player2);

        // When
        PlayerData result = underTest.getMVP(players).get(0);

        // Then
        Assertions.assertEquals(player2, result);
    }

    @Test
    public void testWithDuplicatedScores() {
        // Given
        PlayerData player1 = PlayerData.builder().nick("nick1").ratingPoints(10).build();
        PlayerData player2 = PlayerData.builder().nick("nick2").ratingPoints(10).build();

        List<PlayerData> players = List.of(player1, player2);

        // When
        List<PlayerData> result = underTest.getMVP(players);

        // Then
        Assertions.assertTrue(result.contains(player1));
        Assertions.assertTrue(result.contains(player2));
    }
}
