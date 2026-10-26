#!/bin/bash

set -e

IMAGE_NAME="dealership-migrations:local-v2"
PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

docker build \
  -f "$PROJECT_ROOT/docker/services/migrations.Dockerfile" \
  -t "$IMAGE_NAME" \
  "$PROJECT_ROOT"

minikube image load "$IMAGE_NAME"

kubectl delete job migrations --ignore-not-found
kubectl apply -f "$PROJECT_ROOT/k8s/migrations/job.yaml"
kubectl wait --for=condition=complete job/migrations --timeout=180s