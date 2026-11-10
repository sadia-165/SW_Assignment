package edu.suams.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Black-box boundary-value tests for rule R5 (TC-BB-06 to TC-BB-12). */
class GradeScaleTest {

    @ParameterizedTest(name = "{0}: total {1} -> {2} ({3})")
    @CsvSource({
            "TC-BB-06, 79,    A,  3.75",
            "TC-BB-07, 79.5,  A+, 4.00",
            "TC-BB-08, 80,    A+, 4.00",
            "TC-BB-09, 39.4,  F,  0.00",
            "TC-BB-10, 39.5,  D,  2.00",
            "TC-BB-11, 100,   A+, 4.00"
    })
    @DisplayName("Grade band edges and the rounding rule")
    void gradeBoundaries(String id, double total, String letter, double point) {
        Grade g = GradeScale.toLetterGrade(total);
        assertAll(id,
                () -> assertEquals(letter, g.letter()),
                () -> assertEquals(point, g.point(), 1e-9));
    }

    @ParameterizedTest(name = "TC-BB-12: total {0} is rejected")
    @ValueSource(doubles = {100.5, -1})
    @DisplayName("Totals outside 0 to 100 are rejected")
    void outOfRangeRejected(double total) {
        assertThrows(IllegalArgumentException.class, () -> GradeScale.toLetterGrade(total));
    }
}
