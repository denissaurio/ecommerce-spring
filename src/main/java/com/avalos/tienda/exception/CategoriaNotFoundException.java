package com.avalos.tienda.exception;

public class CategoriaNotFoundException extends RuntimeException{
    public CategoriaNotFoundException(String mensaje){
        super(mensaje);
    }
}
