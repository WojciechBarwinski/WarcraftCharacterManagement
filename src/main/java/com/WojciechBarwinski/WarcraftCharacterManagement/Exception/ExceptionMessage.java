package com.WojciechBarwinski.WarcraftCharacterManagement.Exception;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Properties;

@Component
public class ExceptionMessage {
    private final Properties prop = new Properties();

    @SneakyThrows
    public String getMessage(String exceptionKey){
        FileReader reader = new FileReader("src/main/resources/ex_mess.properties");
        prop.load(reader);
        return prop.getProperty(exceptionKey);
    }
}
