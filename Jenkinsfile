pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    environment {
        DOCKER_IMAGE = "hyeonjin5012/log-generator"
        IMAGE_TAG = "latest"
    }


    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Gradle Build') {
            steps {
                dir('backend') {
                    sh '''
                    chmod +x gradlew
                    ./gradlew clean build
                    '''
                    }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    dir('backend') {
                        def image = docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}")

                        docker.withRegistry('', 'docker_password') {
                            image.push()
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh """
                helm upgrade --install log-generator ./helm/log-generator \
                --namespace observability-platform \
                --set image.repository=${DOCKER_IMAGE} \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }
    }
}

