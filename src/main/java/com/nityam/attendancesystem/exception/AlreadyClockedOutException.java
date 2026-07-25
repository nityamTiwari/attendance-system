package com.nityam.attendancesystem.exception;

public class AlreadyClockedOutException extends  RuntimeException{

    public  AlreadyClockedOutException(String message){
        super(message);
    }

}
