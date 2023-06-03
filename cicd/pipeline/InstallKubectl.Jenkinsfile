pipeline {
    agent any

    stages {
        stage('Install Kubectl') {
            steps {
                script {
                     sh '''
                         # Install kubectl
                         curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
                         chmod +x kubectl
                     '''
                }
            }
        }
        stage('Move Kubectl to HOME/bin') {
            steps {
                script {
                     sh '''
                         # Move kubectl to HOME/bin
                         mkdir -p $HOME/bin
                         mv kubectl $HOME/bin/
                     '''
                }
            }
        }
    }
}