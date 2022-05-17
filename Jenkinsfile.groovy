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
            steps {
                sh 'echo "Fail!" '
            }
        }
    }
        post {
        always {
            echo 'This will always run'
            mail to: 'gevireddy@gmail.com',
             subject: "Jenkins Pipeline: ${currentBuild.fullDisplayName}",
             body: "Result -  ${env.BUILD_URL}"

        }
        success {
            echo 'This will run only if successful'
            emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test'
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