package com.example.assignnumbertoclient.client;

public class ClientException extends RuntimeException{

    public ClientException(){
        super();
    }

    public ClientException(String message){
        super(message);
    }
}
