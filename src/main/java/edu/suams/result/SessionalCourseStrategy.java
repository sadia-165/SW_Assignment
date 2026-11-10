package edu.suams.result;

/** Rule R6: lab work (60) + quizzes (20) + viva (20); no written final. */
public class SessionalCourseStrategy implements AssessmentStrategy {

    @Override
    public double computeTotal(MarkSheet m) {
        check(m.labWork(), 60, "lab work");
        check(m.quiz(), 20, "quiz");
        check(m.viva(), 20, "viva");
        return m.labWork() + m.quiz() + m.viva();
    }

    private static void check(double value, double max, String name) {
        if (value < 0 || value > max) {
            throw new IllegalArgumentException(name + " must be 0 to " + max);
        }
    }
}
