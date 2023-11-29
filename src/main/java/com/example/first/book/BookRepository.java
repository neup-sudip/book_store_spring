package com.example.first.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBySlug(String slug);

    @Query(value = "SELECT * FROM books b WHERE b.title ~* :searchTerm OR b.author ~* :searchTerm OR b.genre ~* :searchTerm LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Book> searchBooks(String searchTerm, int offset, int limit);

    @Query(value = "SELECT COUNT(*) FROM books b WHERE b.title ~* :searchTerm OR b.author ~* :searchTerm OR b.genre ~* :searchTerm", nativeQuery = true)
    long countBooks(String searchTerm);
}
