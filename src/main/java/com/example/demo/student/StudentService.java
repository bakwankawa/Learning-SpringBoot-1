package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// 1. this class must be instantiated because used in controller. Named spring bean
@Service // 2. annotation to make a spring bean
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        // this logic check if email is already used
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExist = studentRepository.existsById(studentId);
        if (!studentExist) {
            throw new IllegalStateException(
                    "Student with id " + studentId + "does not exists"
            );
        }
        studentRepository.deleteById(studentId);
    }

    // this annotation is to make the entity goes to manage all state below. So we don't need to declare sql logic
    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException(
                "student with id " + studentId + "does not exist!" // .orElse above is to check if the id exist
        ));

        // this logic is to test either the name not null and the current id name
        // isn't same with the input
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        // this logic is to test either the email not null and the current id name
        // isn't same with the input
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
            // this logic to find that does the email already taken
            if (studentByEmail.isPresent()) {
                throw new IllegalStateException(
                        "Student with email " + email + "is already exist!"
                );
            }
            student.setEmail(email);
        }
    }
}
