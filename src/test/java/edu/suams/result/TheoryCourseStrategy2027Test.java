package edu.suams.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The example agreed with the controller's office before coding (SQA-1). */
class TheoryCourseStrategy2027Test {

    private final TheoryCourseStrategy2027 strategy = new TheoryCourseStrategy2027();

    @Test
    @DisplayName("Class tests 7, 9, 5 give 16 of 20; with attendance 10 and final 50 the total is 76")
    void bestTwoOfThree() {
        MarkSheet m = MarkSheet.theory(new int[]{7, 9, 5}, 10, 50);
        assertEquals(76.0, strategy.computeTotal(m));
    }

    @Test
    @DisplayName("A 2022-style sheet with four class tests is rejected")
    void fourTestsRejected() {
        MarkSheet m = MarkSheet.theory(new int[]{7, 9, 5, 8}, 10, 50);
        assertThrows(IllegalArgumentException.class, () -> strategy.computeTotal(m));
    }
}
