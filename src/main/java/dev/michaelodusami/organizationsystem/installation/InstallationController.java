package dev.michaelodusami.organizationsystem.installation;

/**
 * Controller for managing Installation entities.
 * Provides endpoints to perform CRUD operations on Installation objects.
 * Includes functionality to manage associated plans, weekly reviews, and tracking fields.
 * 
 * @author Michael-Andre Odusami
 * @version (v1) 2024.12.14
 */
import dev.michaelodusami.organizationsystem.fields.Field;
import dev.michaelodusami.organizationsystem.weeklyreviews.WeeklyReview;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/installations")
public class InstallationController {

    private final List<Installation> installationRepository = new ArrayList<>(); // Temporary in-memory storage

    /**
     * Create a new Installation.
     * @param installation the Installation object to create
     * @return the created Installation
     */
    @PostMapping
    public ResponseEntity<Installation> createInstallation(@RequestBody Installation installation) {
        installationRepository.add(installation);
        return ResponseEntity.ok(installation);
    }

    /**
     * Get all Installations.
     * @return a list of all Installations
     */
    @GetMapping
    public ResponseEntity<List<Installation>> getAllInstallations() {
        return ResponseEntity.ok(installationRepository);
    }

    /**
     * Get an Installation by its index.
     * @param index the index of the Installation to retrieve
     * @return the requested Installation
     */
    @GetMapping("/{index}")
    public ResponseEntity<Installation> getInstallationByIndex(@PathVariable int index) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(installationRepository.get(index));
    }

    /**
     * Update an Installation by its index.
     * @param index the index of the Installation to update
     * @param updatedInstallation the updated Installation object
     * @return the updated Installation
     */
    @PutMapping("/{index}")
    public ResponseEntity<Installation> updateInstallation(@PathVariable int index, @RequestBody Installation updatedInstallation) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        installationRepository.set(index, updatedInstallation);
        return ResponseEntity.ok(updatedInstallation);
    }

    /**
     * Delete an Installation by its index.
     * @param index the index of the Installation to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteInstallation(@PathVariable int index) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        installationRepository.remove(index);
        return ResponseEntity.ok("Installation deleted successfully.");
    }

    /**
     * Add a plan to an Installation.
     * @param index the index of the Installation
     * @param plan the plan to add
     * @return the updated Installation
     */
    @PostMapping("/{index}/plans")
    public ResponseEntity<Installation> addPlanToInstallation(@PathVariable int index, @RequestBody String plan) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        Installation installation = installationRepository.get(index);
        installation.addPlan(plan);
        return ResponseEntity.ok(installation);
    }

    /**
     * Add a WeeklyReview to an Installation.
     * @param index the index of the Installation
     * @param weeklyReview the WeeklyReview to add
     * @return the updated Installation
     */
    @PostMapping("/{index}/weekly-reviews")
    public ResponseEntity<Installation> addWeeklyReviewToInstallation(@PathVariable int index, @RequestBody WeeklyReview weeklyReview) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        Installation installation = installationRepository.get(index);
        installation.addWeeklyReview(weeklyReview);
        return ResponseEntity.ok(installation);
    }

    /**
     * Add a Field to an Installation.
     * @param index the index of the Installation
     * @param field the Field to add
     * @return the updated Installation
     */
    @PostMapping("/{index}/fields")
    public ResponseEntity<Installation> addFieldToInstallation(@PathVariable int index, @RequestBody Field field) {
        if (index < 0 || index >= installationRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        Installation installation = installationRepository.get(index);
        installation.addFieldForTracking(field);
        return ResponseEntity.ok(installation);
    }
}
