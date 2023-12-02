package com.company.accountservice.repository.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {
	@Id
	private String accountId;

	@Column(name = "name")
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "created", nullable = false)
	private ZonedDateTime created;

	@Column(name = "lastLogin", nullable = false)
	private ZonedDateTime lastLogin;

	@Column(name = "isActive", nullable = false)
	private boolean isActive;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "account_id")
	private List<PhoneEntity> phones;

}
