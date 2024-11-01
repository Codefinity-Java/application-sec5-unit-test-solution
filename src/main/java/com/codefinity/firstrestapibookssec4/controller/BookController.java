package com.codefinity.firstrestapibookssec4.controller;

import com.codefinity.firstrestapibookssec4.dto.BookRequestDTO;
import com.codefinity.firstrestapibookssec4.dto.BookResponseDTO;
import com.codefinity.firstrestapibookssec4.service.BookService;
import com.codefinity.firstrestapibookssec4.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@Tag(name="Books", description = "API for managing books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get the list of all books")
    @ApiResponse(responseCode = "200", description = "Books successfully retrieved")
    public List<BookResponseDTO> findAllBooks() {
        return bookService.findAllBooks();
    }

    @PostMapping
    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "200", description = "Book successfully created")
    public BookResponseDTO createBook(@RequestBody BookRequestDTO book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully updated"),
            @ApiResponse(responseCode = "404", description = "ID not found")
    })
    public BookResponseDTO updateBook(
            @Parameter(description = "ID of the book to be updated")
            @PathVariable String id,
            @RequestBody BookRequestDTO book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ID of the book to be deleted"),
            @ApiResponse(responseCode = "404", description = "ID not found")
    })
    public void deleteBooks(
            @Parameter(description = "ID of the book to be deleted")
            @PathVariable String id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/author/{author}")
    @Operation(summary = "Get the list of all books by author")
    @ApiResponse(responseCode = "200", description = "Books successfully retrieved")
    public List<BookResponseDTO> findByAuthor(
            @Parameter(description = "Author name parameter")
            @PathVariable String author
    ) {
       return bookService.findByAuthor(author);
    }

}


