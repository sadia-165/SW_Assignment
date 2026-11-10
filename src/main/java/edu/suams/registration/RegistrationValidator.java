package edu.suams.registration;

import edu.suams.course.Course;

/**
 * Checks rules R1 to R3 each time a student adds a course.
 *
 * <p>This is the function used for basis-path testing in the proposal
 * (section B.5). It has four decisions, so its cyclomatic complexity is 5.
 */
public class RegistrationValidator {

    public static final double MAX_CREDIT_REGULAR = 24.0;
    public static final double MAX_CREDIT_PROBATION = 15.0;

    private final RegistrationWindow window;

    public RegistrationValidator(RegistrationWindow window) {
        this.window = window;
    }

    public RegistrationOutcome validateRegistration(Student s, Course c, java.time.LocalDate today) {
        if (!window.isOpen(today))                              // D1 (node 1)
            return RegistrationOutcome.WINDOW_CLOSED;           // node 2
        if (!s.hasPassed(c.prerequisite()))                     // D2 (node 3)
            return RegistrationOutcome.PREREQ_MISSING;          // node 4
        double maxCredit = MAX_CREDIT_REGULAR;                  // node 5
        if (s.cgpa() < Student.PROBATION_CGPA)                  // D3 (node 5)
            maxCredit = MAX_CREDIT_PROBATION;                   // node 6
        if (s.currentCredits() + c.credit() > maxCredit)        // D4 (node 7)
            return RegistrationOutcome.CREDIT_LIMIT;            // node 8
        return RegistrationOutcome.OK;                          // node 9
    }                                                           // node 10 (exit)
}
