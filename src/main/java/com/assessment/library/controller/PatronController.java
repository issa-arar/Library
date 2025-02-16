package com.assessment.library.controller;

import com.assessment.library.dto.ApiResponse;
import com.assessment.library.dto.PaginationApiResponse;
import com.assessment.library.dto.request.PatronPaginationRequest;
import com.assessment.library.model.Patron;
import com.assessment.library.service.PatronService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public ResponseEntity<PaginationApiResponse<Page<Patron>>> getAllPatrons( @Validated @ModelAttribute PatronPaginationRequest request) {

        Page<Patron> patrons = patronService.getAllPatrons(request);

        PaginationApiResponse<Page<Patron>> response = new PaginationApiResponse<>(
                "Patrons retrieved successfully",
                patrons,
                true,
                patrons.getNumber(),
                patrons.getSize(),
                patrons.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> getPatronById(@PathVariable Long id) {
            Optional<Patron> patron = patronService.getPatronById(id);
            if(patron.isEmpty()){
                throw new RuntimeException("Patron Not Found");
            }
            return ResponseEntity.ok(new ApiResponse("Patron Fethed!",patron,true));        
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Patron>> createPatron(@Valid @RequestBody Patron patron) {

        return ResponseEntity.ok(new ApiResponse("Patron created Successfully!",patronService.createPatron(patron),true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Patron>> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patron) {

        patron = patronService.updatePatron(id, patron);
        return ResponseEntity.ok(new ApiResponse("Patron Updated Successfully!",patron,true));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok(new ApiResponse("Patron Deleted Successfully!",true));
    }
}
