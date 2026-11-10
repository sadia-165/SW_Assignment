package edu.suams.result;

import java.util.Arrays;

/**
 * Revised rule for batches admitted from 2027: best two of three class tests
 * (10 each, 20 in total) + attendance (10); final examination 70.
 * Added as a new class so TheoryCourseStrategy2022 stays untouched.
 */
public class TheoryCourseStrategy2027 implements AssessmentStrategy {

    @Override
    public double computeTotal(MarkSheet m) {
        int[] ct = m.classTests();
        if (ct.length != 3) {
            throw new IllegalArgumentException("expected 3 class tests, got " + ct.length);
        }
        for (int mark : ct) {
            if (mark < 0 || mark > 10) {
                throw new IllegalArgumentException("class test mark must be 0 to 10");
            }
        }
        if (m.attendance() < 0 || m.attendance() > 10) {
            throw new IllegalArgumentException("attendance must be 0 to 10");
        }
        if (m.finalExam() < 0 || m.finalExam() > 70) {
            throw new IllegalArgumentException("final exam must be 0 to 70");
        }
        int bestTwo = Arrays.stream(ct).sorted().skip(1).sum();     // drop the lowest
        return bestTwo + m.attendance() + m.finalExam();
    }
}
