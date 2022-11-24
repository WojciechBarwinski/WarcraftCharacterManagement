package com.WojciechBarwinski.WarcraftCharacterManagement.Controllers;

import com.WojciechBarwinski.WarcraftCharacterManagement.ControllerType;
import com.WojciechBarwinski.WarcraftCharacterManagement.DTOs.DTOFlag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class RespEntityPathCreator {

    private RespEntityPathCreator() {
    }

    public static ResponseEntity<DTOFlag> getCorrectResponseEntity(ControllerType controller, DTOFlag dto){
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path(getURL(controller))
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(location).body(dto);
    }

    private static String getURL(ControllerType controller){
        StringBuilder url = new StringBuilder();

        switch (controller){
            case AUTHOR -> url.append("/authors/");
            case HERO -> url.append("/heroes/");
            case ITEM -> url.append("/items/");
            case BOOK -> url.append("/books/");
            case RACE -> url.append("/races/");
        }
        return url.append("id").toString();
    }
}
