package com.assessment.library.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.assessment.library.model.Book;
import com.assessment.library.model.BorrowedBook;
import com.assessment.library.model.Patron;
import com.assessment.library.repository.BookRepository;
import com.assessment.library.repository.BorrowedBookRepository;
import com.assessment.library.repository.PatronRepository;

import jakarta.transaction.Transactional;

@Service
public class BorrowingService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BorrowingService(BorrowedBookRepository borrowedBookRepository, BookRepository bookRepository, PatronRepository patronRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Transactional
    public BorrowedBook borrowBook(Long patronId, Long bookId) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (borrowedBookRepository.findByBookIdAndIsBorrowedTrue(bookId).isPresent()) {
            throw new RuntimeException("Book is already borrowed");
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setPatron(patron);
        borrowedBook.setBook(book);
        borrowedBook.setBorrowedAt(LocalDateTime.now());
        borrowedBook.setBorrowed(true);

        return borrowedBookRepository.save(borrowedBook);
    }

    @Transactional
    public BorrowedBook returnBook(Long bookId,Long patronId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findByPatronIdAndBookIdAndIsBorrowedTrue(bookId,patronId)
                .orElseThrow(() -> new RuntimeException("Book is not currently borrowed"));

        borrowedBook.setReturnedAt(LocalDateTime.now());
        borrowedBook.setBorrowed(false);

        return borrowedBookRepository.save(borrowedBook);
    }
}
