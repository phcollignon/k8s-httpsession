#!/bin/bash
deployment_name="basic-session-deployment"

# Get all pod names for the deployment
pods=$(kubectl get pods -l app=sticky-session -o name)

# Loop through the pod names and display logs
for pod in $pods; do
    echo ""
    echo "Logs for $pod"
    echo "____________________________________________"
    kubectl logs $pod
    echo ""
done

