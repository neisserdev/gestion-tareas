package com.tareas.api.exception;

public class TareaNotFoundException extends RuntimeException {
    public TareaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
