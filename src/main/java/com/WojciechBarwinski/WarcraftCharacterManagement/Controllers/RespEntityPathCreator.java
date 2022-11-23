package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.ControllerURL;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RespEntityPathCreator {

    private RespEntityPathCreator() {
    }

    public static ResponseEntity<DTOFlag> getCorrectResponseEntaty(ControllerURL controller, DTOFlag dto){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(getURL(controller))
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(location).body(dto);
    }

    private static String getURL(ControllerURL controller){
        String url;
        switch (controller){
            case AUTHOR -> url = "/authors/{id}";
            case HERO -> url = "/heroes/{id}";
            case ITEM -> url = "/items/{id}";
            case BOOK -> url = "/books/{id}";
            case RACE -> url = "/races/{id}";
            default -> url = "";
        }
        return url;
    }
}
