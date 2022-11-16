package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String itemId) {
        super("wybrany przedmiot o id=" + itemId + " nie istnieje. Proszę wybrać inne id");
    }
}
