# Push this project to GitHub from VS Code, and take the screenshots

Repository: https://github.com/sadia-165/SW_Assignment

You need: VS Code, Git (git-scm.com), and for running tests JDK 21 and Maven.
When VS Code opens this folder it suggests the Java Extension Pack; click Install.

Open the terminal in VS Code with **Terminal > New Terminal** and type the
commands below one block at a time. The first `git push` opens a browser window
to sign in to GitHub; sign in with the account that owns the repository.

---

## Step 0. Run the tests (Screenshot 1)

```bash
mvn test
```

Wait for `Tests run: 35, Failures: 0, Errors: 0` and `BUILD SUCCESS`.

**Screenshot 1:** the terminal showing those lines.

(No Maven? Skip this now and use the Actions log in Step 5 instead.)

---

## Step 1. First commit on main

```bash
git init -b main
git remote add origin https://github.com/sadia-165/SW_Assignment.git
git add pom.xml .gitignore README.md HOW_TO_PUSH.md push_to_github.sh .github scripts docs
git commit -m "SUAMS-100: project skeleton, CI workflow and PR template"
git push -u origin main
```

If the push is rejected because the repository already has a README, run
`git pull origin main --allow-unrelated-histories --no-edit` and push again.

**Screenshot 2:** open https://github.com/sadia-165/SW_Assignment (the Code tab on `main`).

**Screenshot 3:** on GitHub go to **Settings > Branches > Add branch protection rule**
(or **Settings > Rules > Rulesets**). Branch name `main`. Tick
*Require a pull request before merging* (set required approvals to 0 if you work alone,
because GitHub does not let you approve your own pull request) and
*Require status checks to pass* (choose `build-test`). Save, then screenshot the rule.

---

## Step 2. Feature 1: result processing

```bash
git switch -c feature/SUAMS-101-grading-strategy
git add src/main/java/edu/suams/course src/main/java/edu/suams/result
git commit -m "SUAMS-101: Strategy-based result processing and grade scale (R4-R6)"
git add src/test/java/edu/suams/result
git commit -m "SUAMS-101: unit, boundary and integration tests for grading"
git push -u origin feature/SUAMS-101-grading-strategy
```

On GitHub click **Compare & pull request**. The description box already holds the
checklist template; tick the boxes, then **Create pull request**. Wait for the green
check (1 to 2 minutes). Click **Squash and merge**, **Confirm**, **Delete branch**.

---

## Step 3. Feature 2: registration rules

```bash
git switch main
git pull origin main
git switch -c feature/SUAMS-142-probation-credit-limit
git add src/main/java/edu/suams/registration
git commit -m "SUAMS-142: registration rules R1-R3 with probation credit limit"
git add src/test/java/edu/suams/registration
git commit -m "SUAMS-142: basis-path and partition tests for registration"
git push -u origin feature/SUAMS-142-probation-credit-limit
```

**Screenshot 4:** open
https://github.com/sadia-165/SW_Assignment/commits/feature/SUAMS-142-probation-credit-limit
(the two commits on the feature branch).

Click **Compare & pull request**, tick the checklist, **Create pull request**.

**Screenshot 5:** the pull request page with the checklist and the green
"All checks have passed" box.

**Screenshot 6:** open the **Files changed** tab, hover a line in
`RegistrationValidator.java`, click the blue **+**, write a comment such as
"Boundary at CGPA 2.20 is covered by a test", and click **Add single comment**.
Screenshot the comment.

Click **Squash and merge**, **Confirm**, **Delete branch**.

**Screenshot 7:** the merged pull request (purple "Merged" label), or
https://github.com/sadia-165/SW_Assignment/commits/main showing both squash commits.

---

## Step 4. Release

```bash
git switch main
git pull origin main
git tag -a v1.0.0 -m "SUAMS 1.0: registration and result module"
git push origin v1.0.0
```

On GitHub open **Releases > Draft a new release**, choose tag `v1.0.0`, click
**Generate release notes**, then **Publish release**.

**Screenshot 8:** the release page for `v1.0.0`.

---

## Step 5. GitHub Actions

**Screenshot 9:** open https://github.com/sadia-165/SW_Assignment/actions and click
the run for `v1.0.0`; both `build-test` and `deploy` should be green.
(If you skipped Step 0, open the `build-test` job and screenshot the test step
for Screenshot 1.)

---

## Step 6. SonarCloud (optional, Screenshot 10)

Sign in at sonarcloud.io with GitHub and import `SW_Assignment`. Turn off
*Automatic Analysis* in its settings. In the GitHub repository, add the secret
`SONAR_TOKEN` (Settings > Secrets and variables > Actions > Secrets) and the
variables `SONAR_ORGANIZATION` and `SONAR_PROJECT_KEY` (same page, Variables tab)
with the values SonarCloud shows. Re-run the latest Actions run, then screenshot
the SonarCloud project page with "Quality Gate: Passed".
