package com.company.accountservice.controller.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

        private String name;

        @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "is not valid")
        @NotEmpty(message = "cannot be empty")
        private String email;

        @NotBlank(message = "cannot be empty or null")
        @Pattern(regexp = "^(?=(?:.*[A-Z]){1})(?=(?:.*[0-9]){2})(?!.*[^A-Za-z0-9]).{8,12}$", message = "is not valid")
        private String password;

        private List<PhoneDTO> phones;
}
