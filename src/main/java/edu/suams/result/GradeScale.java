package edu.suams.result;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rule R5: round the total to a whole number (0.5 rounds up), then grade it
 * on the uniform scale used by most Bangladeshi public universities.
 * The same for every course, so it is not part of any strategy.
 */
public final class GradeScale {

    private GradeScale() {
    }

    public static Grade toLetterGrade(double total) {
        if (Double.isNaN(total) || total < 0 || total > 100) {
            throw new IllegalArgumentException("total out of range: " + total);
        }
        // BigDecimal avoids 79.4999... style errors from double arithmetic
        int t = BigDecimal.valueOf(total).setScale(0, RoundingMode.HALF_UP).intValue();

        if (t >= 80) return new Grade("A+", 4.00, t);
        if (t >= 75) return new Grade("A", 3.75, t);
        if (t >= 70) return new Grade("A-", 3.50, t);
        if (t >= 65) return new Grade("B+", 3.25, t);
        if (t >= 60) return new Grade("B", 3.00, t);
        if (t >= 55) return new Grade("B-", 2.75, t);
        if (t >= 50) return new Grade("C+", 2.50, t);
        if (t >= 45) return new Grade("C", 2.25, t);
        if (t >= 40) return new Grade("D", 2.00, t);
        return new Grade("F", 0.00, t);
    }
}
