package edu.suams.result;

import edu.suams.course.Course;

/** Context class of the Strategy pattern: marks to total to grade. */
public class ResultProcessor {

    private final AssessmentStrategyRegistry registry;

    public ResultProcessor(AssessmentStrategyRegistry registry) {
        this.registry = registry;
    }

    public Grade process(Course c, MarkSheet m) {
        AssessmentStrategy s = registry.forScheme(c.scheme());
        return GradeScale.toLetterGrade(s.computeTotal(m));          // R5
    }
}
