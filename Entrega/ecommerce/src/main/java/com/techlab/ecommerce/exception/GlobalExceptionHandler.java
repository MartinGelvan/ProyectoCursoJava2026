package com.techlab.ecommerce.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> manejarValidacion(
        MethodArgumentNotValidException ex) {
        Map<String,String> errores = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errores.put(error.getField(),error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }


    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> manejarProductoNoEncontrado(
        ProductoNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    
    
    @ExceptionHandler(CategoriaNoEncontradaException.class)
    public ResponseEntity<String> manejarCategoriaNoEncontrada(
        CategoriaNoEncontradaException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }


    @ExceptionHandler(CategoriaNombreInvalidoException.class)
    public ResponseEntity<String> manejarCategoriaNombreInvalido(
        CategoriaNombreInvalidoException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
 
    @ExceptionHandler(StockInsuficianteException.class)
    public ResponseEntity<String> manejarStockInsuficiente(
        StockInsuficianteException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    

    @ExceptionHandler(CarritoNoEncontradoException.class)
    public ResponseEntity<String> manejarCarritoNoEncontrado(
        CarritoNoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    
    
}

    
