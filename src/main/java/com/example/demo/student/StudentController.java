package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController    // this annotation to make code below serve API
@RequestMapping(path = "API/v1/student")
public class StudentController {

    private final StudentService studentService;    // 1. reference to StudentService class

    // 3. we need to instance this to be used
    @Autowired  // 4. this annotation will instantiate studentService object to inject this constructor
    public StudentController(StudentService studentService) {   // 2. pass reference to controller
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    /* the example of post request is:
    {
        "name": "kawa",
        "email": "kawa@gmail.com",
        "dob": "2000-29-05"
    } */
    // payload is a name for client to make a post request to db
    @PostMapping
    public void registerNewStudent(@RequestBody Student student) { // this annotation means we take req body and mapping
                                                                    // to the student
        studentService.addNewStudent(student);
    }

    // the example of delete request is: http://localhost:8080/API/v1/student/1
    @DeleteMapping(path = "{studentId}") // path variable is declared directly from url path
    public void deleteStudent(@PathVariable("studentId") Long studentId) { // this @PathVariable is to grab path above
        studentService.deleteStudent(studentId);
    }


    // the example of put request is: http://localhost:8080/API/v1/student/1?name=Kawaa&email=Kawaa@gmail.com
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name, // this param is not must require
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }
}
