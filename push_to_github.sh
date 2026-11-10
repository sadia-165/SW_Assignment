#!/usr/bin/env bash
# Pushes the SUAMS code to https://github.com/sadia-165/SW_Assignment with the
# history described in the proposal (Part C.2):
#   main skeleton -> feature branch 1 -> PR -> squash merge
#                 -> feature branch 2 -> PR -> squash merge -> tag v1.0.0 -> release
#
# Needs git and the GitHub CLI (https://cli.github.com), logged in first with:
#   gh auth login
# Run from inside the unzipped "suams" folder:
#   bash push_to_github.sh
# On Windows, run it in Git Bash.

set -euo pipefail

REPO="sadia-165/SW_Assignment"
URL="https://github.com/${REPO}.git"

cd "$(dirname "$0")"

gh auth status >/dev/null 2>&1 || { echo "Run 'gh auth login' first."; exit 1; }

pause() {
  echo
  echo ">>> $1"
  read -r -p ">>> Take the screenshot, then press Enter to continue... " _
}

open_pr_and_merge() {   # $1 branch, $2 title, $3 body
  gh pr create --repo "$REPO" --base main --head "$1" --title "$2" --body "$3"
  local pr
  pr=$(gh pr view "$1" --repo "$REPO" --json number -q .number)
  echo "Waiting for CI checks on pull request #$pr ..."
  sleep 20
  gh pr checks "$pr" --repo "$REPO" --watch || true
  gh pr view "$pr" --repo "$REPO" --web || true
  pause "Screenshot 5 (Conversation tab with green checks) and Screenshot 6 (add a review comment in Files changed)"
  gh pr merge "$pr" --repo "$REPO" --squash --delete-branch
}

# ---- 1. first commit on main --------------------------------------------
if [ ! -d .git ]; then
  git init -b main
  git remote add origin "$URL"
fi

# If the GitHub repository already has commits (for example a README),
# build on top of them instead of overwriting them.
if git ls-remote --exit-code --heads origin main >/dev/null 2>&1; then
  git fetch origin main
  git reset origin/main
fi

git add pom.xml .gitignore README.md HOW_TO_PUSH.md .github scripts docs push_to_github.sh
git commit -m "SUAMS-100: project skeleton, CI workflow and PR template"
git push -u origin main

# ---- 2. feature 1: result processing --------------------------------------
git switch -c feature/SUAMS-101-grading-strategy
git add src/main/java/edu/suams/course src/main/java/edu/suams/result
git commit -m "SUAMS-101: Strategy-based result processing and grade scale (R4-R6)"
git add src/test/java/edu/suams/result
git commit -m "SUAMS-101: unit, boundary and integration tests for grading"
git push -u origin feature/SUAMS-101-grading-strategy

open_pr_and_merge feature/SUAMS-101-grading-strategy \
  "SUAMS-101: Strategy-based result processing" \
  "Adds the Strategy pattern for assessment schemes (R4, R6) and the grade scale (R5), with tests TC-UT-01 to 05, TC-BB-06 to 12 and TC-IT-03, 04."

# ---- 3. feature 2: registration rules -------------------------------------
git switch main
git pull origin main
git switch -c feature/SUAMS-142-probation-credit-limit
git add src/main/java/edu/suams/registration
git commit -m "SUAMS-142: registration rules R1-R3 with probation credit limit"
git add src/test/java/edu/suams/registration
git commit -m "SUAMS-142: basis-path and partition tests for registration"
git push -u origin feature/SUAMS-142-probation-credit-limit

echo "Feature branch commits: https://github.com/${REPO}/commits/feature/SUAMS-142-probation-credit-limit"
pause "Screenshot 4 (open the link above: the two commits on the feature branch)"

open_pr_and_merge feature/SUAMS-142-probation-credit-limit \
  "SUAMS-142: registration rules with probation credit limit" \
  "Adds registration rules R1 to R3 with the 15-credit probation limit, with tests TC-BB-01 to 05, TC-WB-01 to 06 and TC-IT-01, 02."

# ---- 4. release -----------------------------------------------------------
git switch main
git pull origin main
git tag -a v1.0.0 -m "SUAMS 1.0: registration and result module"
git push origin v1.0.0
gh release create v1.0.0 --repo "$REPO" --title "SUAMS v1.0.0" --generate-notes

echo
echo "Done. Pages for the remaining screenshots:"
echo "  2  Repository home:      https://github.com/${REPO}"
echo "  3  Branch protection:    https://github.com/${REPO}/settings/branches"
echo "  7  Main history:         https://github.com/${REPO}/commits/main"
echo "  8  Release:              https://github.com/${REPO}/releases/tag/v1.0.0"
echo "  9  Actions runs:         https://github.com/${REPO}/actions"
