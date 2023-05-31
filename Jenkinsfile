pipeline {
    agent any

    tools {
        dockerTool 'docker-latest'
    }
    environment {
        DOCKER_HOST = 'tcp://host.docker.internal:2375'
        APP_NAME = 'webflux-demo'
        IMAGE_TAG = '0.0.1'
    }
    stages {
        stage ('Sync Source') {
            steps {
                checkout scmGit(
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: 'https://github.com/shikhorroy/jenkins-cicd']])
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
                script {
                    def imageId = sh(script: "docker images ${env.APP_NAME}:${env.IMAGE_TAG} --format '{{.ID}}'", returnStdout: true).trim()
                    env.OLD_IMAGE_ID = imageId
                    docker.build("${env.APP_NAME}:${env.IMAGE_TAG}", '-f Dockerfile .')
                }
            }
        }
        stage('Delete Old Container') {
            steps {
                script {
                    def containerId = sh(script: "docker ps -aqf 'name=${env.APP_NAME}'", returnStdout: true).trim()
                    def containerExists = sh(script: "docker ps -a --filter 'id=${containerId}' --format '{{.ID}}'", returnStdout: true).trim()
                    if (containerExists) {
                        sh "docker rm -f ${containerId}"
                        echo "Removed container: ${containerId}"
                    } else {
                        echo "${env.APP_NAME} not exist!"
                    }
                }
            }
        }
        stage ('Run Webapp') {
            steps {
                script {
                    docker.image("${env.APP_NAME}:${env.IMAGE_TAG}").run("-d -p 8081:8081 --name ${env.APP_NAME}")
                }
            }
        }
        stage ('Cleanup Old Image') {
            steps {
                script {
                    if (env.OLD_IMAGE_ID) {
                        sh "docker rmi ${env.OLD_IMAGE_ID}"
                        echo "Deleted image: ${env.OLD_IMAGE_ID}"
                    } else {
                        echo "Image not found with ID: ${env.OLD_IMAGE_ID}"
                    }
                }
            }
        }
    }
}
