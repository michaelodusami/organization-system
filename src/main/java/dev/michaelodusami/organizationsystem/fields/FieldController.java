/**
 * Controller for managing Field entities.
 * Provides endpoints to perform CRUD operations on Field objects.
 * @author Michael-Andre Odusami
 * @version (v1) 2024.12.14
 */
package dev.michaelodusami.organizationsystem.fields;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldController {

    private final List<Field> fieldRepository = new ArrayList<>(); // Temporary in-memory storage

    /**
     * Create a new Field.
     * @param field the Field object to create
     * @return the created Field
     */
    @PostMapping
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        fieldRepository.add(field);
        return ResponseEntity.ok(field);
    }

    /**
     * Get all Fields.
     * @return a list of all Fields
     */
    @GetMapping
    public ResponseEntity<List<Field>> getAllFields() {
        return ResponseEntity.ok(fieldRepository);
    }

    /**
     * Get a Field by its index.
     * @param index the index of the Field to retrieve
     * @return the requested Field
     */
    @GetMapping("/{index}")
    public ResponseEntity<Field> getFieldByIndex(@PathVariable int index) {
        if (index < 0 || index >= fieldRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fieldRepository.get(index));
    }

    /**
     * Update a Field by its index.
     * @param index the index of the Field to update
     * @param updatedField the updated Field object
     * @return the updated Field
     */
    @PutMapping("/{index}")
    public ResponseEntity<Field> updateField(@PathVariable int index, @RequestBody Field updatedField) {
        if (index < 0 || index >= fieldRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        fieldRepository.set(index, updatedField);
        return ResponseEntity.ok(updatedField);
    }

    /**
     * Delete a Field by its index.
     * @param index the index of the Field to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{index}")
    public ResponseEntity<String> deleteField(@PathVariable int index) {
        if (index < 0 || index >= fieldRepository.size()) {
            return ResponseEntity.notFound().build();
        }
        fieldRepository.remove(index);
        return ResponseEntity.ok("Field deleted successfully.");
    }
}
