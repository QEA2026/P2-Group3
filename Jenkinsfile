pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                echo 'Building the app'
                sh 'docker compose up --build'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running tests'
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Deploying the app'
            }   
        }
    }
}