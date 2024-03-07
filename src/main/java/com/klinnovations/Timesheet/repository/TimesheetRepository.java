package com.klinnovations.Timesheet.repository;

import com.klinnovations.Timesheet.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
}
