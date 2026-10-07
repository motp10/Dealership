#!/bin/bash

set -e

IMAGE_NAME="dealership-frontend:local"
FRONTEND_ROOT="$(cd "$(dirname "$0")/../frontend" && pwd)"

docker build \
  -f "$FRONTEND_ROOT/../docker/services/frontend.Dockerfile" \
  -t "$IMAGE_NAME" \
  "$FRONTEND_ROOT"

minikube image load "$IMAGE_NAME"

kubectl rollout restart deployment/frontend
kubectl rollout status deployment/frontend
