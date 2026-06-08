package com.avalos.tienda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<?> manejarProductoNoEncontrado(ProductoNotFoundException e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("codigo", 404, "mensaje", e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidationException(MethodArgumentNotValidException e){
        List<FieldError> errores = e.getBindingResult().getFieldErrors();
        HashMap<String, String> mensajes = new HashMap<>();
        for(FieldError error : errores){
            mensajes.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", 400, "mensaje", "Datos inválidos", "errores", mensajes));
    }
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<?> manejarCategoriaNoEncontrada(CategoriaNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("codigo", 404, "mensaje", e.getMessage()));
    }
    @ExceptionHandler(UsedEmailException.class)
    public ResponseEntity<?> handleUsedEmail(UsedEmailException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("codigo", 409, "mensaje", e.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("codigo", 404, "mensaje:", e.getMessage()));
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPassword(InvalidPasswordException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("codigo", 401, "mensaje", e.getMessage()));
    }
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<?> handleStockInsuficiente(StockInsuficienteException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("codigo", 404, "mensaje", e.getMessage()));
    }
}
