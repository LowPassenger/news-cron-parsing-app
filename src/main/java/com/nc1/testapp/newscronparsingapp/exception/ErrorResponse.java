package com.nc1.testapp.newscronparsingapp.exception;

import lombok.Getter;

@Getter
public record ErrorResponse(int statusCode, String errorMessage) {
}
