package com.codersHub.AniTracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
}
