package edu.suams.registration;

import edu.suams.course.AssessmentScheme;
import edu.suams.course.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * White-box basis-path tests for validateRegistration().
 * V(G) = 5, so five independent paths; TC-WB-06 is an extra risky path.
 */
class RegistrationValidatorTest {

    private static final LocalDate OPEN = LocalDate.of(2027, 1, 5);
    private static final LocalDate CLOSE = LocalDate.of(2027, 1, 19);
    private static final LocalDate DURING = LocalDate.of(2027, 1, 10);

    private final RegistrationValidator validator = new RegistrationValidator(new RegistrationWindow(OPEN, CLOSE));
    private final Course cse2201 = new Course("CSE 2201", 3.0, "CSE 1201", AssessmentScheme.THEORY_2022);

    private static Student student(double cgpa, double currentCredits, String... passed) {
        return new Student("2203149", cgpa, Set.of(passed), currentCredits);
    }

    @Test
    @DisplayName("TC-WB-01 path 1-2-10: window closed")
    void windowClosed() {
        assertEquals(RegistrationOutcome.WINDOW_CLOSED,
                validator.validateRegistration(student(3.40, 18.0, "CSE 1201"), cse2201, CLOSE.plusDays(1)));
    }

    @Test
    @DisplayName("TC-WB-02 path 1-3-4-10: prerequisite not passed")
    void prerequisiteMissing() {
        assertEquals(RegistrationOutcome.PREREQ_MISSING,
                validator.validateRegistration(student(3.40, 18.0), cse2201, DURING));
    }

    @Test
    @DisplayName("TC-WB-03 path 1-3-5-7-9-10: regular student within limit")
    void regularWithinLimit() {
        assertEquals(RegistrationOutcome.OK,
                validator.validateRegistration(student(3.40, 18.0, "CSE 1201"), cse2201, DURING));
    }

    @Test
    @DisplayName("TC-WB-04 path 1-3-5-6-7-9-10: probation student reaches exactly 15")
    void probationExactlyAtLimit() {
        assertEquals(RegistrationOutcome.OK,
                validator.validateRegistration(student(2.10, 12.0, "CSE 1201"), cse2201, DURING));
    }

    @Test
    @DisplayName("TC-WB-05 path 1-3-5-7-8-10: regular student over 24")
    void regularOverLimit() {
        assertEquals(RegistrationOutcome.CREDIT_LIMIT,
                validator.validateRegistration(student(3.40, 22.5, "CSE 1201"), cse2201, DURING));
    }

    @Test
    @DisplayName("TC-WB-06 path 1-3-5-6-7-8-10: probation student over 15")
    void probationOverLimit() {
        assertEquals(RegistrationOutcome.CREDIT_LIMIT,
                validator.validateRegistration(student(2.10, 13.5, "CSE 1201"), cse2201, DURING));
    }

    @Test
    @DisplayName("Boundary: CGPA exactly 2.20 is not probation (catches <= instead of <)")
    void cgpaExactlyAtProbationLine() {
        assertEquals(RegistrationOutcome.OK,
                validator.validateRegistration(student(2.20, 18.0, "CSE 1201"), cse2201, DURING));
    }

    @Test
    @DisplayName("Boundary: first and last day of the window are open")
    void windowEdgesAreOpen() {
        Student s = student(3.40, 18.0, "CSE 1201");
        assertEquals(RegistrationOutcome.OK, validator.validateRegistration(s, cse2201, OPEN));
        assertEquals(RegistrationOutcome.OK, validator.validateRegistration(s, cse2201, CLOSE));
    }
}
