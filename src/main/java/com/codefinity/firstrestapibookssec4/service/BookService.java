package com.codefinity.firstrestapibookssec4.service;

import com.codefinity.firstrestapibookssec4.dto.BookRequestDTO;
import com.codefinity.firstrestapibookssec4.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> findAllBooks();

    BookResponseDTO createBook(BookRequestDTO book);

    BookResponseDTO updateBook(String id,BookRequestDTO book);

    List<BookResponseDTO> findByAuthor(String author);

    void deleteBook(String id);
}
