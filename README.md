
## HTTP Session Management in Kubernetes-Deployed Applications

### Project Goal
This project aims to study HTTP session behavior in a Kubernetes environment using HTTP session based applications. It explores how sessions are managed without session affinity, with sticky sessions, and with shared session storage using Redis.

### Application Description
The applications implemented are simple "counter" applications that store the count in the HTTP session. There are three versions:

1. **basic-session:** This version does not use session affinity. When deployed across multiple instances, the session is not guaranteed to stick to a single instance.
2. **sticky-session:** This version uses session affinity, ensuring that a user’s session remains with a single instance as long as it is available.
3. **redis-session:** This version uses Redis for shared session storage, ensuring that sessions are persistent and accessible across different instances.

### Application Compilation and Publishing (Optional)
To compile and publish the application Docker images:

1. Navigate to the respective application directory (`basic-session`, `sticky-session`, or `redis-session`).
2. Build the application using Maven:
   ```bash
   mvn clean package
   ```
3. Build the Docker image:
   ```bash
   docker build -t ghcr.io/your_docker_repo/k8s-httpsession:latest .
   ```
4. Push the Docker image to the container registry:
   ```bash
   docker push ghcr.io/your_docker_repo/k8s-httpsession:latest
   ```

Alternatively, you can use the pre-built Docker images provided in ```ghcr.io/phcollignon``` repo as set as default in helm charts.

### DNS Setup
To test the applications locally, you need to map domain names to your Minikube IP. Edit your `/etc/hosts` file to include:

```
<Minikube_IP> basic-session.local
<Minikube_IP> sticky-session.local
<Minikube_IP> redis-session.local
```

Replace `<Minikube_IP>` with the IP address of your Minikube instance. You can find the IP using:
```bash
minikube ip
```

### Deployment and Testing

#### Deploying `basic-session`

1. Navigate to the `basic-session/helm-chart` directory.
2. Deploy the application using Helm:
   ```bash
   helm install basic-session .
   ```
3. Verify that the application is running:
   ```bash
   kubectl get pods
   ```
4. Access the application at `http://basic-session.local` and observe the counter behavior.

#### Deploying `sticky-session`

1. Navigate to the `sticky-session/helm-chart` directory.
2. Deploy the application using Helm:
   ```bash
   helm install sticky-session .
   ```
3. Verify that the application is running:
   ```bash
   kubectl get pods
   ```
4. Access the application at `http://sticky-session.local` and observe the counter behavior. The session should stick to different client browser's session.

5. To facilitate monitoring, a script named `logs.sh` is provided to collect logs from all pods. 
6. You should see that HTTP sessions are distributed among the pods. However, the sessions are sticky, and there is no problem as long as a pod is not deleted.
7. Try to delete a pod and see the result:
   ```bash
   kubectl delete pod <pod_name>
   ```
   Observe that some sessions may be lost if a pod is deleted because the session data is not shared across pods.

#### Deploying `redis-session`

1. Navigate to the `redis-session/helm-chart` directory.
2. Ensure that a Redis instance is deployed and accessible. If not, deploy one using a Helm chart or other means.
3. Deploy the application using Helm:
   ```bash
   helm install redis-session .
   ```
4. Verify that the application is running:
   ```bash
   kubectl get pods
   ```
5. Access the application at `http://redis-session.local` and observe the counter behavior. The session data should persist across multiple instances.

6. Simulate a node failure by deleting one pod:
   ```bash
   kubectl delete pod <pod_name>
   ```
 
7. Refresh the page or continue to use the application. For `redis-session`, the session should persist because the data is stored in a shared Redis instance, unaffected by individual pod failures.

### Conclusion
This setup demonstrates the differences in HTTP session handling in a Kubernetes environment, from no session affinity to sticky sessions and shared session storage with Redis. It highlights the advantages of using Redis for session persistence and reliability in distributed systems.
