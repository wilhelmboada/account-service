package com.company.accountservice.controller.dto;

import lombok.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class AccountResponseDTO {

        private String id;
        private String name;
        private String email;
        private ZonedDateTime created;
        private ZonedDateTime lastLogin;
        private String token;
        private boolean isActive;
        private List<PhoneDTO> phones;
}
