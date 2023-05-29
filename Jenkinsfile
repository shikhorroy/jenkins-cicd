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
                script {
                    def imageName = 'webflux-demo'
                    def imageTag = '0.0.1'
                    def imageId = sh(script: "docker images ${imageName}:${imageTag} --format '{{.ID}}'", returnStdout: true).trim()

                    env.OLD_IMAGE_ID = imageId
                    docker.build('webflux-demo:0.0.1', '-f Dockerfile .')
                }
            }
        }
        stage('Delete Old Container') {
            steps {
                script {
                    def containerName = 'webflux-demo'
                    def containerId = sh(script: "docker ps -aqf 'name=${containerName}'", returnStdout: true).trim()
                    def containerExists = sh(script: "docker ps -a --filter 'id=${containerId}' --format '{{.ID}}'", returnStdout: true).trim()
                    if (containerExists) {
                        sh "docker rm -f ${containerId}"
                        echo "Removed container: ${containerId}"
                    } else {
                        echo "${containerName} not exist!"
                    }
                }
            }
        }
        stage ('Run Webapp') {
            steps {
                script {
                    docker.image('webflux-demo:0.0.1').run('-d -p 8081:8081 --name webflux-demo')
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
