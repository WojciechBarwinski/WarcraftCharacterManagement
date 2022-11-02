package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.HeroNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.RaceNotFoundException;
import com.WojciechBarwinski.WarcraftCharacterManagement.Exception.UpdateConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HeroNotFoundException.class)
    public String heroNotFound(Exception e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RaceNotFoundException.class)
    public String raceNotFound(Exception e){
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UpdateConflictException.class)
    public String updateConflict(Exception e){
        return e.getMessage();
    }
}
