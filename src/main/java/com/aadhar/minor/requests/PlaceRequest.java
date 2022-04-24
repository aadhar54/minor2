package com.aadhar.minor.requests;

import com.aadhar.minor.models.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PlaceRequest {

    @NotNull
    private String requestType;

    @NotNull
    private Integer bookId;

    public Request to(Student student){
        return Request.builder()
                .externalRequestId(UUID.randomUUID().toString())
                .requestType(RequestType.valueOf(this.getRequestType()))
                .requestStatus(RequestStatus.PENDING)
                .student(student)
                .book(Book.builder().id(this.getBookId()).build())
                .build();
    }

}
