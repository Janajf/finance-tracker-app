package com.francis.janaj.financetracker.domain.account.repos;

import com.francis.janaj.financetracker.domain.account.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Integer> {

}
