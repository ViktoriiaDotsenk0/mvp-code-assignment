package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.service.GameFileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameFileServiceImplTest {

    private final GameFileService underTest = new GameFileServiceImpl();


    @Test
    public void testGetGameName() {
        // given
        File gameFile = new File("src/test/resources/test-tournament/basketball-game.csv");
        String expectedGameName = "BASKETBALL";

        // when
        String gameName = underTest.getGameName(gameFile);

        // then
        assertEquals(expectedGameName, gameName);
    }

    @Test
    public void testGetGameNameWithEmptyFile() throws IOException {
        // Arrange
        File gameFile = new File("src/test/resources/test-tournament/empty.csv");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> underTest.getGameName(gameFile));
    }

    @Test
    public void testGetGameNameWithDirectory() {
        // given
        File gameFile = new File("src/test/resources/test-tournament/");

        // then
        assertThrows(IllegalStateException.class, () -> underTest.getGameName(gameFile));
    }

    @Test
    public void testGetGameNameWithUnsupportedExtension() {
        // given
        File gameFile = new File("src/test/resources/test-tournament/text.txt");

        // then
        assertThrows(IllegalStateException.class, () -> underTest.getGameName(gameFile));
    }
}