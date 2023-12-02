package com.company.accountservice.controller.errors;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Error {
    private ZonedDateTime timestamp;
    private int code;
    private String detail;
}
