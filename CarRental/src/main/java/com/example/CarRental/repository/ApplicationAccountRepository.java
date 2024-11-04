package com.example.CarRental.repository;

import com.example.CarRental.entity.ApplicationAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAccountRepository extends JpaRepository<ApplicationAccount, Long> {
}
