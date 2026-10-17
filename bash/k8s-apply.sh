#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
K8S_DIR="${SCRIPT_DIR}/../k8s"

if ! command -v kubectl >/dev/null 2>&1; then
  echo "Error: kubectl is not installed or not in PATH." >&2
  exit 1
fi

directories=(
  postgres
  backend
  migrations
  frontend
  logging
  alertManager
  vmalert
)

for directory in "${directories[@]}"; do
  manifest_dir="${K8S_DIR}/${directory}"
  if [[ ! -d "${manifest_dir}" ]]; then
    echo "Error: Kubernetes manifest directory not found: ${manifest_dir}" >&2
    exit 1
  fi

  echo "Applying ${directory}..."
  kubectl apply -f "${manifest_dir}"
done

echo "All Kubernetes manifests applied successfully."