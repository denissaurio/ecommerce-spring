package com.avalos.tienda.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException (String mensaje){
        super(mensaje);
    }
}
