package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

public class HeroNotFoundException extends RuntimeException{

    public HeroNotFoundException(String message){
        super(message);
    }
}
