package com.aadhar.minor.responses;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminProcessRequestResponse {

    @NotNull
    private Integer requestId;

    @NotNull
    private String requestStatus;

    private String adminComment;

}
