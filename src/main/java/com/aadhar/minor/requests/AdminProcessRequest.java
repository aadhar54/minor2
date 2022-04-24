package com.aadhar.minor.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminProcessRequest {

    @NotNull
    private int requestId;

    @NotNull
    private String requestStatus;

    private String adminComment;


}
