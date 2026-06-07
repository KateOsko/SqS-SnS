pipeline {
    agent any

    stages {
        stage('Check Environment') {
            steps {
                sh 'echo AWS_REGION=$AWS_REGION'
                sh 'echo SQS_QUEUE_URL=$SQS_QUEUE_URL'
                sh 'echo SNS_TOPIC_ARN=$SNS_TOPIC_ARN'
            }
        }

        stage('Check AWS') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws_secret_access_key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh 'aws sts get-caller-identity'
                }
            }
        }

        stage('Run AWS Tests') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws_secret_access_key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh './run-aws-tests.sh --info'
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
        }
    }
}