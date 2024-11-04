package com.example.CarRental.controller;

import com.example.CarRental.dto.TransactionDto;
import com.example.CarRental.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
@Validated
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@Valid @RequestBody TransactionDto transactionDto) {
        transactionService.transferFunds(transactionDto);
        return ResponseEntity.ok("Перевод выполнен успешно!");
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/deposit")
    public ResponseEntity<?> depositToUserAccount(@Valid @RequestBody TransactionDto transactionDto) {
        transactionService.depositToAccount(transactionDto);
        return ResponseEntity.ok("Счет пополнен успешно");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("read/all")
    public ResponseEntity<Page<TransactionDto>> getAllTransactions(Pageable pageable) {
        Page<TransactionDto> transactions = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(transactions);
    }
}
