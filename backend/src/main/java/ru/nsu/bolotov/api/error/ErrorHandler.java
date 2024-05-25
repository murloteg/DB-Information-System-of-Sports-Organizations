package ru.nsu.bolotov.api.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nsu.bolotov.model.dto.error.ErrorDto;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleInternalServerError() {
        return ResponseEntity.internalServerError()
                .body(
                        new ErrorDto("Запрос не может быть выполнен", LocalDateTime.now())
                );
    }
}
