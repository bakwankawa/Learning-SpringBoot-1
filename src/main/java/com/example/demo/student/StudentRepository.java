package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // below code is same as:
    // 1. SELECT * FROM student WHERE EMAIL = ? or
    // 2. @Query("SELECT S FROM Student s WHERE s.email = ?1?")
    // info: Student class can be accessed from everywhere because it has @Entity
    Optional<Student> findStudentByEmail(String email);
}
