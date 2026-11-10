package edu.suams.result;

import edu.suams.course.AssessmentScheme;

import java.util.EnumMap;
import java.util.Map;

/** Picks the strategy for a course's assessment scheme. */
public class AssessmentStrategyRegistry {

    private final Map<AssessmentScheme, AssessmentStrategy> strategies = new EnumMap<>(AssessmentScheme.class);

    public AssessmentStrategyRegistry() {
        strategies.put(AssessmentScheme.THEORY_2022, new TheoryCourseStrategy2022());
        strategies.put(AssessmentScheme.THEORY_2027, new TheoryCourseStrategy2027());
        strategies.put(AssessmentScheme.SESSIONAL, new SessionalCourseStrategy());
    }

    public AssessmentStrategy forScheme(AssessmentScheme scheme) {
        AssessmentStrategy s = strategies.get(scheme);
        if (s == null) {
            throw new IllegalStateException("no strategy registered for " + scheme);
        }
        return s;
    }
}
