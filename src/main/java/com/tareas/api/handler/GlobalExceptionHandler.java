package com.tareas.api.handler;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.tareas.api.exception.ErrorCode;
import com.tareas.api.exception.ProblemDetailFactory;
import com.tareas.api.exception.TareaNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TareaNotFoundException.class)
    public ProblemDetail handleNotFound(TareaNotFoundException ex, HttpServletRequest request) {
        return ProblemDetailFactory.from(
                ErrorCode.TASK_NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        ProblemDetail pd = ProblemDetailFactory.from(
                ErrorCode.VALIDATION_ERROR,
                "Los datos enviados no son válidos",
                uri,
                Map.of(
                        "errors", fieldErrors,
                        "errorCount", fieldErrors.size()));

        return ResponseEntity.status(pd.getStatus()).body(pd);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex, HttpServletRequest request) {
        return ProblemDetailFactory.from(
                ErrorCode.INTERNAL_ERROR,
                "Ha ocurrido un error inesperado",
                request.getRequestURI());
    }
}
