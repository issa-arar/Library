package com.assessment.library;


import com.assessment.library.controller.BorrowingController;
import com.assessment.library.dto.ApiResponse;
import com.assessment.library.model.Book;
import com.assessment.library.model.BorrowedBook;
import com.assessment.library.model.Patron;
import com.assessment.library.service.BorrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingControllerTest {

    @InjectMocks
    private BorrowingController borrowingController; 

    @Mock
    private BorrowingService borrowingService;

    private Patron patron;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patron = new Patron();
        patron.setId(1L);
        patron.setName("patron one");
        patron.setEmail("patron@one.com");

        book = new Book();
        book.setId(1L);
        book.setTitle("DDD");
        book.setAuthor("Eric");
        book.setIsbn("123456789");
    }

    @Test
    void testBorrowBook_Success() {
    BorrowedBook borrowedBook = new BorrowedBook();
    borrowedBook.setId(1L);
    borrowedBook.setBook(book);
    borrowedBook.setPatron(patron);
    borrowedBook.setBorrowed(true);

    when(borrowingService.borrowBook(1L, 1L)).thenReturn(borrowedBook);

    ResponseEntity<ApiResponse<BorrowedBook>> response = borrowingController.borrowBook(1L, 1L);

    assertEquals(200, response.getStatusCodeValue());
    assertNotNull(response.getBody());
    assertEquals("Book borrowed successfully!", response.getBody().getMessage());
    assertTrue(response.getBody().isSuccess());

    verify(borrowingService, times(1)).borrowBook(1L, 1L);
}

    @Test
    void testBorrowBook_BookNotAvailable() {
        when(borrowingService.borrowBook(1L, 1L)).thenThrow(new RuntimeException("Book is already borrowed"));
        Exception exception = assertThrows(RuntimeException.class, () -> borrowingController.borrowBook(1L, 1L));
        assertEquals("Book is already borrowed", exception.getMessage());
        verify(borrowingService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    void testReturnBook_Success() {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(1L);
        borrowedBook.setBook(book);
        borrowedBook.setPatron(patron);
        borrowedBook.setBorrowed(true);
                when(borrowingService.returnBook(1L, 1L))
            .thenReturn(borrowedBook);
    
        ResponseEntity<ApiResponse<BorrowedBook>> response = borrowingController.returnBook(1L, 1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Book returned successfully!", response.getBody().getMessage());
        assertTrue(response.getBody().isSuccess());
        verify(borrowingService, times(1)).returnBook(1L, 1L);
    }
    

    @Test
    void testReturnBook_NotBorrowed() {
        when(borrowingService.returnBook(1L, 1L)).thenThrow(new RuntimeException("Book was not borrowed"));
        Exception exception = assertThrows(RuntimeException.class, () -> borrowingController.returnBook(1L, 1L));
        assertEquals("Book was not borrowed", exception.getMessage());
        verify(borrowingService, times(1)).returnBook(1L, 1L);
    }
}

