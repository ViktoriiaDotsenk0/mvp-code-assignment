package com.skai.mvpassignment.service.impl;

import com.skai.mvpassignment.service.GameFileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class GameFileServiceImpl implements GameFileService {
    @Override
    public String getGameName(File gameFile) {
        validateFile(gameFile);
        try (BufferedReader br = new BufferedReader(new FileReader(gameFile.getPath()))) {
            String firstLine = br.readLine();
            if (firstLine == null || firstLine.equals(""))
                throw new IllegalStateException("Game name is not determined in file: " + gameFile.getPath());
            return firstLine;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validateFile(File gameFile) {
        if (gameFile.isDirectory())
            throw new IllegalStateException("Game file cannot be a directory: " + gameFile.getPath());
        else if (!FilenameUtils.getExtension(gameFile.getName()).equals("csv"))
            throw new RuntimeException("Unsupported file extension");
    }
}
