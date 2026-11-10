package edu.suams.course;

import java.util.Objects;

/**
 * A course offered in a semester.
 *
 * @param code         course code, for example "CSE 2201"
 * @param credit       credit hours, for example 3.0 or 1.5
 * @param prerequisite code of the prerequisite course, or null if there is none
 * @param scheme       how the course is assessed
 */
public record Course(String code, double credit, String prerequisite, AssessmentScheme scheme) {

    public Course {
        Objects.requireNonNull(code, "code");
        Objects.requireNonNull(scheme, "scheme");
        if (credit <= 0) {
            throw new IllegalArgumentException("credit must be positive");
        }
    }
}
