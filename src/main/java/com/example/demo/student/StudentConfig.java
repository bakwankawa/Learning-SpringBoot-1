package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    // make Bean because it will be instantiated from other class
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) { // inject to StudentRepository
        return args -> {
            Student kawa = new Student(    // call the student class from here
                    "Kawa",
                    "mahatmakawa@gmail.com",
                    LocalDate.of(2000, 5, 29)
            );
            Student myesha = new Student(    // call the student class from here
                    "myesha",
                    "afsheen@gmail.com",
                    LocalDate.of(2012, 6, 22)
            );

            // save to our databases
            repository.saveAll(
                    List.of(kawa, myesha)
            );
        };
    }
}
