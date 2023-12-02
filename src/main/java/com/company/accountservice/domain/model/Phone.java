package com.company.accountservice.domain.model;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Phone {

    private Long id;
    private long number;
    private String cityCode;
    private String countryCode;
}
