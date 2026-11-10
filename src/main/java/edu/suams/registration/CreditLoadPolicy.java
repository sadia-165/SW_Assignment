package edu.suams.registration;

/**
 * Rule R1, checked when the student submits the whole registration.
 * Courses are added one at a time, so the minimum can only be checked here.
 */
public final class CreditLoadPolicy {

    public static final double MIN_CREDIT = 15.0;

    public enum Submission { ACCEPTED, BELOW_MINIMUM, ABOVE_MAXIMUM, ABOVE_PROBATION_LIMIT }

    private CreditLoadPolicy() {
    }

    public static Submission checkTotal(Student s, double totalCredits) {
        if (s.onProbation()) {
            return totalCredits > RegistrationValidator.MAX_CREDIT_PROBATION
                    ? Submission.ABOVE_PROBATION_LIMIT
                    : Submission.ACCEPTED;
        }
        if (totalCredits < MIN_CREDIT) {
            return Submission.BELOW_MINIMUM;
        }
        if (totalCredits > RegistrationValidator.MAX_CREDIT_REGULAR) {
            return Submission.ABOVE_MAXIMUM;
        }
        return Submission.ACCEPTED;
    }
}
