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
                sh 'docker compose build --no-cache'
            }
        }

        stage('Start') {
            steps {
                sh 'docker compose up -d'
            }
        }

        stage('Wait for Selenium') {
            steps {
                sh '''
                    apt-get -y update; apt-get -y install curl
                    until docker exec $(docker compose ps -q selenium) \
                        curl -sf http://selenium:4444/status; do
                        echo "Waiting for Selenium..."
                        sleep 2
                    done
                '''
            }
        }

        // stage('Wait for Frontend') {
        //     steps {
        //         sh '''
        //             until docker compose exec -T employee-backend \
        //                 python -c "import urllib.request; urllib.request.urlopen('http://frontend:5173')"; do
                        
        //                 echo "Waiting for frontend..."
        //                 sleep 2
        //             done
        //             echo "Frontend is ready!"
        //         '''
        //     }
        // }

        stage('Manager Backend Unit Tests') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh 'docker compose exec -T manager-backend mvn test -f /manager_app/pom.xml -Dtest="com.expense.manager.unit.*.*Test"'
                }
            }

        }

        stage('Employee Backend Unit Tests') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh 'docker compose exec -T employee-backend pytest'
                }
            }
        }

        stage('Employee Behave Tests') {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh 'docker compose exec -T -w /employee_app/e2e employee-backend behave'
                }
            }

        }

        stage('Manager Selenium Tests') {
            steps {
                sh 'echo "Selenium tests not implemented yet..."'
            }
        }

        stage('API Tests') {
            steps {
                sh 'echo "API tests not implemented yet..."'
            }
        }


    }

    post {
        always {
            sh 'docker compose down -v --remove-orphans'
        }
    }
}