package com.techlab.ecommerce.exception;

public class StockInsuficianteException extends RuntimeException {
    public StockInsuficianteException(String mensaje) {
        super(mensaje);
    }

}
