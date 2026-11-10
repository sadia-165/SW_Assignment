package edu.suams.course;

/**
 * How a course is assessed. Stored on the course record, so the academic
 * office can assign a scheme to a course without a code change.
 */
public enum AssessmentScheme {
    /** Rule R4: best 3 of 4 class tests (30) + attendance (10) + final (60). */
    THEORY_2022,
    /** Council change for batches from 2027: best 2 of 3 class tests (20) + attendance (10) + final (70). */
    THEORY_2027,
    /** Rule R6: lab work + quizzes + viva, no written final. */
    SESSIONAL
}
