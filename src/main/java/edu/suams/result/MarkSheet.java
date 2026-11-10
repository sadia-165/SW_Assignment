package edu.suams.result;

/**
 * Marks recorded for one student in one course. Theory courses use the
 * class test, attendance and final fields; sessional courses use lab work,
 * quiz and viva.
 */
public record MarkSheet(int[] classTests, int attendance, double finalExam,
                        double labWork, double quiz, double viva) {

    public MarkSheet {
        classTests = classTests == null ? new int[0] : classTests.clone();
    }

    public static MarkSheet theory(int[] classTests, int attendance, double finalExam) {
        return new MarkSheet(classTests, attendance, finalExam, 0, 0, 0);
    }

    public static MarkSheet sessional(double labWork, double quiz, double viva) {
        return new MarkSheet(new int[0], 0, 0, labWork, quiz, viva);
    }

    @Override
    public int[] classTests() {
        return classTests.clone();
    }
}
