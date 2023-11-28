package com.example.first.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews WHERE book_id = :bookId", nativeQuery = true)
    List<Review> getReviewByBookId(long bookId);

    @Query(value = "SELECT book_id, AVG(rating) AS overall_rating, COUNT(*) AS num_reviews FROM reviews WHERE book_id IN :bookIds AND rating > 0 GROUP BY book_id HAVING overall_rating IS NOT NULL", nativeQuery = true)
    List<Object[]> getOverallRatingAndNumReviewsForBooks(List<Long> bookIds);
}
