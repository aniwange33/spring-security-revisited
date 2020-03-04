package com.example.demo;

import java.util.Comparator;
import java.util.Objects;

public class Student implements Comparable<Student> {
    private final int studentId;
    private final String studentName;
    public  static final Comparator<Student> COMPARABLE = Comparator.comparing(Student::hashCode);

    public Student(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
    @Override
    public String toString() {
        return "{"
                +studentId+ " "
                +studentName
        +"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return studentId == student.studentId &&
                Objects.equals(studentName, student.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, studentName);
    }

    @Override
    public int compareTo(Student o) {
        return COMPARABLE.compare(o, this);
    }
}
