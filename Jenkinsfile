pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    parameters {
        choice(
            name: 'SERVICE',
            choices: ['user', 'order', 'pay'],
            description: '배포할 서비스 선택'
        )
    }

    environment {
        DOCKER_REPO = "hyeonjin5012"
        IMAGE_TAG = "${BUILD_NUMBER}"

        SERVICE_CHOICE = "${params.SERVICE}"
        SERVICE_NAME = "${SERVICE_CHOICE}-service"
        DOCKER_IMAGE = "${DOCKER_REPO}/${SERVICE_NAME}"

        SERVICE_DIR = "backend/${SERVICE_NAME}"
        HELM_CHART = "helm/service-chart"
        VALUES_FILE = "helm/values-${SERVICE_CHOICE}.yaml"

        NAMESPACE = "observability-platform"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Gradle Build') {
            steps {
                dir("${SERVICE_DIR}") {
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
                    dir("${SERVICE_DIR}") {
                        def image = docker.build("${DOCKER_IMAGE}:${IMAGE_TAG}")

                        docker.withRegistry('', 'docker_password') {
                            image.push()
                        }
                    }
                }
            }
        }

        stage('Deploy helm') {
            steps {
                sh """
                helm upgrade --install ${SERVICE_CHOICE} ${HELM_CHART} \
                --namespace ${NAMESPACE} \
                -f ${VALUES_FILE} \
                --set image.repository=${DOCKER_IMAGE} \
                --set image.tag=${IMAGE_TAG}
                """
            }
        }
    }
    post {
        success {
            echo "✅ ${SERVICE_NAME} 배포 성공"
        }
        failure {
            echo "❌ ${SERVICE_NAME} 배포 실패"
        }
    }
}

