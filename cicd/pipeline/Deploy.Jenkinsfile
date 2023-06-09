pipeline {
    agent any
    parameters {
        booleanParam(name: 'SKIP_SONAR_TEST', defaultValue: true, description: 'Skip Sonar Test?')
    }
    tools {
        dockerTool 'docker-latest'
    }
    environment {
        PATH="$HOME/bin:$PATH"
        DOCKER_HOST = 'tcp://host.docker.internal:2375'
        APP_NAME = 'webflux-demo'
        IMAGE_TAG = '0.0.1'
    }
    stages {
        stage ('Sync Source') {
            steps {
                echo "PATH: ${env.PATH}"
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
        stage('SonarQube Test') {
            when {
                expression { params.SKIP_SONAR_TEST == false }
            }
            steps {
                withSonarQubeEnv(installationName: 'sonarserver') {
                    sh './gradlew sonar'
                }

                timeout(time: 1, unit: 'HOURS') {
                  waitForQualityGate abortPipeline: true
              }
            }
        }
        stage ('Dockerize Source') {
            steps {
                script {
                    def imageId = sh(script: "docker images ${env.APP_NAME}:${env.IMAGE_TAG} --format '{{.ID}}'", returnStdout: true).trim()
                    env.OLD_IMAGE_ID = imageId
                    docker.build("${env.APP_NAME}:${env.IMAGE_TAG}", '-f cicd/docker/Dockerfile .')
                }
            }
        }
//         stage('Delete Old Container') {
//             steps {
//                 script {
//                     def containerId = sh(script: "docker ps -aqf 'name=${env.APP_NAME}'", returnStdout: true).trim()
//                     def containerExists = sh(script: "docker ps -a --filter 'id=${containerId}' --format '{{.ID}}'", returnStdout: true).trim()
//                     if (containerExists) {
//                         sh "docker rm -f ${containerId}"
//                         echo "Removed container: ${containerId}"
//                     } else {
//                         echo "${env.APP_NAME} not exist!"
//                     }
//                 }
//             }
//         }
//         stage ('Run Webapp') {
//             steps {
//                 script {
//                     docker.image("${env.APP_NAME}:${env.IMAGE_TAG}").run("-d -p 8081:8081 --name ${env.APP_NAME}")
//                 }
//             }
//         }
        stage ('K8S Deployment') {
            steps {
                script {
                    withKubeConfig(credentialsId: 'kubeConfig') {
                        sh 'kubectl apply -f cicd/kubernetes/deployment.yaml'
                    }
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
