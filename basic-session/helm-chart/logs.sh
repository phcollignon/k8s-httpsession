#!/bin/bash
deployment_name="basic-session-deployment"

# Get all pod names for the deployment
pods=$(kubectl get pods -l app=basic-session -o name)

# Loop through the pod names and display logs
for pod in $pods; do
    echo "Logs for $pod"
    kubectl logs $pod
done

