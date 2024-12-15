package dev.michaelodusami.organizationsystem.installation;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.michaelodusami.organizationsystem.fields.Field;
import dev.michaelodusami.organizationsystem.fields.FieldColumn;
import dev.michaelodusami.organizationsystem.weeklyreviews.WeeklyReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an Installation that tracks a repair process with associated plans, reviews, and tracking fields.
 * @author Michael-Andre Odusami
 * @version (v1) 2024.12.14
 */
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Installation {

    private String repair;

    private List<String> outcome;

    private LocalDate repairStartDate;
    private LocalDate repairEndDate;

    private long daysBetweenStartAndEnd;

    private List<String> plan;

    private List<WeeklyReview> weeklyReviews;

    private Map<FieldColumn, List<Field>> fieldsForTracking;
    
    /**
     * Creates an Installation object with essential fields using JSON deserialization.
     * This constructor is designed to be used by Jackson during the deserialization of JSON data
     * into an Installation object. It initializes the required fields and provides default values
     * for optional lists.
     *
     * @param repair            the description of the repair being tracked.
     * @param repairStartDate   the start date of the repair.
     * @param repairEndDate     the end date of the repair.
     */
    @JsonCreator
    public Installation(@JsonProperty("repair") String repair,
                        @JsonProperty("repairStartDate") LocalDate repairStartDate,
                        @JsonProperty("repairEndDate") LocalDate repairEndDate) {
        this(repair, repairStartDate, repairEndDate, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    /**
     * Creates an Installation object with all fields initialized.
     * This constructor provides full flexibility to initialize all fields of the Installation object.
     * If any of the list parameters are null, they are replaced with empty lists to avoid NullPointerException.
     * The days between the start and end dates are calculated automatically.
     *
     * @param repair            the description of the repair being tracked.
     * @param repairStartDate   the start date of the repair.
     * @param repairEndDate     the end date of the repair.
     * @param plan              the list of steps or plans associated with the repair process.
     * @param outcome           the list of outcomes from the repair process.
     * @param weeklyReviews     the list of weekly reviews tracking the repair progress.
     * @param fieldsForTracking the list of fields being tracked during the repair process.
     */
    public Installation(String repair, LocalDate repairStartDate, LocalDate repairEndDate,
                         List<String> plan, List<String> outcome, List<WeeklyReview> weeklyReviews,  Map<FieldColumn, List<Field>> fieldsForTracking) {
        this.repair = repair;
        this.outcome = (outcome != null) ? outcome : new ArrayList<>();
        this.repairStartDate = repairStartDate;
        this.repairEndDate = repairEndDate;
        this.plan = (plan != null) ? plan : new ArrayList<>();
        this.weeklyReviews = (weeklyReviews != null) ? weeklyReviews : new ArrayList<>();
        this.fieldsForTracking = (fieldsForTracking != null) ? fieldsForTracking : new HashMap<>();
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
     * @param column the column to add the field to
     * @param field the field to add
     */
    public void addFieldForTracking(FieldColumn column, Field field) {
        // Validate the field's type against the column's type first
        if (!field.validateType(column.getType(), field.getValue())) {
            throw new IllegalArgumentException("Field type does not match column type.");
        }

        // Add field to the list for the column
        fieldsForTracking.computeIfAbsent(column, key -> new ArrayList<>()).add(field);
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
