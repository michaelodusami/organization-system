package dev.michaelodusami.organizationsystem.installation;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import dev.michaelodusami.organizationsystem.fields.Field;
import dev.michaelodusami.organizationsystem.weeklyreviews.WeeklyReview;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an Installation that tracks a repair process with associated plans, reviews, and tracking fields.
 * @author Michael-Andre Odusami
 * @version (v1) 2024.12.14
 */
@Getter
@Setter
@Entity
@ToString
public class Installation {

   // @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   // private Long id;

    private String repair;

    @ElementCollection
    private List<String> outcome;

    private LocalDate repairStartDate;
    private LocalDate repairEndDate;

    // @Transient
    private long daysBetweenStartAndEnd;

    @ElementCollection
    private List<String> plan;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyReview> weeklyReviews;

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fieldsForTracking;


    public Installation(String repair, LocalDate repairStartDate, LocalDate repairEndDate) {
        this(repair, new ArrayList<>(), repairStartDate, repairEndDate, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Installation(String repair, List<String> outcome, LocalDate repairStartDate, LocalDate repairEndDate,
                         List<String> plan, List<WeeklyReview> weeklyReviews, List<Field> fieldsForTracking) {
        this.repair = repair;
        this.outcome = (outcome != null) ? outcome : new ArrayList<>();
        this.repairStartDate = repairStartDate;
        this.repairEndDate = repairEndDate;
        this.plan = (plan != null) ? plan : new ArrayList<>();
        this.weeklyReviews = (weeklyReviews != null) ? weeklyReviews : new ArrayList<>();
        this.fieldsForTracking = (fieldsForTracking != null) ? fieldsForTracking : new ArrayList<>();
        calculateDaysBetween();
    }

    /**
     * Adds a weekly review to the list of weekly reviews.
     * @param weeklyReview the weekly review to add.
     */
    public void addWeeklyReview(WeeklyReview weeklyReview) {
        if (this.weeklyReviews == null) {
            this.weeklyReviews = new ArrayList<>();
        }
        this.weeklyReviews.add(weeklyReview);
    }

    /**
     * Adds a field to the list of fields for tracking.
     * @param field the field to add.
     */
    public void addFieldForTracking(Field field) {
        if (this.fieldsForTracking == null) {
            this.fieldsForTracking = new ArrayList<>();
        }
        this.fieldsForTracking.add(field);
    }

    /**
     * Adds a plan to the list of plans.
     * @param planItem the plan item to add.
     */
    public void addPlan(String planItem) {
        if (this.plan == null) {
            this.plan = new ArrayList<>();
        }
        this.plan.add(planItem);
    }


    /**
     * Calculates the number of days between the start and end dates of the repair.
     */
    public void calculateDaysBetween() {
        if (repairStartDate != null && repairEndDate != null) {
            this.daysBetweenStartAndEnd = ChronoUnit.DAYS.between(repairStartDate, repairEndDate);
        } else {
            this.daysBetweenStartAndEnd = 0;
        }
    }

    /**
     * Updates the repair start and end dates and recalculates the days between.
     * @param repairStartDate the new start date
     * @param repairEndDate   the new end date
     */
    public void updateRepairDates(LocalDate repairStartDate, LocalDate repairEndDate) {
        this.repairStartDate = repairStartDate;
        this.repairEndDate = repairEndDate;
        calculateDaysBetween();
    }
}
