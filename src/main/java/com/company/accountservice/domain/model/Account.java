package com.company.accountservice.domain.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Account {

    private String accountId;
    private String name;
    private String email;
    private String password;
    private ZonedDateTime created;
    private ZonedDateTime lastLogin;
    private boolean isActive;
    private List<Phone> phones;
}
