#!/bin/bash

set -e

IMAGE_NAME="dealership-migrations:local"
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

docker build \
  -f "$PROJECT_ROOT/docker/services/migrations.Dockerfile" \
  -t "$IMAGE_NAME" \
  "$PROJECT_ROOT"

minikube image load "$IMAGE_NAME"

kubectl rollout restart deployment/migrations
kubectl rollout status deployment/migrations