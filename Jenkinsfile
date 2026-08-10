pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Start') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Tests') {
            steps {
                sh 'docker compose exec -T employee-backend pytest'
                dir('employee_app/e2e'){
                    sh 'behave'
                }
                sh 'docker compose exec -T manager-backend pytest'
            }
        }
    }

    post {
        always {
            sh 'docker compose down -v --remove-orphans'
        }
    }
}