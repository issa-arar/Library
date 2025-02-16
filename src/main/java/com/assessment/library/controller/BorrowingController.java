package com.assessment.library.controller;


import com.assessment.library.dto.ApiResponse;
import com.assessment.library.model.BorrowedBook;
import com.assessment.library.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowedBook>> borrowBook(@PathVariable Long patronId, @PathVariable Long bookId) {
        BorrowedBook borrowedBook = borrowingService.borrowBook(patronId, bookId);
        return ResponseEntity.ok(new ApiResponse<>("Book borrowed successfully!", borrowedBook, true));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<BorrowedBook>> returnBook(@PathVariable Long bookId,@PathVariable Long patronId) {
        BorrowedBook returnedBook = borrowingService.returnBook(bookId,patronId);
        return ResponseEntity.ok(new ApiResponse<>("Book returned successfully!", returnedBook, true));
    }
}

