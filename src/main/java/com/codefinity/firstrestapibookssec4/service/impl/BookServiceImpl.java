package com.codefinity.firstrestapibookssec4.service.impl;

import com.codefinity.firstrestapibookssec4.dto.BookRequestDTO;
import com.codefinity.firstrestapibookssec4.dto.BookResponseDTO;
import com.codefinity.firstrestapibookssec4.exception.ApiException;
import com.codefinity.firstrestapibookssec4.mapper.MapperBook;
import com.codefinity.firstrestapibookssec4.model.Book;
import com.codefinity.firstrestapibookssec4.repositroy.BookRepository;
import com.codefinity.firstrestapibookssec4.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(MapperBook::modelToResponseDto)
                .toList();
    }

    @Transactional
    public BookResponseDTO createBook(BookRequestDTO book) {
        Book modelBook = MapperBook.dtoRequestToModel(book);
        Book repositoryBook = bookRepository.save(modelBook);
        return MapperBook.modelToResponseDto(repositoryBook);
    }

    @Transactional
    public BookResponseDTO updateBook(String id,BookRequestDTO book) {
        Book bookWithRepository = bookRepository.findById(id).orElseThrow(
                () -> new ApiException("Not found book by id: " + id, HttpStatus.NOT_FOUND)
        );

        bookWithRepository.setName(book.getName());
        bookWithRepository.setAuthor(book.getAuthor());
        bookWithRepository.setPrice(book.getPrice());

        Book saveBook = bookRepository.save(bookWithRepository);

        return MapperBook.modelToResponseDto(saveBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findByAuthor(String author) {
        return bookRepository.findBookByAuthor(author).stream()
                .map(MapperBook::modelToResponseDto)
                .toList();
    }

    @Transactional
    public void deleteBook(String id) {
        if(!bookRepository.existsById(id)) {
            throw new ApiException("Not found by id: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

}