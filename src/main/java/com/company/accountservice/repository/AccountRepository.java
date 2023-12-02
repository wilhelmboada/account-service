package com.company.accountservice.repository;

import com.company.accountservice.repository.data.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {

    Optional<AccountEntity> findByEmail(String email);
}
