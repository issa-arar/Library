package com.assessment.library.repository;


import com.assessment.library.model.Patron;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Page<Patron> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

