package edu.suams.result;

import edu.suams.course.AssessmentScheme;
import edu.suams.course.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Marks entry -> strategy -> grade scale, joined bottom-up (INT-02). */
class ResultProcessorIntegrationTest {

    private final ResultProcessor processor = new ResultProcessor(new AssessmentStrategyRegistry());

    @Test
    @DisplayName("TC-IT-03 theory course: tests 8,6,9,0, attendance 9, final 48 -> 80 -> A+")
    void theoryCourseEndToEnd() {
        Course cse3201 = new Course("CSE 3201", 3.0, null, AssessmentScheme.THEORY_2022);
        Grade g = processor.process(cse3201, MarkSheet.theory(new int[]{8, 6, 9, 0}, 9, 48));
        assertEquals("A+", g.letter());
        assertEquals(80, g.roundedTotal());
    }

    @Test
    @DisplayName("TC-IT-04 sessional course: lab 45, quiz 15, viva 14 -> 74 -> A-")
    void sessionalCourseUsesItsOwnStrategy() {
        Course cse3202 = new Course("CSE 3202", 1.5, null, AssessmentScheme.SESSIONAL);
        Grade g = processor.process(cse3202, MarkSheet.sessional(45, 15, 14));
        assertEquals("A-", g.letter());
        assertEquals(3.50, g.point(), 1e-9);
    }
}
