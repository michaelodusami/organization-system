/**
 * Controller for managing WeeklyReview entities.
 * Provides endpoints to perform CRUD operations on WeeklyReview objects.
 * @author Michael-Andre Odusami
 * @version (v1) 2024.12.14
 */
package dev.michaelodusami.organizationsystem.weeklyreviews;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/weekly-reviews")
public class WeeklyReviewController {

    private final List<WeeklyReview> weeklyReviewRepository = new ArrayList<>(); // Temporary in-memory storage

    /**
     * Create a new WeeklyReview.
     * @param weeklyReview the WeeklyReview object to create
     * @return the created WeeklyReview
     */
    @PostMapping
    public ResponseEntity<WeeklyReview> createWeeklyReview(@RequestBody WeeklyReview weeklyReview) {
        weeklyReviewRepository.add(weeklyReview);
        return ResponseEntity.ok(weeklyReview);
    }

    /**
     * Get all WeeklyReviews.
     * @return a list of all WeeklyReviews
     */
    @GetMapping
    public ResponseEntity<List<WeeklyReview>> getAllWeeklyReviews() {
        return ResponseEntity.ok(weeklyReviewRepository);
    }

    /**
     * Get a WeeklyReview by its index.
     * @param index the index of the WeeklyReview to retrieve
     * @return the requested WeeklyReview
     */
    @GetMapping("/{index}")
    public ResponseEntity<WeeklyReview> getWeeklyReviewByIndex(@PathVariable int index) {
        if (index < 0 || index >= weeklyReviewRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(weeklyReviewRepository.get(index));
    }

    /**
     * Update a WeeklyReview by its index.
     * @param index the index of the WeeklyReview to update
     * @param updatedReview the updated WeeklyReview object
     * @return the updated WeeklyReview
     */
    @PutMapping("/{index}")
    public ResponseEntity<WeeklyReview> updateWeeklyReview(@PathVariable int index, @RequestBody WeeklyReview updatedReview) {
        if (index < 0 || index >= weeklyReviewRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        weeklyReviewRepository.set(index, updatedReview);
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * Delete a WeeklyReview by its index.
     * @param index the index of the WeeklyReview to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteWeeklyReview(@PathVariable int index) {
        if (index < 0 || index >= weeklyReviewRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        weeklyReviewRepository.remove(index);
        return ResponseEntity.ok("WeeklyReview deleted successfully.");
    }
}
