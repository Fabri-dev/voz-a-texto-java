package org.example.Excepciones;

public class UrlInvalidaException extends Exception{
    //atributos
    private String message;


    //constructores
    public UrlInvalidaException() {
        message="Url recibida es invalida, debe empezar con http o https";
    }
    //get y set

    @Override
    public String getMessage() {
        return message;
    }

    //metodos
}
