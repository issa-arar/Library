package com.assessment.library.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.assessment.library.model.BorrowedBook;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByPatronIdAndIsBorrowedTrue(Long patronId);
    Optional<BorrowedBook> findByBookIdAndIsBorrowedTrue(Long bookId);
    Optional<BorrowedBook> findByPatronIdAndBookIdAndIsBorrowedTrue(Long bookId,Long patronId);

}
