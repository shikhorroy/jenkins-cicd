![CICD Pipeline.png](about%2FCICD%20Pipeline.png)

Application URL: http://localhost:8081

# Prerequisites

### Jenkins plugins:

- Docker Pipeline ([Details](https://plugins.jenkins.io/docker-workflow/))
- Kubernetes ([Details](https://plugins.jenkins.io/kubernetes/))
- Kubernetes CLI ([Details](https://plugins.jenkins.io/kubernetes-cli/))
- SonarQube Scanner ([Details](https://plugins.jenkins.io/sonar/))

### Jenkins Tools:

- Configure docker tool to access docker.
  ![Docker Tool Configuration.png](about%2FDocker%20Tool%20Configuration.png)

### Jenkins Credentials:

- Kind: Secret file, ID: kubeConfig, File: <browse .kube config file>
- Kind: Secret text, Secret: <sonarqube token>, ID: sonarToken

### System Configure:

- Setup SonarQube Server
  ![Configure Sonar Server.png](about%2FConfigure%20Sonar%20Server.png)

---

### Webhooks:

- SonarQube webhook:

  We may want to take sonarqube analysis result and based on that we may want to execute further pipeline.
  To wait for SonarQube analysis to be completed and return quality gate status a webhook needed to be configured
  in SonarQube server:

  Webhook URL: http://host.docker.internal:8080/sonarqube-webhook/
  ![SonarQube Webhook.png](about%2FSonarQube%20Webhook.png)
