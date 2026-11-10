package edu.suams.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit tests for computeCATotal() (rule R4). */
class TheoryCourseStrategy2022Test {

    private final TheoryCourseStrategy2022 strategy = new TheoryCourseStrategy2022();

    @Test
    @DisplayName("TC-UT-01 absent class test (0) is dropped as the lowest")
    void absentTestIsDropped() {
        assertEquals(32, strategy.computeCATotal(new int[]{8, 6, 9, 0}, 9));
    }

    @Test
    @DisplayName("TC-UT-02 only one of two equal lowest marks is dropped")
    void oneOfTwoEqualLowestDropped() {
        assertEquals(34, strategy.computeCATotal(new int[]{7, 7, 9, 10}, 8));
    }

    @Test
    @DisplayName("TC-UT-03 attendance above 10 is rejected")
    void attendanceAboveTenRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.computeCATotal(new int[]{8, 8, 8, 8}, 11));
    }

    @Test
    @DisplayName("Wrong number of class tests is rejected")
    void wrongTestCountRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.computeCATotal(new int[]{8, 8, 8}, 10));
    }

    @Test
    @DisplayName("Total = CA + final")
    void totalAddsFinal() {
        MarkSheet m = MarkSheet.theory(new int[]{8, 6, 9, 0}, 9, 48);
        assertEquals(80.0, strategy.computeTotal(m));
    }
}
