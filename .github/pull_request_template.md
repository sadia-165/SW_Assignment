## What this pull request does

Backlog item: SUAMS-___
Short description:

## Rules affected

- [ ] R1 credit limits   - [ ] R2 prerequisites   - [ ] R3 registration window
- [ ] R4 theory marks    - [ ] R5 grade scale     - [ ] R6 sessional marks   - [ ] R7 publication

## SQA-2 secure coding checklist (reviewer ticks every line)

- [ ] Every endpoint checks the caller's role **and** ownership of the record
- [ ] Database queries use parameters, never string concatenation
- [ ] Every change to marks writes an audit entry
- [ ] No student data (names, IDs, marks) goes into application logs
- [ ] New code has unit tests named with their test case ID
- [ ] CI is green and the quality gate passed
