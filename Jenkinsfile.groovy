pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World"'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
                //step([$class: 'Mailer', recipients: 'gevireddy@gmail.com'])
            }
        }
    stage('Test') {
            node {
                try {
                    sh 'exit 1'
                } 
                finally {
                    step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'gevireddy@gmail.com', sendToIndividuals: true])
                }
            }
        }
    }
        post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}