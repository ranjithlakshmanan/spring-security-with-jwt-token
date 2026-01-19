package org.demo.springsecurity.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {

    private String name;
    private int marks;

    public Student() {}

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

}
