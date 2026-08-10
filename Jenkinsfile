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
                sh '''
                    until curl -sf http://localhost:4444/status; do
                        echo "Waiting for Selenium..."
                        sleep 2
                    done
                '''
            }
        }

        stage('Tests') {
            steps {
                sh 'docker compose exec -T employee-backend pytest'
                sh 'docker compose exec -T -w /employee_app/e2e employee-backend behave'
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