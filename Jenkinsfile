pipeline {

    agent any

    tools {
        maven 'Maven-3.9.9'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
             steps {
                sh '''
                                    docker build \
                                        -t shopping-services/product-service:${BUILD_NUMBER} \
                                        -f product-service/Dockerfile .

                                    docker build \
                                        -t shopping-services/order-service:${BUILD_NUMBER} \
                                        -f order-service/Dockerfile .

                                    docker build \
                                        -t shopping-services/inventory-service:${BUILD_NUMBER} \
                                        -f inventory-service/Dockerfile .

                                    docker build \
                                        -t shopping-services/auth-service:${BUILD_NUMBER} \
                                        -f AuthService/Dockerfile .

                                    docker build \
                                        -t shopping-services/api-gateway:${BUILD_NUMBER} \
                                        -f api-gateway/Dockerfile .

                                    docker build \
                                        -t shopping-services/discovery-server:${BUILD_NUMBER} \
                                        -f discovery-server/Dockerfile .
                                '''
             }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }

        failure {
            echo 'Build failed!'
        }
    }
}