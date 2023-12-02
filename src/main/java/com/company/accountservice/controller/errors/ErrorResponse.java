package com.company.accountservice.controller.errors;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

    public List<Error> error;
}
