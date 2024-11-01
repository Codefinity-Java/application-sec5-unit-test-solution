package com.codefinity.firstrestapibookssec4.service;

import com.codefinity.firstrestapibookssec4.dto.BookRequestDTO;
import com.codefinity.firstrestapibookssec4.dto.BookResponseDTO;
import com.codefinity.firstrestapibookssec4.exception.ApiException;
import com.codefinity.firstrestapibookssec4.model.Book;
import com.codefinity.firstrestapibookssec4.repositroy.BookRepository;
import com.codefinity.firstrestapibookssec4.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //----findAllBooks----

    @Test
    void testFindAllBooks_whenBooksExist_shouldReturnListOfBookResponseDTO() {
        Book modelBook = new Book();
        modelBook.setId("1");
        modelBook.setName("Sample Book");
        modelBook.setAuthor("Author Name");
        modelBook.setPrice("10.99");

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId("1");
        responseDTO.setName("Sample Book");
        responseDTO.setAuthor("Author Name");
        responseDTO.setPrice("10.99");

        when(bookRepository.findAll()).thenReturn(List.of(modelBook));

        List<BookResponseDTO> expectedResponse = List.of(responseDTO);

        List<BookResponseDTO> result = bookService.findAllBooks();

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        assertEquals(expectedResponse.size(), result.size());

        verify(bookRepository).findAll();
    }

    @Test
    void testFindAllBooks_whenNoBooksExist_shouldReturnEmptyList() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<BookResponseDTO> result = bookService.findAllBooks();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository).findAll();
    }

    //----createBook----

    @Test
    void testCreateBook_whenValidBook_shouldReturnBookResponseDTO() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setName("Sample Book");
        bookRequestDTO.setAuthor("Author Name");
        bookRequestDTO.setPrice("10.99");

        Book modelBook = new Book();
        modelBook.setId("1");
        modelBook.setName("Sample Book");
        modelBook.setAuthor("Author Name");
        modelBook.setPrice("10.99");

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId("1");
        responseDTO.setName("Sample Book");
        responseDTO.setAuthor("Author Name");
        responseDTO.setPrice("10.99");


        when(bookRepository.save(any(Book.class))).thenReturn(modelBook);

        BookResponseDTO result = bookService.createBook(bookRequestDTO);

        assertNotNull(result);
        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getName(), result.getName());
        assertEquals(responseDTO.getAuthor(), result.getAuthor());
        assertEquals(responseDTO.getPrice(), result.getPrice());

        verify(bookRepository).save(any(Book.class));
    }

    //----updateBook----

    @Test
    void testUpdateBook_whenBookExists_shouldUpdateAndReturnBook() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setName("Updated Name");
        bookRequestDTO.setAuthor("Updated Author");
        bookRequestDTO.setPrice("150");

        Book bookWithRepository = new Book();
        bookWithRepository.setId("1");
        bookWithRepository.setName("Original Name");
        bookWithRepository.setAuthor("Original Author");
        bookWithRepository.setPrice("100");

        Book updateBook = new Book();
        updateBook.setId("1");
        updateBook.setName("Updated Name");
        updateBook.setAuthor("Updated Author");
        updateBook.setPrice("150");

        when(bookRepository.findById("1")).thenReturn(Optional.of(bookWithRepository));
        when(bookRepository.save(bookWithRepository)).thenReturn(updateBook);

        BookResponseDTO result = bookService.updateBook("1", bookRequestDTO);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals("150", result.getPrice());

        verify(bookRepository).findById("1");
        verify(bookRepository).save(bookWithRepository);
    }

    @Test
    void testUpdateBook_whenBookNotFound_shouldThrowApiException() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setName("Updated Name");
        bookRequestDTO.setAuthor("Updated Author");
        bookRequestDTO.setPrice("150");

        String idTest = "999";

        when(bookRepository.findById(idTest)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows(ApiException.class, () -> {
            bookService.updateBook(idTest, bookRequestDTO);
        });

        assertEquals("Not found book by id: " + idTest, apiException.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, apiException.getHttpStatus());

        verify(bookRepository, never()).save(any(Book.class));
    }

    //----findByAuthor----

    @Test
    void testFindByAuthor_whenBooksExist_shouldReturnListOfBookResponseDTO() {
        Book modelBook = new Book();
        modelBook.setId("1");
        modelBook.setName("Sample Book");
        modelBook.setAuthor("Author Name");
        modelBook.setPrice("10.99");

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId("1");
        responseDTO.setName("Sample Book");
        responseDTO.setAuthor("Author Name");
        responseDTO.setPrice("10.99");

        when(bookRepository.findBookByAuthor("Author Name")).thenReturn(List.of(modelBook));

        List<BookResponseDTO> expectedResponse = List.of(responseDTO);

        List<BookResponseDTO> result = bookService.findByAuthor("Author Name");

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());
        assertEquals(expectedResponse, result);

        verify(bookRepository).findBookByAuthor("Author Name");
    }

    @Test
    void testFindByAuthor_whenNoBooksExist_shouldReturnEmptyList() {
        when(bookRepository.findBookByAuthor("Nonexistent Author")).thenReturn(Collections.emptyList());

        List<BookResponseDTO> result = bookService.findByAuthor("Nonexistent Author");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository).findBookByAuthor("Nonexistent Author");
    }

    //----deleteBook----

    @Test
    void testDeleteBook_whenBookExists_shouldDeleteBook() {
        String bookId = "1";

        when(bookRepository.existsById(bookId)).thenReturn(true);

        bookService.deleteBook(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void testDeleteBook_whenBookDoesNotExist_shouldThrowApiException() {
        String bookId = "1";

        when(bookRepository.existsById(bookId)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> {
            bookService.deleteBook(bookId);
        });

        assertEquals("Not found by id: " + bookId, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());

        verify(bookRepository, never()).deleteById(bookId);
    }
}
