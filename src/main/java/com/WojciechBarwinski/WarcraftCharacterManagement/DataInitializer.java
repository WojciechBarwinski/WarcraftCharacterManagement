package com.WojciechBarwinski.WarcraftCharacterManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent>{

    TestRepository testRepository;

    public DataInitializer(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        testRepository.save(new TestEntity("Sylvanas", "Windrunner", "Horda"));
        testRepository.save(new TestEntity("Thrall", "---", "Horda"));
        testRepository.save(new TestEntity("Tyrande", "Whisperwind", "Aliance"));
    }
}
