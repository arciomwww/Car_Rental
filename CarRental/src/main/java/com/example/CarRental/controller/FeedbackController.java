package com.example.CarRental.controller;

import com.example.CarRental.dto.FeedbackDto;
import com.example.CarRental.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/feedback")
@Validated
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackDto feedbackDto) {
        try {
            feedbackService.createFeedback(feedbackDto);
            return ResponseEntity.ok("Feedback created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackDto> updateFeedback(@Valid @PathVariable Long id, @RequestBody FeedbackDto feedbackDto) {
        FeedbackDto updatedFeedback = feedbackService.updateFeedback(id, feedbackDto);
        return new ResponseEntity<>(updatedFeedback, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/read/all")
    public ResponseEntity<Page<FeedbackDto>> getAllFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeedbackDto> feedbackPage = feedbackService.getAllFeedback(pageable);
        return new ResponseEntity<>(feedbackPage, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id) {
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
        return new ResponseEntity<>(feedbackDto, HttpStatus.OK);
    }
}
