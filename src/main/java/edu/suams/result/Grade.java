package edu.suams.result;

/** A letter grade with its grade point and the rounded total it came from. */
public record Grade(String letter, double point, int roundedTotal) {
}
