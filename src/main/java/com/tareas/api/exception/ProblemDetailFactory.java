package com.tareas.api.exception;

import java.net.URI;
import java.time.Instant;
import java.util.Map;

import org.springframework.http.ProblemDetail;

// ProblemDetailFactory.java
public final class ProblemDetailFactory {

    private static final String BASE_TYPE = "https://api.neisser.dev/problems/";

    private ProblemDetailFactory() {
    }

    public static ProblemDetail from(ErrorCode error, String detail, String requestUri) {
        return from(error, detail, requestUri, Map.of());
    }

    public static ProblemDetail from(
            ErrorCode error,
            String detail,
            String requestUri,
            Map<String, Object> extra) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(error.status(), detail);

        pd.setType(URI.create(BASE_TYPE + error.code()));
        pd.setTitle(error.title());
        pd.setInstance(URI.create(requestUri));
        pd.setProperty("timestamp", Instant.now().toString());
        pd.setProperty("code", error.code());

        extra.forEach(pd::setProperty);

        return pd;
    }
}
