package dev.michaelodusami.organizationsystem.weeklyreviews;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a weekly review beloning to a single installation
 * @author Michael-Andre Odusami 
 * @version (v1) 2024.12.14
 */
@Getter
@Setter 
@ToString
@Component
public class WeeklyReview {
    
    // some ids (for self, and for installation)
  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String review;

    public WeeklyReview()
    {
        this("This is the start of your weekly review.");
    }

    public WeeklyReview(String review)
    {
        this.review = review;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
}
