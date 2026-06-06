pipeline {
    agent any

    stages {
        stage('Run AWS Tests') {
            steps {
                dir('/Users/ekaterinaostapenko/IdeaProjects/SqS-SnS') {

                    script {
                        def props = readProperties file: '.env'

                        env.AWS_ACCESS_KEY_ID = props.AWS_ACCESS_KEY_ID
                        env.AWS_SECRET_ACCESS_KEY = props.AWS_SECRET_ACCESS_KEY
                        env.AWS_REGION = props.AWS_REGION
                    }

                    sh './run-aws-tests.sh --info'
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
