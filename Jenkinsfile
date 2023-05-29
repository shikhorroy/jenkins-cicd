pipeline {
    agent any

    tools {
        dockerTool 'docker-latest'
    }
    environment {
        DOCKER_HOST = 'tcp://host.docker.internal:2375'
    }
    stages {
        stage ('Sync Source') {
            steps {
                git 'https://github.com/bjitshikhor/webflux-demo'
            }
        }
        stage ('Build Source') {
            steps {
                sh ('chmod +x gradlew')
                sh ('./gradlew clean build')
            }
        }
        stage ('Dockerize Source') {
            steps {
               sh ('docker build -t webflux-demo:0.0.1 .')
            }
        }
    }
}
