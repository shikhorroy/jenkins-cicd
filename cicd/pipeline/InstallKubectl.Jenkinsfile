pipeline {
    agent any

    stages {
        stage('Install Kubectl') {
            steps {
                steps {
                    script {
                         sh 'curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"'
                         sh 'chmod +x kubectl'
                    }
                }
            }
        }
        stage('Move Kubectl to HOME/bin') {
            steps {
                steps {
                    script {
                         sh "mv kubectl ${HOME}/bin"
                    }
                }
            }
        }
    }
}