package edu.suams.registration;

/** Result of trying to add one course to a student's registration. */
public enum RegistrationOutcome {
    OK,
    WINDOW_CLOSED,
    PREREQ_MISSING,
    CREDIT_LIMIT
}
