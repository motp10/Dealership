#!/bin/bash

set -e

IMAGE_NAME="dealership-backend:local"
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

docker build \
  -f "$PROJECT_ROOT/docker/services/backend.Dockerfile" \
  -t "$IMAGE_NAME" \
  "$PROJECT_ROOT"

minikube image load "$IMAGE_NAME"


kubectl rollout restart deployment/backend
kubectl rollout status deployment/backend