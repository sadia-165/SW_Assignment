#!/usr/bin/env bash
# Placeholder deployment step for the assignment.
# In a real SUAMS setup this would copy the tagged build to the production server.
set -euo pipefail

VERSION="${1:?usage: deploy.sh <tag>}"
echo "Deploying SUAMS ${VERSION} to production"
echo "Build source: tagged commit ${GITHUB_SHA:-local}"
echo "Deployment of ${VERSION} finished"
