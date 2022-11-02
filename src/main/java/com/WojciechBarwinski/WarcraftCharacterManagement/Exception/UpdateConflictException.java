package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

public class UpdateConflictException extends RuntimeException{

    public UpdateConflictException(String message) {
        super(message);
    }
}
