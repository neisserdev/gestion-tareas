package com.neisser.springboot_plantilla.exception;

public class TareaNotFoundException extends RuntimeException {
    public TareaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
