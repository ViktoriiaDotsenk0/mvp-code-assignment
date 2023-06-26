package com.skai.mvpassignment.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorLogger {
    public static void logAndExit(String message) {
        log.error(message);
        System.exit(0);
    }
}