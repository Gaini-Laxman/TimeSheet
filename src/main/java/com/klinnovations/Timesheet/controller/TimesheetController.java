package com.klinnovations.Timesheet.controller;

import com.klinnovations.Timesheet.entity.Timesheet;
import com.klinnovations.Timesheet.repository.TimesheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500/timesheet-frontend/src/app/timesheet-form/timesheet-form.component.html")
@RequestMapping("/api/timesheets")
public class TimesheetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimesheetController.class);

    @Autowired
    private TimesheetRepository timesheetRepository;

    @GetMapping("/getAll")
    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Timesheet> getTimesheetById(@PathVariable Long id) {
        return timesheetRepository.findById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTimesheet(@RequestBody Timesheet timesheet) {
        try {
            Timesheet savedTimesheet = timesheetRepository.save(timesheet);
            String successMessage = "Timesheet saved successfully with ID: " + savedTimesheet.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save timesheet. Please try again.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTimesheet(@PathVariable Long id, @RequestBody Timesheet updatedTimesheet) {
        if (timesheetRepository.existsById(id)) {
            updatedTimesheet.setId(id);
            Timesheet savedTimesheet = timesheetRepository.save(updatedTimesheet);
            String successMessage = "Timesheet with ID " + id + " updated successfully.";
            return ResponseEntity.ok(new UpdateResponse(savedTimesheet, successMessage));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    static class UpdateResponse {
        private Timesheet timesheet;
        private String message;

        public UpdateResponse(Timesheet timesheet, String message) {
            this.timesheet = timesheet;
            this.message = message;
        }

        public Timesheet getTimesheet() {
            return timesheet;
        }

        public String getMessage() {
            return message;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimesheet(@PathVariable Long id) {
        if (timesheetRepository.existsById(id)) {
            timesheetRepository.deleteById(id);
            return ResponseEntity.ok("Timesheet with ID " + id + " Deleted Successfully...");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Timesheet with ID " + id + " does not Exist.");
        }
    }
}
