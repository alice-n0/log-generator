pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    parameters {
        choice(
            name: 'SERVICE',
            choices: ['user-service', 'order-service', 'payment-service'],
            description: '배포할 서비스 선택'
        )
    }

    environment {
        DOCKER_IMAGE = "hyeonjin5012/log-generator"
        IMAGE_TAG = "latest"

        SERVICE_NAME = "${params.SERVICE}"
        DOCKER_IMAGE = "${DOCKER_REPO}/${SERVICE_NAME}"

        SERVICE_DIR = "backend/${SERVICE_NAME}"
        HELM_CHART = "helm/service-chart"
        VALUES_FILE = "helm/values-${SERVICE_NAME}.yaml"

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
                helm upgrade --install ${SERVICE_NAME} ${HELM_CHART} \
                --namespace ${NAMESPACE} \
                -f ${VALUES_FILE} \
                --set image.repository=${DOCKER_IMAGE} \
                --set image.tag=${IMAGE_TAG}
                """
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
}

