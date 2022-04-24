package com.aadhar.minor.requests;

import com.aadhar.minor.models.MyUser;
import com.aadhar.minor.models.Student;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudentCreateRequest {
    @Min(value = 1)
    private int age;

    @NotNull
    private String rollNo;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Student to(){
        return Student.builder()
                .age(this.getAge())
                .name(this.getName())
                .rollNo(this.getRollNo())
                .build();
    }

    public MyUser toMyUser(){
        return MyUser.builder()
                .username(getUsername())
                .password(getPassword())
                .build();
    }
}
