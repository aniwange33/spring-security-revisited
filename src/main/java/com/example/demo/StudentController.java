package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static List<Student> STUDENTS = List.of(
            new Student(1, " James Bond"),
            new Student(2, "Mike Atorba"),
            new Student(2, "Amos Amine")
            );
    @GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId") int studentId){
        return STUDENTS.stream()
                .filter(e ->studentId == e.getStudentId() )
                .findFirst()
                .orElseThrow(()->
                        new IllegalArgumentException("Student with " + studentId +" does not exist"));
    }
}
