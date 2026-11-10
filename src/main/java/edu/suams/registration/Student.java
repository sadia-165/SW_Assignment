package edu.suams.registration;

import java.util.Set;

/**
 * The parts of a student record that course registration needs.
 *
 * @param id             student ID
 * @param cgpa           current CGPA
 * @param passedCourses  codes of courses passed with any grade except F
 * @param currentCredits credit hours already added this semester
 */
public record Student(String id, double cgpa, Set<String> passedCourses, double currentCredits) {

    /** Rule R1: below this CGPA a student is on academic probation. */
    public static final double PROBATION_CGPA = 2.20;

    public Student {
        passedCourses = Set.copyOf(passedCourses);
    }

    /** Rule R2: a course with no prerequisite is always allowed. */
    public boolean hasPassed(String courseCode) {
        return courseCode == null || passedCourses.contains(courseCode);
    }

    public boolean onProbation() {
        return cgpa < PROBATION_CGPA;
    }
}
