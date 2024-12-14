package dev.michaelodusami.organizationsystem;

import org.junit.jupiter.api.Test;

import dev.michaelodusami.organizationsystem.weeklyreviews.WeeklyReview;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for WeeklyReview
 */
public class WeeklyReviewTest {

    @Test
    public void testDefaultConstructor() {
        WeeklyReview weeklyReview = new WeeklyReview();

        assertNotNull(weeklyReview.getCreatedAt(), "CreatedAt should not be null");
        assertNotNull(weeklyReview.getUpdatedAt(), "UpdatedAt should not be null");
        assertEquals("This is the start of your weekly review.", weeklyReview.getReview(), "Default review text is incorrect");

        LocalDateTime now = LocalDateTime.now();
        assertTrue(weeklyReview.getCreatedAt().isBefore(now.plusSeconds(1)), "CreatedAt is not set correctly");
        assertTrue(weeklyReview.getUpdatedAt().isBefore(now.plusSeconds(1)), "UpdatedAt is not set correctly");
    }

    @Test
    public void testParameterizedConstructor() {
        String reviewText = "My first weekly review.";
        WeeklyReview weeklyReview = new WeeklyReview(reviewText);

        assertNotNull(weeklyReview.getCreatedAt(), "CreatedAt should not be null");
        assertNotNull(weeklyReview.getUpdatedAt(), "UpdatedAt should not be null");
        assertEquals(reviewText, weeklyReview.getReview(), "Review text does not match the input");

        LocalDateTime now = LocalDateTime.now();
        assertTrue(weeklyReview.getCreatedAt().isBefore(now.plusSeconds(1)), "CreatedAt is not set correctly");
        assertTrue(weeklyReview.getUpdatedAt().isBefore(now.plusSeconds(1)), "UpdatedAt is not set correctly");
    }

    @Test
    public void testUpdateReview() {
        WeeklyReview weeklyReview = new WeeklyReview("Initial review");
        String updatedReview = "Updated review text.";

        weeklyReview.setReview(updatedReview);
        LocalDateTime beforeUpdate = LocalDateTime.now();
        weeklyReview.setUpdatedAt(beforeUpdate);

        assertEquals(updatedReview, weeklyReview.getReview(), "Review text was not updated correctly");
        assertEquals(beforeUpdate, weeklyReview.getUpdatedAt(), "UpdatedAt was not set correctly");
    }
}
