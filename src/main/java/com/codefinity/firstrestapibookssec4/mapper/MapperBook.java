package com.codefinity.firstrestapibookssec4.mapper;

import com.codefinity.firstrestapibookssec4.dto.BookRequestDTO;
import com.codefinity.firstrestapibookssec4.dto.BookResponseDTO;
import com.codefinity.firstrestapibookssec4.model.Book;
import org.modelmapper.ModelMapper;

public class MapperBook {
    private static final ModelMapper mapper = new ModelMapper();

    public static Book dtoRequestToModel(BookRequestDTO dto) {
        return mapper.map(dto, Book.class);
    }

    public static BookResponseDTO modelToResponseDto(Book book) {
        return mapper.map(book, BookResponseDTO.class);
    }
}
