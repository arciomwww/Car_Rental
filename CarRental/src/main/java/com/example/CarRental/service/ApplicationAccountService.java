package com.example.CarRental.service;

import com.example.CarRental.dto.ApplicationAccountDto;
import com.example.CarRental.entity.ApplicationAccount;
import com.example.CarRental.repository.ApplicationAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAccountService {

    private final ApplicationAccountRepository applicationAccountRepository;

    @Autowired
    public ApplicationAccountService(ApplicationAccountRepository applicationAccountRepository) {
        this.applicationAccountRepository = applicationAccountRepository;
    }

    public ApplicationAccountDto createApplicationAccount(ApplicationAccountDto applicationAccountDto) {

        ApplicationAccount applicationAccount = ApplicationAccount.builder()
                .balance(applicationAccountDto.getBalance())
                .build();

        ApplicationAccount savedApplicationAccount = applicationAccountRepository.save(applicationAccount);

        return ApplicationAccountDto.builder()
                .id(savedApplicationAccount.getId())
                .balance(savedApplicationAccount.getBalance())
                .build();
    }

    public ApplicationAccountDto getApplicationAccount(Long id) {

        ApplicationAccount applicationAccount = applicationAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application Account not found"));

        return ApplicationAccountDto.builder()
                .id(applicationAccount.getId())
                .balance(applicationAccount.getBalance())
                .build();
    }

    public ApplicationAccountDto updateApplicationAccount(Long id, ApplicationAccountDto applicationAccountDto) {

        ApplicationAccount applicationAccount = applicationAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application Account not found"));

        applicationAccount.setBalance(applicationAccountDto.getBalance());

        ApplicationAccount updatedApplicationAccount = applicationAccountRepository.save(applicationAccount);

        return ApplicationAccountDto.builder()
                .id(updatedApplicationAccount.getId())
                .balance(updatedApplicationAccount.getBalance())
                .build();
    }

    public void deleteApplicationAccount(Long id) {
        applicationAccountRepository.deleteById(id);
    }
}
