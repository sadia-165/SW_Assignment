package edu.suams.result;

/**
 * Strategy pattern: one implementation per assessment scheme.
 * Each turns a mark sheet into a course total out of 100.
 */
public interface AssessmentStrategy {
    double computeTotal(MarkSheet m);
}
