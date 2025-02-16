package com.assessment.library.service;


import com.assessment.library.dto.request.PatronPaginationRequest;
import com.assessment.library.model.Patron;
import com.assessment.library.repository.PatronRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public Page<Patron> getAllPatrons(PatronPaginationRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getSize());
        return (request.getName() != null && !request.getName().isEmpty()) 
            ? patronRepository.findByNameContainingIgnoreCase(request.getName(), pageable) 
            : patronRepository.findAll(pageable);
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, Patron updatedPatron) {
        return patronRepository.findById(id)
                .map(existingPatron -> {
                    existingPatron.setName(updatedPatron.getName());
                    existingPatron.setEmail(updatedPatron.getEmail());
                    existingPatron.setContactInfo(updatedPatron.getContactInfo());
                    return patronRepository.save(existingPatron);
                })
                .orElseThrow(() -> new RuntimeException("Patron not found with id: " + id));
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}

