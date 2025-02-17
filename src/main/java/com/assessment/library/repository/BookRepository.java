package com.assessment.library.repository;


import com.assessment.library.model.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByIsbnContainingIgnoreCaseOrTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
        String isbn, String title, String author, Pageable pageable
    );
}

