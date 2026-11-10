package edu.suams.registration;

import edu.suams.course.Course;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Joins Course Registration to Student Records (integration point INT-01).
 * The clock is injected so tests give the same answer on any day.
 */
public class RegistrationService {

    private final StudentRepository students;
    private final RegistrationValidator validator;
    private final Clock clock;

    public RegistrationService(StudentRepository students, RegistrationValidator validator, Clock clock) {
        this.students = students;
        this.validator = validator;
        this.clock = clock;
    }

    public RegistrationOutcome addCourse(String studentId, Course course) {
        Student s = students.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("unknown student " + studentId));
        return validator.validateRegistration(s, course, LocalDate.now(clock));
    }
}
