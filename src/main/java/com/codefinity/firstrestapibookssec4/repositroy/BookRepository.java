package com.codefinity.firstrestapibookssec4.repositroy;

import com.codefinity.firstrestapibookssec4.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = "SELECT b FROM book b WHERE b.author = :author")
    List<Book> findBookByAuthor(String author);
}
