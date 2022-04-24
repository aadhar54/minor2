package com.aadhar.minor.requests;

import com.aadhar.minor.models.Admin;
import com.aadhar.minor.models.MyUser;
import lombok.*;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminCreateRequest {

    @Size(min = 4,max = 20)
    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Admin to(){
        return Admin.builder()
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }

    public MyUser toMyUser(){
        return MyUser.builder()
                .username(getUsername())
                .password(getPassword())
                .build();
    }
}
