package com.nityam.attendancesystem.exception;

public class AlreadyClockedInException extends  RuntimeException{

   public AlreadyClockedInException(String message){
       super(message);
   }
}
