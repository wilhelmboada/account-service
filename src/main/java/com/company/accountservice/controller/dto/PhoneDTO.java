package com.company.accountservice.controller.dto;

import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private long number;
    private String cityCode;
    private String countryCode;
}
