package com.nc1.testapp.server.exception;

public record ErrorResponse(int statusCode, String errorMessage) {
}
