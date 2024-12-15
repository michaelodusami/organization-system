package dev.michaelodusami.organizationsystem;

import dev.michaelodusami.organizationsystem.fields.ColumnType;
import dev.michaelodusami.organizationsystem.fields.Field;
import dev.michaelodusami.organizationsystem.fields.FieldColumn;
import dev.michaelodusami.organizationsystem.installation.Installation;
import dev.michaelodusami.organizationsystem.weeklyreviews.WeeklyReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Installation
 */
public class InstallationTest {

    private Installation installation;

    @BeforeEach
    public void setup() {
        installation = new Installation("Improve Typing Speed", LocalDate.now(), LocalDate.now().plusDays(30));
    }

    @Test
    public void testConstructorInitializesFieldsCorrectly() {
        assertEquals("Improve Typing Speed", installation.getRepair());
        assertNotNull(installation.getOutcome());
        assertNotNull(installation.getPlan());
        assertNotNull(installation.getWeeklyReviews());
        assertNotNull(installation.getFieldsForTracking());
    }

    @Test
    public void testCalculateDaysBetween() {
        long expectedDays = 30;
        assertEquals(expectedDays, installation.getDaysBetweenStartAndEnd());
    }

    @Test
    public void testUpdateRepairDates() {
        LocalDate newStartDate = LocalDate.now().minusDays(10);
        LocalDate newEndDate = LocalDate.now().plusDays(50);

        installation.updateRepairDates(newStartDate, newEndDate);

        assertEquals(newStartDate, installation.getRepairStartDate());
        assertEquals(newEndDate, installation.getRepairEndDate());
        assertEquals(60, installation.getDaysBetweenStartAndEnd());
    }

    @Test
    public void testAddWeeklyReview() {
        WeeklyReview weeklyReview = new WeeklyReview("First review");
        installation.addWeeklyReview(weeklyReview);

        assertEquals(1, installation.getWeeklyReviews().size());
        assertEquals("First review", installation.getWeeklyReviews().get(0).getReview());
    }

    @Test
    public void testAddFieldForTracking_NewColumn() {
        FieldColumn column = new FieldColumn("Test Column", ColumnType.TEXT);
        Field field = new Field(ColumnType.TEXT, "Sample Value");

        installation.addFieldForTracking(column, field);

        assertTrue(installation.getFieldsForTracking().containsKey(column));
        assertEquals(1, installation.getFieldsForTracking().get(column).size()); // Ensure logic execution safely.
    }
   

    @Test
    public void testAddPlanItem() {
        String planItem = "Practice typing daily for 15 minutes.";
        installation.addPlan(planItem);

        assertEquals(1, installation.getPlan().size());
        assertEquals(planItem, installation.getPlan().get(0));

        System.out.println(installation);
    }

}
