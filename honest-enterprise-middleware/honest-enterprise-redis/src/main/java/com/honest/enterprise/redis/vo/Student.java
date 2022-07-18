package com.honest.enterprise.redis.vo;



import lombok.Data;

@Data
public class Student {


    private String firstName;


    private String lastName;


    private Integer age;

    public Student(){

    }
    public Student(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
