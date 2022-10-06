package com.WojciechBarwinski.WarcraftCharacterManagement.samples;


import com.WojciechBarwinski.WarcraftCharacterManagement.samples.TestEntity;
import com.WojciechBarwinski.WarcraftCharacterManagement.samples.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping("/")
    public String hello(){
        return "Hello world";
    }

    @GetMapping("/1")
    public List<TestEntity> test(){
        return testRepository.findAll();
    }

    @PostMapping("/put")
    public ResponseEntity addCharacter(@RequestBody TestEntity entity){
        testRepository.save(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
