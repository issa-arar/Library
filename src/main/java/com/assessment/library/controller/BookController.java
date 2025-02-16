package com.assessment.library.controller;

import com.assessment.library.dto.ApiResponse;
import com.assessment.library.dto.PaginationApiResponse;
import com.assessment.library.dto.request.BookPaginationRequest;
import com.assessment.library.model.Book;
import com.assessment.library.service.BookService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<PaginationApiResponse<Page<Book>>> getAllbooks( @Validated @ModelAttribute BookPaginationRequest request) {

        Page<Book> books = bookService.getAllbooks(request);

        PaginationApiResponse<Page<Book>> response = new PaginationApiResponse<>(
                "books retrieved successfully",
                books,
                true,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getbookById(@PathVariable Long id) {
            Optional<Book> book = bookService.getbookById(id);
            if(book.isEmpty()){
                throw new RuntimeException("Book Not Found");
            }
            return ResponseEntity.ok(new ApiResponse("book Fethed!",book,true));        
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createbook(@Valid @RequestBody Book book) {

        return ResponseEntity.ok(new ApiResponse("book created Successfully!",bookService.createbook(book),true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updatebook(@PathVariable Long id, @Valid @RequestBody Book book) {

        book = bookService.updateBook(id, book);
        return ResponseEntity.ok(new ApiResponse("book Updated Successfully!",book,true));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletebook(@PathVariable Long id) {
        bookService.deletebook(id);
        return ResponseEntity.ok(new ApiResponse("book Deleted Successfully!",true));
    }
}
