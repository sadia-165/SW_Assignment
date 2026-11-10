package edu.suams.registration;

import edu.suams.course.AssessmentScheme;
import edu.suams.course.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Course Registration joined to Student Records (INT-01). */
class RegistrationServiceIntegrationTest {

    private static final ZoneId DHAKA = ZoneId.of("Asia/Dhaka");

    /** In-memory stand-in for the Student Records module. */
    private static final class InMemoryStudentRepository implements StudentRepository {
        private final Map<String, Student> rows;

        InMemoryStudentRepository(Student... students) {
            this.rows = new java.util.HashMap<>();
            for (Student s : students) {
                rows.put(s.id(), s);
            }
        }

        @Override
        public Optional<Student> findById(String id) {
            return Optional.ofNullable(rows.get(id));
        }
    }

    private final RegistrationWindow window =
            new RegistrationWindow(LocalDate.of(2027, 1, 5), LocalDate.of(2027, 1, 19));
    private final Clock duringWindow =
            Clock.fixed(LocalDate.of(2027, 1, 10).atStartOfDay(DHAKA).toInstant(), DHAKA);
    private final Course cse2201 = new Course("CSE 2201", 3.0, "CSE 1201", AssessmentScheme.THEORY_2022);

    @Test
    @DisplayName("TC-IT-01 prerequisite passed on a retake, as stored in Student Records, counts as passed")
    void retakePassCounts() {
        Student retaker = new Student("2203101", 2.95, Set.of("CSE 1201"), 16.5);
        RegistrationService service = new RegistrationService(
                new InMemoryStudentRepository(retaker), new RegistrationValidator(window), duringWindow);
        assertEquals(RegistrationOutcome.OK, service.addCourse("2203101", cse2201));
    }

    @Test
    @DisplayName("TC-IT-02 a CGPA below 2.20 stored in Student Records applies the 15-credit limit")
    void probationFlagComesFromRecords() {
        Student onProbation = new Student("2203102", 2.10, Set.of("CSE 1201"), 13.5);
        RegistrationService service = new RegistrationService(
                new InMemoryStudentRepository(onProbation), new RegistrationValidator(window), duringWindow);
        assertEquals(RegistrationOutcome.CREDIT_LIMIT, service.addCourse("2203102", cse2201));
    }
}
