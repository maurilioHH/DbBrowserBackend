package org.dbbrowser.api;

public record ApiResponse (
    boolean success,
    String message
){}

