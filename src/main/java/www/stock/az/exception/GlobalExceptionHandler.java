package www.stock.az.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(e -> e.getField(), e -> e.getDefaultMessage() != null ? e.getDefaultMessage() : "Invalid", (a, b) -> a));
        ApiErrorResponse body = ApiErrorResponse.builder()
                .message("Validation failed")
                .code("VALIDATION_ERROR")
                .errors(errors)
                .build();
        log.warn("Validation error: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiErrorResponse> handleWebClient(WebClientResponseException ex) {
        String message = "Service error";
        String code = "DOWNSTREAM_ERROR";
        Map<String, String> errors = null;
        try {
            String bodyStr = ex.getResponseBodyAsString();
            if (bodyStr != null && !bodyStr.isBlank()) {
                ApiErrorResponse parsed = objectMapper.readValue(bodyStr, ApiErrorResponse.class);
                if (parsed.getMessage() != null) message = parsed.getMessage();
                if (parsed.getCode() != null) code = parsed.getCode();
                if (parsed.getErrors() != null) errors = parsed.getErrors();
            }
        } catch (Exception e) {
            log.debug("Could not parse downstream error body: {}", e.getMessage());
        }
        HttpStatus status;
        if (ex.getStatusCode().is5xxServerError()) {
            status = ex.getStatusCode().value() == 503 ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.BAD_GATEWAY;
            code = status == HttpStatus.SERVICE_UNAVAILABLE ? "SERVICE_UNAVAILABLE" : "BAD_GATEWAY";
        } else {
            status = HttpStatus.valueOf(ex.getStatusCode().value());
        }
        ApiErrorResponse body = ApiErrorResponse.builder()
                .message(message)
                .code(code)
                .errors(errors)
                .build();
        log.error("Downstream error: {} {}", status, message, ex);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntime(RuntimeException ex) {
        String message = ex.getMessage() != null ? ex.getMessage() : "Service temporarily unavailable";
        ApiErrorResponse body = ApiErrorResponse.builder()
                .message(message)
                .code("BAD_GATEWAY")
                .build();
        log.error("Runtime (downstream?) exception: {}", message, ex);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        ApiErrorResponse body = ApiErrorResponse.builder()
                .message("An unexpected error occurred")
                .code("INTERNAL_ERROR")
                .build();
        log.error("Unexpected exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
