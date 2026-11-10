package edu.suams.result;

import edu.suams.result.GpaCalculator.CourseResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GpaCalculatorTest {

    @Test
    @DisplayName("TC-UT-04 credit-weighted GPA with a 1.5-credit sessional course")
    void weightedBySessionalCredit() {
        double gpa = GpaCalculator.computeGPA(List.of(
                new CourseResult(3.0, GradeScale.toLetterGrade(85)),   // A+ 4.00
                new CourseResult(3.0, GradeScale.toLetterGrade(62)),   // B  3.00
                new CourseResult(1.5, GradeScale.toLetterGrade(41))    // D  2.00
        ));
        assertEquals(3.20, gpa, 1e-9);
    }

    @Test
    @DisplayName("TC-UT-05 an F counts as 0.00 with its credits; GPA rounded to two decimals")
    void failCountsWithItsCredits() {
        double gpa = GpaCalculator.computeGPA(List.of(
                new CourseResult(3.0, GradeScale.toLetterGrade(77)),   // A 3.75
                new CourseResult(3.0, GradeScale.toLetterGrade(30))    // F 0.00
        ));
        assertEquals(1.88, gpa, 1e-9);
    }
}
