package www.stock.az.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Validation failed",
                        (existing, replacement) -> existing
                ));
        
        errors.put("message", "Validation xətası");
        errors.put("errors", fieldErrors);
        
        log.warn("Validation error: {}", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, Object>> handleWebClientException(WebClientResponseException ex) {
        Map<String, Object> error = new HashMap<>();
        
        String message = "Xidmət xətası";
        if (ex.getResponseBodyAsString() != null && !ex.getResponseBodyAsString().isEmpty()) {
            try {
                // Try to extract error message from response body
                message = ex.getResponseBodyAsString();
            } catch (Exception e) {
                log.warn("Could not parse error response body", e);
            }
        }
        
        error.put("message", message);
        error.put("status", ex.getStatusCode().value());
        
        log.error("WebClient error: Status {}, Message: {}", ex.getStatusCode(), message, ex);
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<Map<String, Object>> handleMyException(MyException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("code", ex.getCode());
        
        log.error("Custom exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(ex.getCode() != null ? ex.getCode() : HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage() != null ? ex.getMessage() : "Xəta baş verdi");
        
        log.error("Runtime exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Gözlənilməz xəta baş verdi");
        
        log.error("Unexpected exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

