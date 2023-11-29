package com.example.first.review;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews WHERE book_id = :bookId", nativeQuery = true)
    List<Review> getReviewByBookId(long bookId);

    @Query(value = "SELECT * FROM reviews WHERE user_id = :userId LIMIT 1", nativeQuery = true)
    Review getReviewByUserId(long userId);

    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM reviews WHERE book_id IN :bookIds AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForBooks(List<Long> bookIds);

    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM reviews WHERE book_id = :bookId AND rating > 0 GROUP BY book_id HAVING AVG(rating) IS NOT NULL", nativeQuery = true)
    List<Map<String, Object>> getOverallRatingAndNumReviewsForSingleBook(long bookId);
}
