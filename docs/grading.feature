# SQA-1: examples agreed with the examination controller's office before coding.
# Each scenario is mirrored by a JUnit test (named in the comment).

Feature: Theory course grading

  Scenario: An absent class test is dropped as the lowest (TC-UT-01)
    Given class test marks 8, 6, 9 and 0
    And attendance 9
    When the CA total is calculated
    Then the CA total is 32

  Scenario: Half a mark below A+ rounds up (TC-BB-07)
    Given a course total of 79.5
    When the grade is assigned
    Then the grade is A+ with grade point 4.00

  Scenario: Best two of three for batches from 2027 (TheoryCourseStrategy2027Test)
    Given class test marks 7, 9 and 5
    When the class test part is calculated
    Then it is 16 out of 20
