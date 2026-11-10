package edu.suams.result;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Credit-weighted GPA, rounded to two decimals. F counts as 0.00 with its credits. */
public final class GpaCalculator {

    public record CourseResult(double credit, Grade grade) {
    }

    private GpaCalculator() {
    }

    public static double computeGPA(List<CourseResult> results) {
        if (results.isEmpty()) {
            throw new IllegalArgumentException("no results");
        }
        double points = 0;
        double credits = 0;
        for (CourseResult r : results) {
            points += r.grade().point() * r.credit();
            credits += r.credit();
        }
        return BigDecimal.valueOf(points / credits).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
