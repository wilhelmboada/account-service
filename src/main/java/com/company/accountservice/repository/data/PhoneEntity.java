package com.company.accountservice.repository.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phones")
public class PhoneEntity {
    @Id
    private long number;

    @Column(name = "cityCode", nullable = false)
    private String cityCode;

    @Column(name = "countryCode", nullable = false)
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity user;
}
