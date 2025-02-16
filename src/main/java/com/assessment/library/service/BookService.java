package com.assessment.library.service;


import com.assessment.library.dto.request.BookPaginationRequest;
import com.assessment.library.model.Book;
import com.assessment.library.repository.BookRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllbooks(BookPaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getSize());

        Page<Book> bookPage = bookRepository.findByIsbnContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            request.getIsbn() != null ? request.getIsbn() : "",
            request.getTitle() != null ? request.getTitle() : "",
            request.getAuthor() != null ? request.getAuthor() : "",
            pageable
        );

        return bookPage;
    }

    @Cacheable(value = "books", key = "#id") 
    public Optional<Book> getbookById(Long id) {
        return bookRepository.findById(id);
    }


    public Book createbook(Book book) {
        return bookRepository.save(book);
    }

    @CachePut(value = "books", key = "#id")
    public Book updateBook(Long id, Book updatedbook) {
        return bookRepository.findById(id)
                .map(existingbook -> {
                    existingbook.setTitle(updatedbook.getTitle());
                    existingbook.setAuthor(updatedbook.getAuthor());
                    existingbook.setIsbn(updatedbook.getIsbn());
                    existingbook.setPublicationYear(updatedbook.getPublicationYear());

                    return bookRepository.save(existingbook);
                })
                .orElseThrow(() -> new RuntimeException("book not found with id: " + id));
    }

    @CacheEvict(value = "books", key = "#id")
    public void deletebook(Long id) {
        bookRepository.deleteById(id);
    }
}

