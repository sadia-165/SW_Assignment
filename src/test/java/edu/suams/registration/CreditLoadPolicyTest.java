package edu.suams.registration;

import edu.suams.registration.CreditLoadPolicy.Submission;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Black-box equivalence partitioning and boundary values for rule R1 (TC-BB-01 to TC-BB-05). */
class CreditLoadPolicyTest {

    @ParameterizedTest(name = "{0}: CGPA {1}, {2} credits -> {3}")
    @CsvSource({
            "TC-BB-01, 3.10, 18.0, ACCEPTED",
            "TC-BB-02, 3.10, 13.5, BELOW_MINIMUM",
            "TC-BB-03, 3.10, 25.5, ABOVE_MAXIMUM",
            "TC-BB-04, 2.10, 18.0, ABOVE_PROBATION_LIMIT",
            "TC-BB-05, 3.10, 24.0, ACCEPTED",
            "Extra-01, 2.20, 18.0, ACCEPTED"         // CGPA exactly 2.20 is not probation
    })
    @DisplayName("Credit load on submission")
    void creditLoad(String id, double cgpa, double credits, Submission expected) {
        Student s = new Student("2203149", cgpa, Set.of(), 0);
        assertEquals(expected, CreditLoadPolicy.checkTotal(s, credits), id);
    }
}
