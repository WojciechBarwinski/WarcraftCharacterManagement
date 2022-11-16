package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

public class HeroNotFoundException extends RuntimeException{

    public HeroNotFoundException() {
        super("Musisz podać ID bohatera");
    }

    public HeroNotFoundException(String idOrName){
        super("wybrana postać o id/name=" + idOrName + " nie istnieje. Proszę wybrać istniejącą postać");
    }
}
