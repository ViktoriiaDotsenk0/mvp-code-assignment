package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.exceptions.ErrorLogger;
import com.skai.mvpassignment.service.ScoreCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ScoreCounterProviderImplTest {
    private ScoreCounterProviderImpl underTest;
    private ScoreCounter mockScoreCounter;

    @BeforeEach
    public void setUp() {
        List<ScoreCounter> scoreCounters = new ArrayList<>();
        mockScoreCounter = Mockito.mock(ScoreCounter.class);
        scoreCounters.add(mockScoreCounter);
        Mockito.when(mockScoreCounter.getGameName()).thenReturn("BASKETBALL");

        underTest = new ScoreCounterProviderImpl(scoreCounters);
    }

    @Test
    public void testProviderWithSupportedGame() {
        // Given
        File file = new File("src/test/resources/test-tournament/basketball-game1.csv");

        Mockito.when(mockScoreCounter.getFileExtension()).thenReturn("csv");
        // When
        ScoreCounter result = underTest.getScoreCounter(file);

        // Then
        Assertions.assertEquals(mockScoreCounter, result);
    }

    @Test
    public void testProviderWithNotSupportedGameAndExtension() {
        // Given
        File unsupportedGameFile = new File("src/test/resources/test-tournament/handball-game1.csv");
        File unsupportedExtensionFile = new File("src/test/resources/test-tournament/text.txt");

        try (MockedStatic<ErrorLogger> mockedErrorLogger = Mockito.mockStatic(ErrorLogger.class)) {
            mockedErrorLogger.when(() -> ErrorLogger.logAndExit(any())).thenAnswer((Answer<Void>) invocation -> {
                throw new RuntimeException((String) invocation.getArgument(0));
            });

            // Then
            Assertions.assertThrows(RuntimeException.class, () -> underTest.getScoreCounter(unsupportedGameFile));
            Assertions.assertThrows(RuntimeException.class, () -> underTest.getScoreCounter(unsupportedExtensionFile));
        }
    }


    @Test
    public void testWithEmptyFile() {
        // Given
        File file = new File("src/test/resources/test-tournament/empty.csv");

        try (MockedStatic<ErrorLogger> mockedErrorLogger = Mockito.mockStatic(ErrorLogger.class)) {
            mockedErrorLogger.when(() -> ErrorLogger.logAndExit(Mockito.anyString())).thenAnswer((Answer<Void>) invocation -> {
                throw new RuntimeException((String) invocation.getArgument(0));
            });

        // Then
        Assertions.assertThrows(RuntimeException.class, () -> underTest.getScoreCounter(file));
        }
    }

    @Test
    public void testWithDirectory() {
        // Given
        File file = new File("src/test/resources/test-tournament");

        try (MockedStatic<ErrorLogger> mockedErrorLogger = Mockito.mockStatic(ErrorLogger.class)) {
            mockedErrorLogger.when(() -> ErrorLogger.logAndExit(Mockito.anyString())).thenAnswer((Answer<Void>) invocation -> {
                throw new RuntimeException((String) invocation.getArgument(0));
            });

            // Then
            Assertions.assertThrows(RuntimeException.class, () -> underTest.getScoreCounter(file));
        }
    }

}
