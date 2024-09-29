package com.example.demo.Exceptions;

public class BalanceNotSufficientException extends  Exception{
    public BalanceNotSufficientException(String message){
        super(message);
    }
}
