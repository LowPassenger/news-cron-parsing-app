package com.nc1.testapp.server.exception;

public class PeriodNotFoundException extends RuntimeException {
    public PeriodNotFoundException(String message) {
        super(message);
    }
}
