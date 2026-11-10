package edu.suams.result;

import java.util.Arrays;

/**
 * Rule R4: CA 40 = best three of four class tests (10 each) + attendance (10);
 * final examination 60.
 */
public class TheoryCourseStrategy2022 implements AssessmentStrategy {

    @Override
    public double computeTotal(MarkSheet m) {
        if (m.finalExam() < 0 || m.finalExam() > 60) {
            throw new IllegalArgumentException("final exam must be 0 to 60");
        }
        return computeCATotal(m.classTests(), m.attendance()) + m.finalExam();
    }

    /** Best three of four class tests plus attendance, out of 40. */
    public int computeCATotal(int[] ct, int attendance) {
        if (ct.length != 4) {
            throw new IllegalArgumentException("expected 4 class tests, got " + ct.length);
        }
        for (int mark : ct) {
            if (mark < 0 || mark > 10) {
                throw new IllegalArgumentException("class test mark must be 0 to 10");
            }
        }
        if (attendance < 0 || attendance > 10) {
            throw new IllegalArgumentException("attendance must be 0 to 10");
        }
        int bestThree = Arrays.stream(ct).sorted().skip(1).sum();   // drop the lowest
        return bestThree + attendance;
    }
}
