package com.tareas.api.exception;

import org.springframework.http.HttpStatus;

// ErrorCode.java
public enum ErrorCode {

    // 4xx
    VALIDATION_ERROR("VALIDATION-400-001", "Error de validación", HttpStatus.BAD_REQUEST),
    TASK_NOT_FOUND("TASK-404-001", "Tarea no encontrada", HttpStatus.NOT_FOUND),

    // 5xx
    INTERNAL_ERROR("INTERNAL-500-001", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String title;
    private final HttpStatus status;

    ErrorCode(String code, String title, HttpStatus status) {
        this.code = code;
        this.title = title;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public String title() {
        return title;
    }

    public HttpStatus status() {
        return status;
    }
}
