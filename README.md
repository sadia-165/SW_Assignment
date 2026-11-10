# SUAMS: Course Registration and Result Module

Code that goes with the technical proposal *Smart University Academic Management System (SUAMS)*
by Md. Iftekhar Hossain Kaif (Roll 2203149, Section C, Series 22, CSE, RUET).

It implements the two parts of SUAMS that the proposal tests in detail:

- **Course registration rules** (R1 to R3): credit limits, probation limit, prerequisites, registration window.
- **Result processing** (R4 to R7) using the **Strategy pattern**: one strategy per assessment scheme, and a shared grade scale.

## Assumed rules

| ID | Rule |
| --- | --- |
| R1 | Regular students take 15 to 24 credits; CGPA below 2.20 means probation and at most 15 credits |
| R2 | A course needs its prerequisite passed (any grade except F) |
| R3 | Registration only while the department's window is open |
| R4 | Theory course: best 3 of 4 class tests (30) + attendance (10) + final (60) |
| R5 | Total rounded (0.5 up), then graded: 80+ A+ 4.00, 75 A 3.75, 70 A- 3.50, 65 B+ 3.25, 60 B 3.00, 55 B- 2.75, 50 C+ 2.50, 45 C 2.25, 40 D 2.00, below 40 F |
| R6 | Sessional course: lab work (60) + quizzes (20) + viva (20) |
| R7 | Results visible only after controller approval; published marks are locked |

## Project structure

```
src/main/java/edu/suams/
  course/        Course, AssessmentScheme
  registration/  RegistrationValidator (basis-path function), CreditLoadPolicy,
                 RegistrationService, Student, RegistrationWindow
  result/        AssessmentStrategy + TheoryCourseStrategy2022 / 2027, SessionalCourseStrategy,
                 AssessmentStrategyRegistry, ResultProcessor, GradeScale, GpaCalculator
src/test/java/edu/suams/   JUnit 5 tests, each named with its test case ID
.github/        CI workflow, pull request template (SQA-2 checklist), CODEOWNERS
docs/           grading.feature (SQA-1 examples)
```

## Test case map

| Test case IDs | Test class | Technique |
| --- | --- | --- |
| TC-UT-01 to 03 | `TheoryCourseStrategy2022Test` | Unit |
| TC-UT-04, 05 | `GpaCalculatorTest` | Unit |
| TC-IT-01, 02 | `RegistrationServiceIntegrationTest` | Integration (Registration + Student Records) |
| TC-IT-03, 04 | `ResultProcessorIntegrationTest` | Integration (Marks + Strategy + Grade scale) |
| TC-BB-01 to 05 | `CreditLoadPolicyTest` | Black-box, equivalence partitioning and BVA |
| TC-BB-06 to 12 | `GradeScaleTest` | Black-box, boundary-value analysis |
| TC-WB-01 to 06 | `RegistrationValidatorTest` | White-box, basis-path testing |

## Run the tests

Needs JDK 21 and Maven.

```bash
mvn test        # tests only
mvn verify      # tests + JaCoCo coverage report in target/site/jacoco/index.html
```

## Push guide (gives the commit history the proposal describes)

**VS Code way:** open this folder in VS Code and follow `HOW_TO_PUSH.md`.

**Quick way:** install the GitHub CLI, run `gh auth login`, then `bash push_to_github.sh` from this folder. It does every step below for `sadia-165/SW_Assignment` and pauses at each screenshot.

Manual way: replace `YOUR-USERNAME/suams-registration-result` below with your repository, and the username in `.github/CODEOWNERS`.

**1. Create the repository.** On GitHub, create a new public repository named
`suams-registration-result`, with no README, licence or .gitignore.

**2. First commit on `main` (skeleton and CI).**

```bash
cd suams
git init -b main
git add pom.xml .gitignore README.md .github scripts docs
git commit -m "SUAMS-100: project skeleton, CI workflow and PR template"
git remote add origin https://github.com/YOUR-USERNAME/suams-registration-result.git
git push -u origin main
```

**3. Protect `main`.** Settings > Branches > Add branch protection rule (or Rulesets), branch `main`:
tick *Require a pull request before merging* and *Require status checks to pass* (choose `build-test`;
it appears after the first CI run). If you work alone, set required approvals to 0, because GitHub
does not let you approve your own pull request. If a classmate reviews, set it to 1.

**4. Feature 1: result processing.**

```bash
git switch -c feature/SUAMS-101-grading-strategy
git add src/main/java/edu/suams/course src/main/java/edu/suams/result
git commit -m "SUAMS-101: Strategy-based result processing and grade scale (R4-R6)"
git add src/test/java/edu/suams/result
git commit -m "SUAMS-101: unit, boundary and integration tests for grading"
git push -u origin feature/SUAMS-101-grading-strategy
```

On GitHub: *Compare & pull request* > fill the template > *Create pull request*. Wait for the green
check. Open *Files changed*, add a review comment on `GradeScale.java`, then *Squash and merge* and
*Delete branch*.

**5. Feature 2: registration rules.**

```bash
git switch main
git pull
git switch -c feature/SUAMS-142-probation-credit-limit
git add src/main/java/edu/suams/registration
git commit -m "SUAMS-142: registration rules R1-R3 with probation credit limit"
git add src/test/java/edu/suams/registration
git commit -m "SUAMS-142: basis-path and partition tests for registration"
git push -u origin feature/SUAMS-142-probation-credit-limit
```

Open the pull request, wait for CI, review, squash and merge as before.

**6. Release.**

```bash
git switch main
git pull
git tag -a v1.0.0 -m "SUAMS 1.0: registration and result module"
git push origin v1.0.0
```

The tag starts the `deploy` job. On GitHub, Releases > *Draft a new release* > choose `v1.0.0` >
*Generate release notes* > *Publish release*.

**7. SonarCloud (optional).** Sign in to sonarcloud.io with GitHub and import the repository. In the
GitHub repository add the secret `SONAR_TOKEN` (Settings > Secrets and variables > Actions) and the
variables `SONAR_ORGANIZATION` and `SONAR_PROJECT_KEY` with the values SonarCloud shows. Turn off
SonarCloud's *Automatic Analysis* so the CI step does the analysis. The next CI run shows the quality gate.
